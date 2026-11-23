package um.haberes.core.hexagonal.etec.hora_escala.infrastructure.web.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Tramo de la escala de horas ETEC. La escala se envía completa al reemplazarla. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EscalaHoraEtecRequest {

    /** Categoría con la que se liquida el tramo (marcada como {@code etec_horas}). */
    private Integer categoriaId;

    private BigDecimal horasDesde;

    private BigDecimal horasHasta;

    private BigDecimal montoFijo;

    private BigDecimal valorHora;
}
