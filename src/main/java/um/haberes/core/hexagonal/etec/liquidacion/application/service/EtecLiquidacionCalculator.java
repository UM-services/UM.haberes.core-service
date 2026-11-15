package um.haberes.core.hexagonal.etec.liquidacion.application.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;
import um.haberes.core.hexagonal.etec.liquidacion.domain.model.AcumuladorEtecCargo;

/**
 * Calcula los conceptos propios de la liquidación ETEC por puntos. Sólo devuelve los códigos con
 * importe distinto de cero: el motor los agrega como ítems remunerativos.
 *
 * <ul>
 *   <li>985 PRESENTISMO ETEC: suma de {@code cargo × porcentaje} por cargo.</li>
 *   <li>986 ADICIONAL CARRERA DOCENTE ETEC: suma de {@code (cargo + antigüedad) × porcentaje}.</li>
 *   <li>987 GARANTIA ETEC: {@code max(0, garantía − (cargo + antigüedad + presentismo))}.</li>
 *   <li>988 ADICIONAL CARGA HORARIA ETEC: {@code horas × valor hora}.</li>
 *   <li>989 ARRAIGO ETEC: suma de {@code cargo × porcentaje} por cargo, con el porcentaje que
 *       corresponde a la antigüedad docente según la escala global ({@code etec_arraigo_escala}).</li>
 * </ul>
 */
@Component
public class EtecLiquidacionCalculator {

    public static final int CODIGO_PRESENTISMO_ETEC = 985;

    public static final int CODIGO_ADICIONAL_CARRERA_ETEC = 986;

    public static final int CODIGO_GARANTIA_ETEC = 987;

    public static final int CODIGO_ADICIONAL_CARGA_HORARIA_ETEC = 988;

    public static final int CODIGO_ARRAIGO_ETEC = 989;

    public Map<Integer, BigDecimal> calcular(AcumuladorEtecCargo acumulado, ControlEtec control) {
        if (control == null || acumulado == null || !acumulado.hayCargos()) {
            return Map.of();
        }

        Map<Integer, BigDecimal> conceptos = new LinkedHashMap<>();
        agregar(conceptos, CODIGO_PRESENTISMO_ETEC, acumulado.getPresentismo());
        agregar(conceptos, CODIGO_ADICIONAL_CARRERA_ETEC, acumulado.getAdicionalCarrera());

        BigDecimal base = acumulado.getCargo()
                .add(acumulado.getAntiguedad())
                .add(acumulado.getPresentismo());
        BigDecimal garantia = valor(control.getGarantia()).subtract(base).max(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP);
        agregar(conceptos, CODIGO_GARANTIA_ETEC, garantia);

        BigDecimal cargaHoraria = valor(control.getHorasAdicionalCargaHoraria())
                .multiply(valor(control.getValorHoraAdicionalCargaHoraria()))
                .setScale(2, RoundingMode.HALF_UP);
        agregar(conceptos, CODIGO_ADICIONAL_CARGA_HORARIA_ETEC, cargaHoraria);

        agregar(conceptos, CODIGO_ARRAIGO_ETEC, acumulado.getArraigo());

        return conceptos;
    }

    private void agregar(Map<Integer, BigDecimal> conceptos, int codigoId, BigDecimal importe) {
        if (importe != null && importe.compareTo(BigDecimal.ZERO) != 0) {
            conceptos.put(codigoId, importe.setScale(2, RoundingMode.HALF_UP));
        }
    }

    private BigDecimal valor(BigDecimal importe) {
        return importe == null ? BigDecimal.ZERO : importe;
    }
}
