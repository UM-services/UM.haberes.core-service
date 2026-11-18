package um.haberes.core.hexagonal.etec.hora_escala.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Tramo de la escala de horas de clase ETEC de un período: a un rango de horas semanales le
 * corresponde un monto fijo y un valor por hora.
 *
 * <p>El importe del tramo es {@code montoFijo + horas × valorHora}, donde {@code horas} es la
 * suma de las horas semanales de los cursos ETEC (facultad 15) de una misma sede. La escala se
 * configura por período porque cuelga de {@code control_etec}.</p>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EscalaHoraEtec {

    private Long escalaHoraEtecId;

    private Long controlEtecId;

    private BigDecimal horasDesde;

    private BigDecimal horasHasta;

    private BigDecimal montoFijo;

    private BigDecimal valorHora;

    /** Indica si el tramo contiene las horas indicadas (extremos inclusive). */
    public boolean contiene(BigDecimal horas) {
        return horas != null && horasDesde != null && horasHasta != null
                && horas.compareTo(horasDesde) >= 0 && horas.compareTo(horasHasta) <= 0;
    }

    /** Importe del tramo: {@code montoFijo + horas × valorHora}. */
    public BigDecimal importe(BigDecimal horas) {
        return montoFijo.add(horas.multiply(valorHora))
                .setScale(2, java.math.RoundingMode.HALF_UP);
    }
}
