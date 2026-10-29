package um.haberes.core.audit;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuditBulkScopeTest {

    private final AuditBuffer buffer = new AuditBuffer();
    private final SharedSessionContractImplementor session = mock(SharedSessionContractImplementor.class);

    @BeforeEach
    void openSession() {
        when(session.isOpen()).thenReturn(true);
    }

    @Test
    void resumeUnEventoPorEntidadYOperacion() {
        try (AuditBulkScope scope = AuditBulkScope.open(
                BulkContext.of("recalculo-legajo", 2026, 8, 12345L),
                Set.of("ItemEntity", "ItemVersionEntity"), buffer, session)) {
            scope.count("ItemEntity", "item", AuditDiffBuilder.Operation.INSERT);
            scope.count("ItemEntity", "item", AuditDiffBuilder.Operation.INSERT);
            scope.count("ItemEntity", "item", AuditDiffBuilder.Operation.DELETE);
            scope.count("ItemVersionEntity", "item_version", AuditDiffBuilder.Operation.INSERT);
            // Una entidad que no es summary-entity la maneja el listener; si se colara acá,
            // se contaría igual, pero el listener decide antes de llamar a count().
        }

        List<AuditEventPayload> events = buffer.drain();

        assertThat(events).hasSize(2);
        AuditEventPayload item = events.stream()
                .filter(event -> "ItemEntity".equals(event.getEntity()))
                .findFirst()
                .orElseThrow();
        assertThat(item.getLevel()).isEqualTo("SUMMARY");
        assertThat(item.getOperation()).isEqualTo("BULK");
        assertThat(item.getAffectedRows()).isEqualTo(3);
        assertThat(item.getChangedFields()).isNull();
        assertThat(item.getBeforeData()).isNull();
        assertThat(item.getAfterData()).isNull();
        assertThat(item.getActorType()).isEqualTo("SYSTEM");
        assertThat(item.getActorTrust()).isEqualTo("SYSTEM");

        @SuppressWarnings("unchecked")
        Map<String, Object> counts = (Map<String, Object>) item.getMetadata().get("counts");
        assertThat(counts).containsEntry("INSERT", 2).containsEntry("DELETE", 1);
        assertThat(item.getMetadata())
                .containsEntry("table", "item")
                .containsEntry("label", "recalculo-legajo")
                .containsEntry("bulkId", item.getCorrelationId());

        @SuppressWarnings("unchecked")
        Map<String, Object> period = (Map<String, Object>) item.getMetadata().get("period");
        assertThat(period).containsEntry("anho", 2026).containsEntry("mes", 8);

        @SuppressWarnings("unchecked")
        Map<String, Object> filter = (Map<String, Object>) item.getMetadata().get("filter");
        assertThat(filter).containsEntry("legajoId", 12345L);

        AuditEventPayload version = events.stream()
                .filter(event -> "ItemVersionEntity".equals(event.getEntity()))
                .findFirst()
                .orElseThrow();
        assertThat(version.getAffectedRows()).isEqualTo(1);
    }

    @Test
    void incluyeElChunkCuandoLaOperacionSeParte() {
        try (AuditBulkScope scope = AuditBulkScope.open(
                BulkContext.chunk("borrado-periodo-item", 2026, 8, "bulk-1", 2, 1000),
                Set.of("ItemEntity"), buffer, session)) {
            scope.count("ItemEntity", "item", AuditDiffBuilder.Operation.DELETE);
        }

        AuditEventPayload event = buffer.drain().getFirst();
        assertThat(event.getCorrelationId()).isEqualTo("bulk-1");
        @SuppressWarnings("unchecked")
        Map<String, Object> chunk = (Map<String, Object>) event.getMetadata().get("chunk");
        assertThat(chunk).containsEntry("index", 2).containsEntry("size", 1000);
    }

    @Test
    void sinCambiosNoEmiteNingunEvento() {
        try (AuditBulkScope scope = AuditBulkScope.open(
                BulkContext.of("sin-cambios", 2026, 8, null),
                Set.of("ItemEntity"), buffer, session)) {
            // nada
        }

        assertThat(buffer.drain()).isEmpty();
    }

    @Test
    void closeEsIdempotente() {
        AuditBulkScope scope = AuditBulkScope.open(
                BulkContext.of("idempotente", 2026, 8, null),
                Set.of("ItemEntity"), buffer, session);
        scope.count("ItemEntity", "item", AuditDiffBuilder.Operation.INSERT);
        scope.close();
        scope.close();

        assertThat(buffer.drain()).hasSize(1);
    }

    @Test
    void disabledNoRegistraScopeNiEmite() {
        AuditBulkScope disabled = AuditBulkScope.disabled();
        assertThat(AuditBulkScope.current()).isNull();
        disabled.count("ItemEntity", "item", AuditDiffBuilder.Operation.INSERT);
        disabled.close();

        assertThat(AuditBulkScope.current()).isNull();
        assertThat(buffer.drain()).isEmpty();
    }
}
