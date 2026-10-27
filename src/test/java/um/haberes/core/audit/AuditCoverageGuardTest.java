package um.haberes.core.audit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class AuditCoverageGuardTest {

    private static final Path SOURCE_ROOT = Path.of(System.getProperty("user.dir"), "src/main/java");

    @Test
    void elCodigoDeProduccionNoTieneEscriturasFueraDeJpa() {
        List<AuditCoverageGuard.Violation> violations = AuditCoverageGuard.scan(SOURCE_ROOT);

        assertThat(violations)
                .withFailMessage(() -> "Escrituras fuera de la auditoría:\n" + violations.stream()
                        .map(AuditCoverageGuard.Violation::message)
                        .collect(Collectors.joining("\n")))
                .isEmpty();
    }

    @Test
    void detectaUnaEscrituraNoAuditadaEnUnArchivoTemporal(@TempDir Path tempDir) throws IOException {
        Path source = write(tempDir, "um/haberes/core/repository/Foo.java", """
                package um.haberes.core.repository;

                public interface Foo {

                    @Modifying
                    void deleteAllByAnhoAndMes(Integer anho, Integer mes);
                }
                """);

        List<AuditCoverageGuard.Violation> violations = AuditCoverageGuard.scan(tempDir);

        assertThat(violations).hasSize(1);
        AuditCoverageGuard.Violation violation = violations.getFirst();
        assertThat(violation.line()).isEqualTo(5);
        assertThat(violation.pattern()).isEqualTo("@Modifying");
        assertThat(violation.file().toString().replace('\\', '/'))
                .isEqualTo("um/haberes/core/repository/Foo.java");
        assertThat(violation.message()).contains("Foo.java:5", "@Modifying", "AGENTS.md");
    }

    @Test
    void ignoraLosComentariosDeLineaYDeBloque(@TempDir Path tempDir) throws IOException {
        write(tempDir, "um/haberes/core/repository/Commented.java", """
                package um.haberes.core.repository;

                // Un borrado con @Modifying no dispara post-delete
                /*
                 * Tampoco executeUpdate ni createNativeQuery.
                 */
                public interface Commented {
                    /** Javadoc con @Query y JdbcTemplate. */
                    void deleteByLegajoId(Long legajoId);
                }
                """);

        assertThat(AuditCoverageGuard.scan(tempDir)).isEmpty();
    }

    @Test
    void excluyeElPaqueteDeAuditoriaPorqueEscribeElOutboxConJdbc(@TempDir Path tempDir) throws IOException {
        write(tempDir, "um/haberes/core/audit/AuditOutboxWriter.java", """
                package um.haberes.core.audit;

                class AuditOutboxWriter {
                    void insert(java.sql.PreparedStatement statement) throws Exception {
                        statement.executeUpdate();
                    }
                }
                """);

        assertThat(AuditCoverageGuard.scan(tempDir)).isEmpty();
    }

    private Path write(Path root, String relativePath, String content) throws IOException {
        Path file = root.resolve(relativePath);
        Files.createDirectories(file.getParent());
        Files.writeString(file, content);
        return file;
    }
}
