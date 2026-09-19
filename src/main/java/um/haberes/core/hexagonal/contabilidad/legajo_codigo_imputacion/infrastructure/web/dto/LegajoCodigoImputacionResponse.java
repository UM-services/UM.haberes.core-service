package um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.infrastructure.web.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LegajoCodigoImputacionResponse {

    private Long legajoCodigoImputacionId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    private Integer dependenciaId;

    private Integer facultadId;

    private Integer geograficaId;

    private Integer codigoId;

    private BigDecimal cuentaSueldos;

    private BigDecimal importe;

    private BigDecimal cuentaAportes;
}
