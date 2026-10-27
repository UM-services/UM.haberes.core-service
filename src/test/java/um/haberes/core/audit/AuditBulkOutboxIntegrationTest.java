package um.haberes.core.audit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import um.haberes.core.model.UsuarioEntity;
import um.haberes.core.repository.JpaUsuarioRepository;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "haberes.audit.mode=SEND",
        "haberes.audit.api-key=test-key",
        "haberes.audit.url=http://127.0.0.1:1",
        "haberes.audit.sender-initial-delay-ms=3600000",
        "haberes.audit.sender-delay-ms=3600000",
        // UsuarioEntity hace de entidad masiva para probar el mecanismo sin crear el
        // esquema de item/item_version en H2.
        "haberes.audit.summary-entities=UsuarioEntity"
})
class AuditBulkOutboxIntegrationTest {

    @Autowired
    private JpaUsuarioRepository usuarioRepository;

    @Autowired
    private AuditBulkService auditBulk;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private DataSource dataSource;

    private final JsonMapper mapper = JsonMapper.builder().build();

    @BeforeEach
    void clean() throws Exception {
        usuarioRepository.deleteAll();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM audit_outbox");
        }
    }

    @Test
    void unScopeDejaUnSoloEventoSummaryConLosConteos() throws Exception {
        new TransactionTemplate(transactionManager).executeWithoutResult(status -> {
            try (AuditBulkScope ignored = auditBulk.begin(
                    BulkContext.of("recalculo-legajo", 2026, 8, 999999L))) {
                usuarioRepository.saveAndFlush(usuario(999999L, "uno"));
                usuarioRepository.saveAndFlush(usuario(888888L, "dos"));
                usuarioRepository.deleteById(999999L);
            }
        });

        List<JsonNode> payloads = payloads();
        assertThat(payloads).hasSize(1);

        JsonNode summary = payloads.getFirst();
        assertThat(summary.get("level").asString()).isEqualTo("SUMMARY");
        assertThat(summary.get("operation").asString()).isEqualTo("BULK");
        assertThat(summary.get("entity").asString()).isEqualTo("UsuarioEntity");
        assertThat(summary.get("affectedRows").asInt()).isEqualTo(3);
        assertThat(summary.get("changedFields").isNull()).isTrue();
        assertThat(summary.get("beforeData").isNull()).isTrue();
        assertThat(summary.get("metadata").get("counts").get("INSERT").asInt()).isEqualTo(2);
        assertThat(summary.get("metadata").get("counts").get("DELETE").asInt()).isEqualTo(1);
        assertThat(summary.get("metadata").get("period").get("anho").asInt()).isEqualTo(2026);
        assertThat(summary.get("metadata").get("filter").get("legajoId").asLong()).isEqualTo(999999L);
        assertThat(summary.get("metadata").get("label").asString()).isEqualTo("recalculo-legajo");
    }

    @Test
    void fueraDelScopeLaMismaEntidadSigueAuditandosePorFila() throws Exception {
        usuarioRepository.saveAndFlush(usuario(999999L, "uno"));
        usuarioRepository.saveAndFlush(usuario(888888L, "dos"));
        usuarioRepository.deleteById(999999L);

        List<JsonNode> payloads = payloads();
        assertThat(payloads).hasSize(3);
        assertThat(payloads).allSatisfy(node -> {
            assertThat(node.get("level").asString()).isEqualTo("CHANGE");
            assertThat(node.get("entity").asString()).isEqualTo("UsuarioEntity");
        });
    }

    private UsuarioEntity usuario(Long legajoId, String password) {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setLegajoId(legajoId);
        usuario.setPassword(password);
        usuario.setUsuarioId(1L);
        usuario.setFacultadId(6);
        return usuario;
    }

    private List<JsonNode> payloads() throws Exception {
        List<JsonNode> payloads = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT payload FROM audit_outbox ORDER BY outbox_id")) {
            while (resultSet.next()) {
                payloads.add(mapper.readTree(resultSet.getString(1)));
            }
        }
        return payloads;
    }
}
