package um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.infrastructure.web.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
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
public class CargoClaseImputacionRequest {

    @NotNull(message = "La dependencia es obligatoria")
    private Integer dependenciaId;

    @NotNull(message = "La facultad es obligatoria")
    private Integer facultadId;

    @NotNull(message = "La geografica es obligatoria")
    private Integer geograficaId;

    @NotNull(message = "El cargo clase es obligatorio")
    private Long cargoClaseId;

    private BigDecimal cuentaSueldos;

    private BigDecimal cuentaAportes;
}
