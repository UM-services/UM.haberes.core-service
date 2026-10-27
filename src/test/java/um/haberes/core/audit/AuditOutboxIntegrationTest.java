package um.haberes.core.audit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        "haberes.audit.sender-delay-ms=3600000"
})
class AuditOutboxIntegrationTest {

    @Autowired
    private JpaUsuarioRepository usuarioRepository;

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
    void unUpdateYUnDeleteDejanSusEventosEnElOutboxConElCampoEnmascarado() throws Exception {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setLegajoId(999999L);
        usuario.setPassword("secreto");
        usuario.setUsuarioId(1L);
        usuario.setFacultadId(6);
        usuarioRepository.saveAndFlush(usuario);

        usuario.setPassword("nuevo-secreto");
        usuarioRepository.saveAndFlush(usuario);

        usuarioRepository.deleteById(999999L);

        List<JsonNode> payloads = payloads();
        assertThat(payloads).hasSize(3);

        JsonNode update = payloads.stream()
                .filter(node -> "UPDATE".equals(node.get("operation").asString()))
                .findFirst()
                .orElseThrow();

        assertThat(update.get("level").asString()).isEqualTo("CHANGE");
        assertThat(update.get("occurredAt").isString()).isTrue();
        assertThat(update.get("entity").asString()).isEqualTo("UsuarioEntity");
        assertThat(update.get("entityId").asString()).isEqualTo("999999");
        assertThat(update.get("service").asString()).isEqualTo("haberes-core");
        List<String> changed = new ArrayList<>();
        update.get("changedFields").forEach(node -> changed.add(node.asString()));
        assertThat(changed).contains("password");
        assertThat(update.get("beforeData").get("password").asString()).isEqualTo("****");
        assertThat(update.get("afterData").get("password").asString()).isEqualTo("****");

        JsonNode delete = payloads.stream()
                .filter(node -> "DELETE".equals(node.get("operation").asString()))
                .findFirst()
                .orElseThrow();

        assertThat(delete.get("level").asString()).isEqualTo("CHANGE");
        assertThat(delete.get("entity").asString()).isEqualTo("UsuarioEntity");
        assertThat(delete.get("entityId").asString()).isEqualTo("999999");
        assertThat(delete.get("affectedRows").asInt()).isEqualTo(1);
        List<String> deleted = new ArrayList<>();
        delete.get("changedFields").forEach(node -> deleted.add(node.asString()));
        assertThat(deleted).contains("password");
        assertThat(delete.get("beforeData").get("password").asString()).isEqualTo("****");
        JsonNode afterData = delete.get("afterData");
        assertThat(afterData == null || afterData.isNull() || afterData.isEmpty()).isTrue();
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
