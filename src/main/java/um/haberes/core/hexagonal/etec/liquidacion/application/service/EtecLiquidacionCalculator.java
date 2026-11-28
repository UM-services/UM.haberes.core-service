package um.haberes.core.hexagonal.etec.liquidacion.application.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;
import um.haberes.core.hexagonal.etec.liquidacion.domain.model.AcumuladorEtec;
import um.haberes.core.hexagonal.etec.liquidacion.domain.model.AcumuladorEtecCargo;

/**
 * Calcula los conceptos propios de la liquidación ETEC. Sólo devuelve los códigos con importe
 * distinto de cero: el motor los agrega como ítems remunerativos.
 *
 * <p>Los acumuladores de puntos y de horas se liquidan por separado y se suman por código: lo
 * calculado sobre el cargo por puntos es del cargo por puntos y lo calculado sobre el cargo por
 * horas es del cargo por horas. Un docente con ambos (o con cargos de otra facultad) acumula los
 * importes del mismo código en un único ítem.</p>
 *
 * <ul>
 *   <li>984 DESCUENTO PRESENTISMO ETEC: importe negativo del presentismo de los cargos que
 *       perdieron el presentismo del período. Acompaña al 985, que se sigue informando
 *       bruto para que el recibo muestre ambos conceptos.</li>
 *   <li>985 PRESENTISMO ETEC: suma de {@code cargo × porcentaje} por cargo.</li>
 *   <li>986 ADICIONAL CARRERA DOCENTE ETEC: suma de {@code (cargo + antigüedad) × porcentaje}.</li>
 *   <li>987 GARANTIA ETEC: dos pisos independientes que se suman.
 *       <ul>
 *         <li>Cargo por puntos: {@code max(0, garantía prorrateada − (cargo + antigüedad +
 *             presentismo))} de los cargos por puntos. El piso se prorratea por
 *             {@code horasCargo / horasReferenciaCargo}.</li>
 *         <li>Cargo por horas: el piso se prorratea por las horas de clase cuando no llegan a la
 *             dedicación completa ({@code garantía / 18 × horas}) y se compara contra la base de
 *             los cargos por horas.</li>
 *       </ul>
 *       Comparar cada origen contra su propia base evita contar las horas dos veces.</li>
 *   <li>988 ADICIONAL CARGA HORARIA ETEC: {@code valor hora × horas}. El cargo por puntos aporta
 *       las horas adicionales fijas del período y el cargo por horas aporta sus horas de clase; los
 *       dos se calculan con el mismo valor hora y se suman en un único ítem.</li>
 *   <li>989 ARRAIGO ETEC: suma de {@code cargo × porcentaje} por cargo, con el porcentaje que
 *       corresponde a la antigüedad ETEC según la escala global ({@code etec_arraigo_escala}).</li>
 *   <li>ADICIONAL DIRECTIVO ETEC: {@code básico del maestro de grado × veces} del cargo. El
 *       código de liquidación no está fijo en el código: lo define el período en
 *       {@code control_etec.codigo_adicional_directivo}.</li>
 * </ul>
 */
@Component
public class EtecLiquidacionCalculator {

    public static final int CODIGO_PRESENTISMO_ETEC = 985;

    public static final int CODIGO_DESCUENTO_PRESENTISMO_ETEC = 984;

    public static final int CODIGO_ADICIONAL_CARRERA_ETEC = 986;

    public static final int CODIGO_GARANTIA_ETEC = 987;

    public static final int CODIGO_ADICIONAL_CARGA_HORARIA_ETEC = 988;

    public static final int CODIGO_ARRAIGO_ETEC = 989;

    /** Horas de clase que corresponden a la garantía completa del período. */
    public static final BigDecimal HORAS_GARANTIA_COMPLETA_ETEC = new BigDecimal("18");

    /**
     * Códigos base que el catálogo global debe tener para liquidar cualquier legajo ETEC. El
     * adicional directivo no está acá: su código lo configura el período en
     * {@code control_etec.codigo_adicional_directivo} y se valida al liquidar.
     */
    public static final List<Integer> CODIGOS_ETEC = List.of(
            CODIGO_DESCUENTO_PRESENTISMO_ETEC,
            CODIGO_PRESENTISMO_ETEC,
            CODIGO_ADICIONAL_CARRERA_ETEC,
            CODIGO_GARANTIA_ETEC,
            CODIGO_ADICIONAL_CARGA_HORARIA_ETEC,
            CODIGO_ARRAIGO_ETEC);

