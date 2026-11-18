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

    private BigDecimal horasDesde;

    private BigDecimal horasHasta;

    private BigDecimal montoFijo;

    private BigDecimal valorHora;
}
