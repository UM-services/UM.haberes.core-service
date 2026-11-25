package um.haberes.core.hexagonal.seguridad.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import um.haberes.core.hexagonal.seguridad.application.service.PermisoEfectivoService;

/**
 * Registra el PEP sólo cuando {@code app.seguridad.permissions-enforce=true}.
 * Por defecto queda apagado para permitir el despliegue escalonado (core nuevo
 * con frontend viejo). El filtro de token es independiente y se controla con
 * {@code app.seguridad.token-required}.
 */
@Configuration
@ConditionalOnProperty(name = "app.seguridad.permissions-enforce", havingValue = "true")
@RequiredArgsConstructor
public class PermissionWebConfig implements WebMvcConfigurer {

    private final PermisoEfectivoService permisoEfectivoService;
    private final RutasPermisos rutasPermisos;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequierePermisoInterceptor(permisoEfectivoService, rutasPermisos));
    }
}
