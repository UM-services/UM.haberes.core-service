package um.haberes.core.hexagonal.etec.control_etec.infrastructure.web.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Parámetros editables del control ETEC. El período viaja en la ruta. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ControlEtecRequest {

    private BigDecimal valorPuntoEtec;

    private BigDecimal montoFijoCargo;

    private BigDecimal porcentajePresentismo;

    private BigDecimal porcentajeAdicionalCarrera;

    private BigDecimal garantia;

    private BigDecimal horasAdicionalCargaHoraria;

    private BigDecimal valorHoraAdicionalCargaHoraria;
}
