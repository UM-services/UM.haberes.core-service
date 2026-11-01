package um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.infrastructure.web.dto;

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
public class LegajoCargoClaseImputacionResponse {

    private Long legajoCargoClaseImputacionId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    private Integer dependenciaId;

    private Integer facultadId;

    private Integer geograficaId;

    private Long cargoClaseId;

    private BigDecimal cuentaSueldos;

    private BigDecimal basico;

    private BigDecimal antiguedad;

    private BigDecimal cuentaAportes;
}
