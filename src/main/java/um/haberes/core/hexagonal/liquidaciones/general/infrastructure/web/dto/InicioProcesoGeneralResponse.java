package um.haberes.core.hexagonal.liquidaciones.general.infrastructure.web.dto;

/**
 * Respuesta de inicio de un proceso.
 *
 * <p>{@code iniciado = true} (HTTP 202) implica que {@code proceso} viene informado. Si no se
 * pudo iniciar (HTTP 200), {@code motivo} explica la causa.</p>
 */
public record InicioProcesoGeneralResponse(boolean iniciado, ProcesoGeneralResponse proceso,
                                           String motivo, String mensaje) {
}
