package um.haberes.core.audit;

import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.type.Type;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuditDiffBuilderTest {

    private final AuditProperties properties = new AuditProperties();
    private final AuditDiffBuilder builder = new AuditDiffBuilder(properties, JsonMapper.builder().build());

    @Test
    void enUpdateSoloIncluyeCamposQueCambianYEnmascaraSensibles() {
        EntityPersister persister = persister(
                new String[]{"id", "cbu", "importe", "persona"},
                new boolean[]{false, false, false, true});

        Object[] before = {1L, "12345678", new BigDecimal("100.00"), null};
        Object[] after = {1L, "87654321", new BigDecimal("100.00"), null};

        AuditDiffBuilder.DiffResult diff = builder.build(
                persister, before, after, AuditDiffBuilder.Operation.UPDATE);

        assertThat(diff.changedFields()).containsExactly("cbu");
        assertThat(diff.before()).containsEntry("cbu", "****5678");
        assertThat(diff.after()).containsEntry("cbu", "****4321");
        assertThat(diff.before()).doesNotContainKey("id");
        assertThat(diff.before()).doesNotContainKey("importe");
        assertThat(diff.before()).doesNotContainKey("persona");
        assertThat(diff.truncated()).isFalse();
    }

    @Test
    void passwordSeEnmascaraCompleto() {
        EntityPersister persister = persister(
                new String[]{"id", "password"},
                new boolean[]{false, false});

        AuditDiffBuilder.DiffResult diff = builder.build(
                persister,
                new Object[]{1L, "secreto"},
                new Object[]{1L, "otro-secreto"},
                AuditDiffBuilder.Operation.UPDATE);

        assertThat(diff.changedFields()).containsExactly("password");
        assertThat(diff.before()).containsEntry("password", "****");
        assertThat(diff.after()).containsEntry("password", "****");
    }

    @Test
    void insertYDeleteRecorrenTodosLosCamposEscalares() {
        EntityPersister persister = persister(
                new String[]{"id", "importe", "persona"},
                new boolean[]{false, false, true});

        AuditDiffBuilder.DiffResult insert = builder.build(
                persister, null, new Object[]{1L, new BigDecimal("10.00"), null},
                AuditDiffBuilder.Operation.INSERT);
        assertThat(insert.changedFields()).containsExactly("id", "importe");
        assertThat(insert.before()).isEmpty();
        assertThat(insert.after()).containsEntry("importe", new BigDecimal("10.00"));

        AuditDiffBuilder.DiffResult delete = builder.build(
                persister, new Object[]{1L, new BigDecimal("10.00"), null}, null,
                AuditDiffBuilder.Operation.DELETE);
        assertThat(delete.changedFields()).containsExactly("id", "importe");
        assertThat(delete.after()).isEmpty();
    }

    @Test
    void siElPayloadEsGrandeSeDescartaYSeMarcaTruncado() {
        properties.setMaxPayloadChars(10);
        EntityPersister persister = persister(
                new String[]{"importe"},
                new boolean[]{false});

        AuditDiffBuilder.DiffResult diff = builder.build(
                persister, null, new Object[]{new BigDecimal("1234567890.00")},
                AuditDiffBuilder.Operation.INSERT);

        assertThat(diff.truncated()).isTrue();
        assertThat(diff.before()).isEmpty();
        assertThat(diff.after()).isEmpty();
        assertThat(diff.changedFields()).containsExactly("importe");
    }

    private EntityPersister persister(String[] names, boolean[] association) {
        Type[] types = new Type[names.length];
        for (int i = 0; i < names.length; i++) {
            Type type = mock(Type.class);
            when(type.isAssociationType()).thenReturn(association[i]);
            types[i] = type;
        }
        EntityPersister persister = mock(EntityPersister.class);
        when(persister.getPropertyNames()).thenReturn(names);
        when(persister.getPropertyTypes()).thenReturn(types);
        return persister;
    }
}
