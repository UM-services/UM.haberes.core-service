package um.haberes.core.hexagonal.etec.control_etec.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Parámetros propios de la liquidación ETEC por puntos de un período.
 *
 * <p>Es la única fuente de verdad del valor del punto, el monto fijo del cargo, los porcentajes de
 * presentismo y adicional carrera, la garantía y el adicional de carga horaria. La liquidación
 * general sigue usando {@code control}.</p>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControlEtec {

    private Long controlEtecId;

    private Integer anho;

    private Integer mes;

    /** Valor de cada punto del cargo ETEC (se multiplica por {@code categoria.puntos}). */
    private BigDecimal valorPuntoEtec;

    /** Importe fijo que se suma al resultado de {@code puntos × valor del punto}. */
    private BigDecimal montoFijoCargo;

    /** Horas de referencia de los cargos por puntos del período. */
    private BigDecimal horasReferenciaCargo;

    /** Básico del maestro de grado del período, base de {@code vecesBasicoMaestroGrado}. */
    private BigDecimal basicoMaestroGrado;

    /**
     * Código de liquidación del adicional directivo ETEC. Es un dato del período: el código se
     * crea o elige en el catálogo y acá sólo se referencia. Nulo o cero significa que el período
     * no liquida el concepto.
     */
    private Integer codigoAdicionalDirectivo;

    /** Porcentaje de presentismo ETEC sobre el valor del cargo. */
    private BigDecimal porcentajePresentismo;

    /** Porcentaje del adicional carrera docente sobre cargo + antigüedad. */
    private BigDecimal porcentajeAdicionalCarrera;

    /** Piso de cargo + antigüedad + presentismo. */
    private BigDecimal garantia;

    /** Horas del adicional de carga horaria. */
    private BigDecimal horasAdicionalCargaHoraria;

    /** Valor de cada hora del adicional de carga horaria. */
    private BigDecimal valorHoraAdicionalCargaHoraria;

    public static final BigDecimal PORCENTAJE_PRESENTISMO_DEFAULT = new BigDecimal("20.00");

    public static final BigDecimal PORCENTAJE_ADICIONAL_CARRERA_DEFAULT = new BigDecimal("10.00");
}
