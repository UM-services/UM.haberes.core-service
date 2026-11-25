package um.haberes.core.hexagonal.seguridad.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import um.haberes.core.hexagonal.seguridad.application.exception.SeguridadNotFoundException;
import um.haberes.core.hexagonal.seguridad.application.service.PermisoEfectivoService;
import um.haberes.core.hexagonal.seguridad.domain.model.PermisoEfectivo;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PEP: valida identidad + permiso. Corre después de
 * {@link TokenAuthenticationFilter}, que publica la {@link Identidad}.
 *
 * <ul>
 *   <li>Sin identidad → 401.</li>
 *   <li>Servicio interno → acceso total (llamadas service-to-service).</li>
 *   <li>Rutas de “propio usuario” (usuario/{id}, lastlog, permisoEfectivo) →
 *       sólo el mismo legajo o un administrador.</li>
 *   <li>Permiso requerido por anotación o por {@link RutasPermisos}; OR entre claves;
 *       comodín {@code modulo.*} soportado.</li>
 * </ul>
 */
public class RequierePermisoInterceptor implements HandlerInterceptor {

    private static final Pattern PROPIO = Pattern.compile(
            "^/api/haberes/core/(?:usuario/(\\d+)|usuario/lastlog/(\\d+)(?:/.*)?|permisoEfectivo/usuario/(\\d+))$");

    private final PermisoEfectivoService permisoEfectivoService;
    private final RutasPermisos rutasPermisos;

    public RequierePermisoInterceptor(PermisoEfectivoService permisoEfectivoService,
                                      RutasPermisos rutasPermisos) {
        this.permisoEfectivoService = permisoEfectivoService;
        this.rutasPermisos = rutasPermisos;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        String path = request.getRequestURI();
        if (esPublico(request.getMethod(), path)) {
            return true;
        }
        Identidad identidad = (Identidad) request.getAttribute(Identidad.ATRIBUTO);
        if (identidad == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sesión no autenticada");
        }
        if (identidad.esServicio()) {
            return true;
        }

        PermisoEfectivo efectivo;
        try {
            efectivo = permisoEfectivoService.obtener(identidad.legajoId());
        } catch (SeguridadNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no válido");
        }

        if (!permitePropio(path, identidad, efectivo)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "No podés acceder a los datos de otro usuario");
        }

        List<String> requeridas = resolver(handlerMethod, request.getMethod(), path);
        if (requeridas.isEmpty() || efectivo.isAdministrador()) {
            return true;
        }
        for (String clave : requeridas) {
            if (coincide(efectivo.getPermisos(), clave)) {
                return true;
            }
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "Permiso requerido: " + String.join(" | ", requeridas));
    }

    /**
     * Rutas que no pasan por el PEP: actuator (Consul/health), documentación,
     * login y el alias legado de validación de credenciales.
     */
    private boolean esPublico(String metodo, String path) {
        if ("OPTIONS".equalsIgnoreCase(metodo)) {
            return true;
        }
        return path.startsWith("/actuator")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.equals("/error")
                || path.startsWith("/api/haberes/core/auth")
                || path.startsWith("/api/haberes/core/usuario/isuservalid");
    }

    private List<String> resolver(HandlerMethod handlerMethod, String metodo, String path) {
        RequierePermiso anotacion = handlerMethod.getMethodAnnotation(RequierePermiso.class);
        if (anotacion == null) {
            anotacion = handlerMethod.getBeanType().getAnnotation(RequierePermiso.class);
        }
        if (anotacion != null) {
            return List.of(anotacion.value());
        }
        // Lecturas del propio usuario (perfil y bundle): la propiedad ya se validó.
        if ("GET".equalsIgnoreCase(metodo) && PROPIO.matcher(path).matches()) {
            return List.of();
        }
        Optional<List<String>> regla = rutasPermisos.requeridas(metodo, path);
        return regla.orElseGet(List::of);
    }

    private boolean permitePropio(String path, Identidad identidad, PermisoEfectivo efectivo) {
        Matcher matcher = PROPIO.matcher(path);
        if (!matcher.matches()) {
            return true;
        }
        Long legajo = primerNoNulo(matcher.group(1), matcher.group(2), matcher.group(3));
        if (legajo == null) {
            return true;
        }
        return legajo.equals(identidad.legajoId()) || efectivo.isAdministrador();
    }

    private Long primerNoNulo(String... valores) {
        for (String valor : valores) {
            if (valor != null) {
                return Long.valueOf(valor);
            }
        }
        return null;
    }

    private boolean coincide(List<String> permisos, String clave) {
        if (clave == null || clave.isBlank()) {
            return true;
        }
        if (clave.endsWith(".*")) {
            String prefijo = clave.substring(0, clave.length() - 1);
            return permisos.stream().anyMatch(permiso -> permiso.startsWith(prefijo));
        }
        return permisos.contains(clave);
    }
}
