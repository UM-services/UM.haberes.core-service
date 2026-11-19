package um.haberes.core.hexagonal.liquidaciones.individual.domain.model;

import java.math.BigDecimal;

/**
 * Comando para registrar (alta o actualización) una novedad del legajo/período.
 *
 * <p>El par {@code importe}/{@code valor} conserva la semántica legada: si {@code valor} es
 * {@code "S"} o {@code "N"}, el importe se guarda en cero y el texto en {@code value}.
 */
public record RegistroNovedadIndividual(Integer codigoId, BigDecimal importe, String valor,
                                        String observaciones, boolean forzar) {
}
