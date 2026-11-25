package um.haberes.core.hexagonal.seguridad.infrastructure.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import um.haberes.core.hexagonal.seguridad.application.service.PermisoEfectivoService;
import um.haberes.core.hexagonal.seguridad.application.service.SeguridadSyncService;
import um.haberes.core.hexagonal.seguridad.infrastructure.config.SeguridadProperties;

@Component
@Order(Ordered.LOWEST_PRECEDENCE - 100)
@RequiredArgsConstructor
@Slf4j
public class SeguridadManifestSyncRunner implements ApplicationRunner {

    private final SeguridadProperties properties;
    private final SeguridadSyncService syncService;
    private final PermisoEfectivoService permisoEfectivoService;

    @Override
    public void run(ApplicationArguments args) {
        if (properties.isManifestSyncEnabled()) {
            try {
                syncService.syncPermisos();
                syncService.syncRoles();
                permisoEfectivoService.invalidar();
            } catch (RuntimeException e) {
                throw new IllegalStateException(
                        "Falló el sync del manifiesto de seguridad. Verificá que el DDL "
                                + "sql/027_rbac_permisos.sql esté aplicado y que permisos.yaml/roles.yaml "
                                + "sean válidos: " + e.getMessage(), e);
            }
        } else {
            log.warn("Sync del manifiesto de seguridad deshabilitado (app.seguridad.manifest-sync-enabled=false)");
        }

        if (!properties.isTokenRequired()) {
            log.warn("Seguridad: token-required=false. Los endpoints no exigen token (modo transición).");
        }
        if (!properties.isPermissionsEnforce()) {
            log.warn("Seguridad: permissions-enforce=false. No se evalúan permisos por endpoint.");
        } else if (!properties.isTokenRequired()) {
            log.warn("Seguridad: permissions-enforce=true con token-required=false. "
                    + "Las requests sin token válido recibirán 401.");
        }
        if (properties.isTokenRequired() && properties.serviceTokensMap().isEmpty()) {
            log.warn("Seguridad: no hay service-tokens configurados. haberes-etec, haberes-report "
                    + "y tesoreria-core no podrán llamar a haberes-core.");
        }
    }
}
