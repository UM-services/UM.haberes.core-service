package um.haberes.core.hexagonal.seguridad.infrastructure.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.haberes.core.hexagonal.seguridad.infrastructure.config.SeguridadProperties;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Emite y valida un JWT compacto firmado con HMAC-SHA256.
 *
 * <p>No se agrega una dependencia JWT: el formato es el mínimo estándar
 * (header.payload.signature) y la firma se calcula con {@link Mac}. Los claims
 * son sólo {@code sub} (legajoId), {@code iat}, {@code exp} y {@code jti}: los
 * permisos y el flag de administrador se resuelven desde la base en cada request,
 * para que un cambio de rol no quede congelado hasta el vencimiento.
 */
@Component
@RequiredArgsConstructor
public class TokenService {

    private static final String HEADER_JSON = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
    private static final long RELOJ_TOLERANCIA_SEGUNDOS = 60;
    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder DECODER = Base64.getUrlDecoder();

    private final SeguridadProperties properties;
    // Instancia propia: Spring Boot 4 no expone el ObjectMapper de Jackson 2 como bean.
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    void validarConfiguracion() {
        if (properties.isTokenRequired()) {
            if (properties.getTokenSecret() == null || properties.getTokenSecret().isBlank()) {
                throw new IllegalStateException(
                        "HABERES_AUTH_SECRET es obligatorio cuando app.seguridad.token-required=true");
            }
            if (properties.getTokenSecret().getBytes(StandardCharsets.UTF_8).length < 32) {
                throw new IllegalStateException("HABERES_AUTH_SECRET debe tener al menos 32 bytes");
            }
        }
    }

    public Token emitir(Long legajoId) {
        Instant ahora = Instant.now();
        Instant expira = ahora.plus(properties.getTokenTtlHours(), ChronoUnit.HOURS);
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("sub", String.valueOf(legajoId));
        payload.put("iat", ahora.getEpochSecond());
        payload.put("exp", expira.getEpochSecond());
        payload.put("jti", UUID.randomUUID().toString());

        try {
            String header = ENCODER.encodeToString(HEADER_JSON.getBytes(StandardCharsets.UTF_8));
            String body = ENCODER.encodeToString(objectMapper.writeValueAsBytes(payload));
            String firma = ENCODER.encodeToString(firmar(header + "." + body));
            return new Token(header + "." + body + "." + firma, expira);
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo emitir el token de sesión", e);
        }
    }

    public Identidad validar(String token) {
        if (token == null || token.isBlank()) {
            throw new TokenInvalidoException("Token ausente");
        }
        String[] partes = token.split("\\.");
        if (partes.length != 3) {
            throw new TokenInvalidoException("Token mal formado");
        }
        byte[] esperada = firmar(partes[0] + "." + partes[1]);
        byte[] recibida;
        try {
            recibida = DECODER.decode(partes[2]);
        } catch (IllegalArgumentException e) {
            throw new TokenInvalidoException("Firma inválida");
        }
        if (!MessageDigest.isEqual(esperada, recibida)) {
            throw new TokenInvalidoException("Firma inválida");
        }
        try {
            Map<String, Object> payload = objectMapper.readValue(
                    DECODER.decode(partes[1]), new TypeReference<Map<String, Object>>() {
                    });
            Object sub = payload.get("sub");
            Object exp = payload.get("exp");
            if (sub == null || exp == null) {
                throw new TokenInvalidoException("Token incompleto");
            }
            long expiracion = ((Number) exp).longValue();
            if (Instant.now().getEpochSecond() > expiracion + RELOJ_TOLERANCIA_SEGUNDOS) {
                throw new TokenInvalidoException("Token vencido");
            }
            return Identidad.usuario(Long.valueOf(sub.toString()));
        } catch (TokenInvalidoException e) {
            throw e;
        } catch (Exception e) {
            throw new TokenInvalidoException("Token inválido");
        }
    }

    private byte[] firmar(String contenido) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(
                    properties.getTokenSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return mac.doFinal(contenido.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo firmar el token", e);
        }
    }

    public record Token(String token, Instant expira) {
    }

    public static class TokenInvalidoException extends RuntimeException {
        public TokenInvalidoException(String message) {
            super(message);
        }
    }
}
