package um.haberes.core.hexagonal.liquidaciones.sijp.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Registro de seguridad social del archivo SIJP (60 campos, 499 caracteres).
 *
 * El orden y los nombres replican {@code bonos.vb6/clsTXTSeguridadSocial.cls}; el layout se
 * renderiza en {@code SijpLayout}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroSijp {

    @Builder.Default
    private String cuil = "";

    @Builder.Default
    private String apellidoNombre = "";

    @Builder.Default
    private int conyuge = 0;

    @Builder.Default
    private int cantidadHijos = 0;

    @Builder.Default
    private int codigoSituacion = 0;

    @Builder.Default
    private int codigoCondicion = 0;

    @Builder.Default
    private int codigoActividad = 0;

    @Builder.Default
    private int codigoZona = 0;

    @Builder.Default
    private BigDecimal porcentajeAporteAdicionalSs = BigDecimal.ZERO;

    @Builder.Default
    private int codigoModalidadContratacion = 0;

    @Builder.Default
    private long codigoObraSocial = 0L;

    @Builder.Default
    private int cantidadAdherentes = 0;

    @Builder.Default
    private BigDecimal remuneracionTotal = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal remuneracionImponible1 = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal asignacionesFamiliaresPagadas = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal importeAporteVoluntario = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal importeAdicionalOs = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal importeExcedenteAportesSs = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal importeExcedenteAportesOs = BigDecimal.ZERO;

    @Builder.Default
    private String provinciaLocalidad = "";

    @Builder.Default
    private BigDecimal remuneracionImponible2 = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal remuneracionImponible3 = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal remuneracionImponible4 = BigDecimal.ZERO;

    @Builder.Default
    private int codigoSiniestrado = 0;

    @Builder.Default
    private String marcaCorrespondeReduccion = " ";

    @Builder.Default
    private BigDecimal capitalRecomposicionLrt = BigDecimal.ZERO;

    @Builder.Default
    private int tipoEmpleado = 1;

    @Builder.Default
    private BigDecimal aporteAdicionalObraSocial = BigDecimal.ZERO;

    @Builder.Default
    private int regimen = 1;

    @Builder.Default
    private int situacionRevista1 = 0;

    @Builder.Default
    private int diaInicioSituacionRevista1 = 0;

    @Builder.Default
    private int situacionRevista2 = 0;

    @Builder.Default
    private int diaInicioSituacionRevista2 = 0;

    @Builder.Default
    private int situacionRevista3 = 0;

    @Builder.Default
    private int diaInicioSituacionRevista3 = 0;

    @Builder.Default
    private BigDecimal sueldoAdicionales = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal sac = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal horasExtras = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal zonaDesfavorable = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal vacaciones = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal cantidadDiasTrabajados = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal remuneracionImponible5 = BigDecimal.ZERO;

    @Builder.Default
    private int trabajadorConvencionado = 0;

    @Builder.Default
    private BigDecimal remuneracionImponible6 = BigDecimal.ZERO;

    @Builder.Default
    private int tipoOperacion = 0;

    @Builder.Default
    private BigDecimal adicionales = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal premios = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal remuneracion8 = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal remuneracionImponible7 = BigDecimal.ZERO;

    @Builder.Default
    private int cantidadHorasExtras = 0;

    @Builder.Default
    private BigDecimal conceptosNoRemunerativos = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal maternidad = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal rectificacionRemuneracion = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal remuneracionImponible9 = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal contribucionTareaDiferencial = BigDecimal.ZERO;

    @Builder.Default
    private int horasTrabajadas = 0;

    @Builder.Default
    private int seguroColectivoVidaObligatorio = 0;

    @Builder.Default
    private BigDecimal importeDetraccion = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal incrementoSalarial = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal remuneracionImponible11 = BigDecimal.ZERO;

}
