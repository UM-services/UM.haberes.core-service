package um.haberes.core.hexagonal.seguridad.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import um.haberes.core.hexagonal.seguridad.infrastructure.config.SeguridadProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Locale;
import java.util.Map;

/**
 * Valida el token Bearer y publica la {@link Identidad} en la request.
 *
 * <p>Con {@code app.seguridad.token-required=false} el filtro es permisivo: si
 * no hay token deja pasar (modo transición/desarrollo) y si hay token lo valida.
 * Con {@code true}, todo endpoint fuera de la lista pública responde 401 sin
 * token válido.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER = "bearer ";
    private static final String[] PUBLICOS = {
            "/actuator",
            "/error",
            "/swagger-ui",
            "/v3/api-docs",
            "/api/haberes/core/auth",
            "/api/haberes/core/usuario/isuservalid"
    };

    private final TokenService tokenService;
    private final SeguridadProperties properties;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String path = request.getRequestURI();
        for (String publico : PUBLICOS) {
            if (path.startsWith(publico)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String token = extraer(authorization);

        if (token == null) {
            if (properties.isTokenRequired()) {
                rechazar(response, "Falta el token de sesión");
                return;
            }
            filterChain.doFilter(request, response);
            return;
        }

        Identidad servicio = identidadDeServicio(token);
        if (servicio != null) {
            request.setAttribute(Identidad.ATRIBUTO, servicio);
            filterChain.doFilter(request, response);
            return;
        }

        try {
            request.setAttribute(Identidad.ATRIBUTO, tokenService.validar(token));
        } catch (TokenService.TokenInvalidoException e) {
            if (properties.isTokenRequired()) {
                rechazar(response, e.getMessage());
                return;
            }
            // Modo transición: un token inválido no bloquea la request.
        }
        filterChain.doFilter(request, response);
    }

    private String extraer(String authorization) {
        if (authorization == null || authorization.isBlank()) {
            return null;
        }
        String valor = authorization.trim();
        if (valor.toLowerCase(Locale.ROOT).startsWith(BEARER)) {
            String token = valor.substring(BEARER.length()).trim();
            return token.isEmpty() ? null : token;
        }
        return null;
    }

    private Identidad identidadDeServicio(String token) {
        Map<String, String> servicios = properties.serviceTokensMap();
        for (Map.Entry<String, String> entry : servicios.entrySet()) {
            if (compararSeguro(entry.getValue(), token)) {
                return Identidad.servicio(entry.getKey());
            }
        }
        return null;
    }

    private boolean compararSeguro(String esperado, String recibido) {
        return MessageDigest.isEqual(
                esperado.getBytes(StandardCharsets.UTF_8),
                recibido.getBytes(StandardCharsets.UTF_8));
    }

    private void rechazar(HttpServletResponse response, String detalle) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write("{\"status\":401,\"error\":\"Unauthorized\",\"detail\":\""
                + escapar(detalle) + "\"}");
    }

    private String escapar(String valor) {
        return valor == null ? "" : valor.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
