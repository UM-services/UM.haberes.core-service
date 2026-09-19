package um.haberes.core.model.internal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AfipContext {

    private Long legajoId = null;

    private String cuil = "";

    private BigDecimal importeDetraer = BigDecimal.ZERO;

    private BigDecimal baseImponible10 = BigDecimal.ZERO;

    private BigDecimal baseParaElCalculoDiferencialDeContribucionesDeSeguridadSocial = BigDecimal.ZERO;

    private BigDecimal baseParaElCalculoDiferencialDeAporteDeSeguridadSocial = BigDecimal.ZERO;

    private BigDecimal baseImponible9 = BigDecimal.ZERO;

    private BigDecimal baseImponible8 = BigDecimal.ZERO;

    private BigDecimal baseImponible7 = BigDecimal.ZERO;

    private BigDecimal baseImponible6 = BigDecimal.ZERO;

    private BigDecimal baseImponible5 = BigDecimal.ZERO;

    private BigDecimal baseImponible4 = BigDecimal.ZERO;

    private BigDecimal baseImponible3 = BigDecimal.ZERO;

    private BigDecimal baseImponible2 = BigDecimal.ZERO;

    private BigDecimal baseImponible1 = BigDecimal.ZERO;

    private BigDecimal remuneracionBruta = BigDecimal.ZERO;

    private BigDecimal remuneracionMaternidadANSeS = BigDecimal.ZERO;

    private BigDecimal baseCalculoDiferencialLRT = BigDecimal.ZERO;

    private BigDecimal baseCalculoDiferencialOSyFSR = BigDecimal.ZERO;

    private BigDecimal baseCalculoDiferencialAportesOSyFSR = BigDecimal.ZERO;

    private BigDecimal contribucionAdicionalOS = BigDecimal.ZERO;

    private BigDecimal aporteAdicionalOS = BigDecimal.ZERO;

    private int cantidadAdherentes = 0;

    private long codigoObraSocial = 0;

    private BigDecimal contribucionTareaDiferencial = BigDecimal.ZERO;

    private BigDecimal porcentajeAporteAdicionalSS = BigDecimal.ZERO;

    private int horasTrabajadas = 0;

    private int cantidadDiasTrabajados = 0;

    private int diaInicioSituacionRevista3 = 0;

    private int situacionRevista3 = 0;

    private int diaInicioSituacionRevista2 = 0;

    private int situacionRevista2 = 0;

    private int diaInicioSituacionRevista1 = 0;

    private int situacionRevista1 = 0;

    private int codigoLocalidad = 0;

    private int codigoSiniestrado = 0;

    private int codigoModalidadContratacion = 0;

    private int codigoActividad = 0;

    private int codigoCondicion = 0;

    private int codigoSituacion = 0;

    private int tipoOperacion = 0;

    private int tipoEmpresa = 0;

    private int marcaCorrespondeReduccion = 0;

    private int marcaSCVO = 0;

    private int marcaCCT = 0;

    private int cantidadHijos = 0;

    private int conyuge = 0;
}
