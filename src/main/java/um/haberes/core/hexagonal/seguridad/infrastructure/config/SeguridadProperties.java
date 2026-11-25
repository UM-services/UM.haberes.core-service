package um.haberes.core.hexagonal.seguridad.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.seguridad")
public class SeguridadProperties {

    /** Sincroniza permisos.yaml/roles.yaml contra la base al arrancar. */
    private boolean manifestSyncEnabled = true;
    private boolean manifestDryRun = false;
    private String permisosManifest = "classpath:permisos.yaml";
    private String rolesManifest = "classpath:roles.yaml";

    /** Exige el token Bearer en todos los endpoints salvo login/actuator/swagger. */
    private boolean tokenRequired = false;
    /** Aplica @RequierePermiso / mapa de rutas sobre los endpoints de negocio. */
    private boolean permissionsEnforce = false;
    /** Secreto HMAC del token. Sin default en el repo; obligatorio si tokenRequired=true. */
    private String tokenSecret = "";
    private long tokenTtlHours = 8;
    /**
     * Tokens de servicio en formato `nombre:token,nombre2:token2`. Se usan para
     * llamadas service-to-service (haberes-etec, haberes-report, tesoreria-core).
     */
    private String serviceTokens = "";

    public Map<String, String> serviceTokensMap() {
        Map<String, String> result = new LinkedHashMap<>();
        if (serviceTokens == null || serviceTokens.isBlank()) {
            return result;
        }
        for (String entry : serviceTokens.split(",")) {
            String[] parts = entry.split(":", 2);
            if (parts.length == 2 && !parts[0].isBlank() && !parts[1].isBlank()) {
                result.put(parts[0].trim(), parts[1].trim());
            }
        }
        return result;
    }
}
