package um.haberes.core.hexagonal.seguridad.infrastructure.manifest;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;
import um.haberes.core.hexagonal.seguridad.application.exception.ManifestException;
import um.haberes.core.hexagonal.seguridad.domain.model.PermisoSemilla;
import um.haberes.core.hexagonal.seguridad.domain.model.RolSemilla;
import um.haberes.core.hexagonal.seguridad.infrastructure.config.SeguridadProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class SeguridadManifestLoader {

    private static final Pattern CLAVE = Pattern.compile("^[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)+$");
    private static final Pattern APLICACION = Pattern.compile("^[A-Z0-9_]+$");
    private static final Pattern ROL = Pattern.compile("^[A-Z0-9_]{2,80}$");

    private final ResourceLoader resourceLoader;
    private final SeguridadProperties properties;

    public List<PermisoSemilla> loadPermisos() {
        Map<String, Object> root = load(properties.getPermisosManifest());
        List<Map<String, Object>> items = list(root, "permisos", properties.getPermisosManifest());
        List<PermisoSemilla> result = new ArrayList<>();
        int index = 0;
        for (Map<String, Object> item : items) {
            index++;
            String aplicacion = upper(text(item, "aplicacion", index, properties.getPermisosManifest()));
            if (!APLICACION.matcher(aplicacion).matches()) {
                throw new ManifestException("Aplicación inválida en permiso #" + index + ": " + aplicacion);
            }
            String clave = text(item, "clave", index, properties.getPermisosManifest());
            if (!CLAVE.matcher(clave).matches()) {
                throw new ManifestException("Clave inválida en permiso #" + index + ": " + clave);
            }
            String modulo = text(item, "modulo", index, properties.getPermisosManifest()).toLowerCase(Locale.ROOT);
            String descripcion = text(item, "descripcion", index, properties.getPermisosManifest());
            result.add(PermisoSemilla.builder()
                    .aplicacion(aplicacion)
                    .clave(clave)
                    .modulo(modulo)
                    .descripcion(descripcion)
                    .activo(flag(item.get("activo")))
                    .build());
        }
        return result;
    }

    public List<RolSemilla> loadRoles() {
        Map<String, Object> root = load(properties.getRolesManifest());
        List<Map<String, Object>> items = list(root, "roles", properties.getRolesManifest());
        List<RolSemilla> result = new ArrayList<>();
        int index = 0;
        for (Map<String, Object> item : items) {
            index++;
            String nombre = upper(text(item, "nombre", index, properties.getRolesManifest()));
            if (!ROL.matcher(nombre).matches()) {
                throw new ManifestException("Nombre de rol inválido en #" + index + ": " + nombre);
            }
            String aplicacion = upper(text(item, "aplicacion", index, properties.getRolesManifest()));
            if (!APLICACION.matcher(aplicacion).matches()) {
                throw new ManifestException("Aplicación inválida en rol #" + index + ": " + aplicacion);
            }
            String descripcion = text(item, "descripcion", index, properties.getRolesManifest());
            List<String> permisos = new ArrayList<>();
            Object raw = item.get("permisos");
            if (raw instanceof List<?> lista) {
                for (Object valor : lista) {
                    if (valor != null) {
                        permisos.add(valor.toString().trim());
                    }
                }
            }
            result.add(RolSemilla.builder()
                    .nombre(nombre)
                    .descripcion(descripcion)
                    .aplicacion(aplicacion)
                    .activo(flag(item.get("activo")))
                    .permisos(permisos)
                    .build());
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> load(String location) {
        Resource resource = resourceLoader.getResource(location);
        if (!resource.exists()) {
            throw new ManifestException("No se encontró el manifiesto de seguridad: " + location);
        }
        try (InputStream input = resource.getInputStream()) {
            Object loaded = new Yaml().load(input);
            if (!(loaded instanceof Map<?, ?> map)) {
                throw new ManifestException("El manifiesto " + location + " no tiene el formato esperado");
            }
            return (Map<String, Object>) map;
        } catch (IOException | YAMLException e) {
            throw new ManifestException("No se pudo leer el manifiesto " + location + ": " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> list(Map<String, Object> root, String key, String location) {
        Object value = root.get(key);
        if (!(value instanceof List<?> list)) {
            throw new ManifestException("El manifiesto " + location + " no contiene la lista '" + key + "'");
        }
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object item : list) {
            if (!(item instanceof Map<?, ?> map)) {
                throw new ManifestException("Ítem inválido en '" + key + "' de " + location);
            }
            result.add((Map<String, Object>) map);
        }
        return result;
    }

    private String text(Map<String, Object> item, String key, int index, String location) {
        Object value = item.get(key);
        if (value == null || value.toString().isBlank()) {
            throw new ManifestException("Falta '" + key + "' en el ítem #" + index + " de " + location);
        }
        return value.toString().trim();
    }

    private String upper(String value) {
        return value.toUpperCase(Locale.ROOT);
    }

    private Byte flag(Object value) {
        if (value == null) {
            return (byte) 1;
        }
        if (value instanceof Boolean bool) {
            return (byte) (bool ? 1 : 0);
        }
        if (value instanceof Number number) {
            return (byte) (number.intValue() == 0 ? 0 : 1);
        }
        String text = value.toString().trim().toLowerCase(Locale.ROOT);
        return (byte) (text.equals("false") || text.equals("0") ? 0 : 1);
    }
}
