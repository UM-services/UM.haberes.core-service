package um.haberes.core.hexagonal.etec.control_etec.infrastructure.web.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ControlEtecResponse {

    private Long controlEtecId;

    private Integer anho;

    private Integer mes;

    private BigDecimal valorPuntoEtec;

    private BigDecimal montoFijoCargo;

    private BigDecimal porcentajePresentismo;

    private BigDecimal porcentajeAdicionalCarrera;

    private BigDecimal garantia;

    private BigDecimal horasAdicionalCargaHoraria;

    private BigDecimal valorHoraAdicionalCargaHoraria;
}
