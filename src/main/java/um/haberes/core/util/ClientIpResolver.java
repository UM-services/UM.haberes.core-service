package um.haberes.core.util;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Resuelve la IP real del cliente detras de nginx/gateway.
 * Orden de precedencia: X-Forwarded-For (primer valor) -> X-Real-IP -> getRemoteAddr().
 */
@Component
public class ClientIpResolver {

	private static final String X_FORWARDED_FOR = "X-Forwarded-For";
	private static final String X_REAL_IP = "X-Real-IP";

	public String resolve(HttpServletRequest request) {
		if (request == null) {
			return "";
		}
		String forwardedFor = request.getHeader(X_FORWARDED_FOR);
		if (hasText(forwardedFor)) {
			return firstForwardedValue(forwardedFor);
		}
		String realIp = request.getHeader(X_REAL_IP);
		if (hasText(realIp)) {
			return realIp.trim();
		}
		String remoteAddr = request.getRemoteAddr();
		return remoteAddr == null ? "" : remoteAddr.trim();
	}

	private String firstForwardedValue(String forwardedFor) {
		for (String candidate : forwardedFor.split(",")) {
			if (hasText(candidate)) {
				return candidate.trim();
			}
		}
		return "";
	}

	private boolean hasText(String value) {
		return value != null && !value.isBlank();
	}
}