    public Map<Integer, BigDecimal> calcular(AcumuladorEtec acumulado, ControlEtec control) {
        if (control == null || acumulado == null || !acumulado.hayCargos()) {
            return Map.of();
        }

        Map<Integer, BigDecimal> conceptos = new LinkedHashMap<>();
        agregar(conceptos, CODIGO_PRESENTISMO_ETEC, acumulado.getPresentismo());
        agregar(conceptos, CODIGO_DESCUENTO_PRESENTISMO_ETEC,
                acumulado.getPresentismoDescuento().negate());
        agregar(conceptos, CODIGO_ADICIONAL_CARRERA_ETEC, acumulado.getAdicionalCarrera());

        BigDecimal garantia = garantiaPuntos(acumulado, control).add(garantiaHoras(acumulado, control));
        agregar(conceptos, CODIGO_GARANTIA_ETEC, garantia);

        BigDecimal cargaHoraria = BigDecimal.ZERO;
        BigDecimal valorHora = valor(control.getValorHoraAdicionalCargaHoraria());
        // El cargo por puntos aporta las horas adicionales fijas del período.
        if (acumulado.getPuntos().hayCargos()) {
            cargaHoraria = cargaHoraria.add(
                    valor(control.getHorasAdicionalCargaHoraria()).multiply(valorHora));
        }
        // El cargo por horas aporta las horas de clase del legajo, con el mismo valor hora.
        if (acumulado.getHorasClase().compareTo(BigDecimal.ZERO) > 0) {
            cargaHoraria = cargaHoraria.add(acumulado.getHorasClase().multiply(valorHora));
        }
        agregar(conceptos, CODIGO_ADICIONAL_CARGA_HORARIA_ETEC, cargaHoraria);

        agregar(conceptos, CODIGO_ARRAIGO_ETEC, acumulado.getArraigo());

        if (acumulado.getAdicionalDirectivo().signum() > 0) {
            Integer codigoAdicionalDirectivo = control.getCodigoAdicionalDirectivo();
            if (codigoAdicionalDirectivo == null || codigoAdicionalDirectivo <= 0) {
                throw new IllegalStateException(
                        "El adicional directivo ETEC tiene importe pero el período no configuró "
                                + "control_etec.codigo_adicional_directivo");
            }
            agregar(conceptos, codigoAdicionalDirectivo, acumulado.getAdicionalDirectivo());
        }

        return conceptos;
    }

    /**
     * Piso del cargo por puntos: se compara sólo contra la base de los cargos por puntos. Si el
     * legajo sólo tiene cargo por horas no hay garantía por este origen.
     */
    private BigDecimal garantiaPuntos(AcumuladorEtec acumulado, ControlEtec control) {
        if (!acumulado.getPuntos().hayCargos()) {
            return BigDecimal.ZERO;
        }
        BigDecimal piso = prorratearPiso(valor(control.getGarantia()),
                acumulado.getHorasCargoPuntos(), valor(control.getHorasReferenciaCargo()));
        return piso(piso, base(acumulado.getPuntos()));
    }

    /**
     * La garantía del cargo por puntos se expresa para las horas de referencia del período y se
     * prorratea por las horas de los cargos: {@code garantía × horasCargo / horasReferencia}.
     * Sin horas informadas o sin referencia se conserva la garantía completa.
     */
    private BigDecimal prorratearPiso(BigDecimal piso, BigDecimal horasCargo, BigDecimal horasReferencia) {
        if (horasCargo == null || horasCargo.signum() <= 0
                || horasReferencia == null || horasReferencia.signum() <= 0) {
            return piso;
        }
        return piso.multiply(horasCargo)
                .divide(horasReferencia, 2, RoundingMode.HALF_UP);
    }

    /**
     * Piso del cargo por horas: la garantía completa corresponde a 18 horas de clase y se
     * prorratea hacia abajo con {@code garantía / 18 × horas}. Desde 18 horas se usa el piso
     * completo, sin prorratear hacia arriba.
     */
    private BigDecimal garantiaHoras(AcumuladorEtec acumulado, ControlEtec control) {
        if (!acumulado.getHoras().hayCargos()) {
            return BigDecimal.ZERO;
        }
        BigDecimal horas = acumulado.getHorasClase();
        BigDecimal piso = valor(control.getGarantia());
        if (horas.compareTo(HORAS_GARANTIA_COMPLETA_ETEC) < 0) {
            piso = piso.multiply(horas)
                    .divide(HORAS_GARANTIA_COMPLETA_ETEC, 2, RoundingMode.HALF_UP);
        }
        return piso(piso, base(acumulado.getHoras()));
    }

    private BigDecimal base(AcumuladorEtecCargo acumulador) {
        return acumulador.getCargo()
                .add(acumulador.getAntiguedad())
                .add(acumulador.getPresentismo());
    }

    private BigDecimal piso(BigDecimal piso, BigDecimal base) {
        return piso.subtract(base).max(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
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
