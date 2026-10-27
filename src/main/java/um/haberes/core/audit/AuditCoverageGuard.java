package um.haberes.core.audit;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Guard estático contra escrituras que saltean el ciclo de vida de JPA y, con él, la
 * auditoría de operaciones.
 *
 * <p>La regla es simple: toda escritura de negocio debe pasar por una entidad JPA
 * ({@code save}, {@code delete}, {@code deleteById}, {@code deleteAll} o un derived delete
 * <b>sin</b> {@code @Modifying}) para que Hibernate dispare
 * {@code PostInsert}/{@code PostUpdate}/{@code PostDelete}. Cualquier API que ejecute DML
 * directamente (JPQL masivo, SQL nativo o JDBC) deja el cambio sin auditar y este guard lo
 * denuncia.</p>
 *
 * <p>No es un bean: se usa desde el test {@code AuditCoverageGuardTest}, que falla en CI si
 * aparece un patrón prohibido. El propio paquete {@code um.haberes.core.audit} queda excluido
 * porque el outbox escribe con JDBC a propósito (es el canal de auditoría, no datos de
 * negocio).</p>
 */
public final class AuditCoverageGuard {

    /** Patrones que ejecutan DML sin disparar los listeners de Hibernate. */
    private static final List<String> FORBIDDEN_PATTERNS = List.of(
            "@Modifying",
            "@Query",
            "JdbcTemplate",
            "NamedParameterJdbcTemplate",
            "createNativeQuery",
            "createMutationQuery",
            "StatelessSession",
            "deleteAllInBatch",
            "deleteAllByIdInBatch",
            "@SQLDelete",
            "@SQLInsert",
            "@SQLUpdate",
            "executeUpdate");

    /** El canal de auditoría (outbox) es la única excepción. */
    private static final String AUDIT_PACKAGE = "/um/haberes/core/audit/";

    private AuditCoverageGuard() {
    }

    /**
     * Recorre todos los {@code *.java} bajo {@code root}, salvo el paquete de auditoría, e
     * informa cada aparición de un patrón prohibido en código (los comentarios se ignoran).
     *
     * @param root raíz del código de producción (por ejemplo {@code src/main/java})
     * @return violaciones ordenadas por archivo y línea; vacío si el código está limpio
     */
    public static List<Violation> scan(Path root) {
        if (!Files.isDirectory(root)) {
            throw new IllegalArgumentException("No existe el directorio a escanear: " + root);
        }

        try (Stream<Path> files = Files.walk(root)) {
            List<Violation> violations = new ArrayList<>();
            files.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .filter(path -> !isAuditPackage(path))
                    .sorted()
                    .forEach(path -> violations.addAll(scanFile(root, path)));
            return List.copyOf(violations);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo escanear " + root, e);
        }
    }

    private static boolean isAuditPackage(Path path) {
        return ("/" + path.toString().replace('\\', '/')).contains(AUDIT_PACKAGE);
    }

    private static List<Violation> scanFile(Path root, Path file) {
        String source;
        try {
            source = Files.readString(file);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo leer " + file, e);
        }

        Path display = root.relativize(file);
        List<Violation> violations = new ArrayList<>();
        List<String> codeLines = codeLines(source);
        for (int index = 0; index < codeLines.size(); index++) {
            String line = codeLines.get(index);
            for (String pattern : FORBIDDEN_PATTERNS) {
                if (line.contains(pattern)) {
                    violations.add(new Violation(display, index + 1, pattern));
                }
            }
        }
        return violations;
    }

    /**
     * Devuelve el código del archivo con los comentarios reemplazados por espacios,
     * conservando los saltos de línea para no perder la numeración. Los literales de texto
     * (incluidos los text blocks) se dejan intactos.
     */
    static List<String> codeLines(String source) {
        StringBuilder code = new StringBuilder(source.length());
        int index = 0;
        while (index < source.length()) {
            char current = source.charAt(index);
            char next = index + 1 < source.length() ? source.charAt(index + 1) : '\0';

            if (current == '/' && next == '/') {
                while (index < source.length() && source.charAt(index) != '\n') {
                    code.append(' ');
                    index++;
                }
            } else if (current == '/' && next == '*') {
                code.append("  ");
                index += 2;
                while (index < source.length()) {
                    if (source.charAt(index) == '*' && index + 1 < source.length()
                            && source.charAt(index + 1) == '/') {
                        code.append("  ");
                        index += 2;
                        break;
                    }
                    code.append(source.charAt(index) == '\n' ? '\n' : ' ');
                    index++;
                }
            } else if (source.startsWith("\"\"\"", index)) {
                code.append("\"\"\"");
                index += 3;
                while (index < source.length()) {
                    if (source.startsWith("\"\"\"", index)) {
                        code.append("\"\"\"");
                        index += 3;
                        break;
                    }
                    code.append(source.charAt(index));
                    index++;
                }
            } else if (current == '"' || current == '\'') {
                char quote = current;
                code.append(quote);
                index++;
                while (index < source.length()) {
                    char inside = source.charAt(index);
                    if (inside == '\\' && index + 1 < source.length()) {
                        code.append(inside).append(source.charAt(index + 1));
                        index += 2;
                        continue;
                    }
                    code.append(inside);
                    index++;
                    if (inside == quote) {
                        break;
                    }
                }
            } else {
                code.append(current);
                index++;
            }
        }

        List<String> lines = new ArrayList<>();
        int start = 0;
        for (int position = 0; position < code.length(); position++) {
            if (code.charAt(position) == '\n') {
                lines.add(code.substring(start, position));
                start = position + 1;
            }
        }
        lines.add(code.substring(start));
        return lines;
    }

    /** Una aparición de un patrón prohibido: archivo, línea y patrón. */
    public record Violation(Path file, int line, String pattern) {

        public String message() {
            return "Escritura fuera de la auditoría en %s:%d: '%s'. "
                    .formatted(file.toString().replace('\\', '/'), line, pattern)
                    + "Toda escritura debe pasar por JPA (ver AGENTS.md).";
        }

        @Override
        public String toString() {
            return message();
        }
    }
}
