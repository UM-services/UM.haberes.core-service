package um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.infrastructure.web.dto;

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
public class CargoClaseImputacionResponse {

    private Long cargoClaseImputacionId;

    private Integer dependenciaId;

    private Integer facultadId;

    private Integer geograficaId;

    private Long cargoClaseId;

    private BigDecimal cuentaSueldos;

    private BigDecimal cuentaAportes;
}
