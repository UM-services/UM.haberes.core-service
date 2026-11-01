package um.haberes.core.hexagonal.contabilidad.categoria_imputacion.infrastructure.web.dto;

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
public class CategoriaImputacionRequest {

    @NotNull(message = "La dependencia es obligatoria")
    private Integer dependenciaId;

    @NotNull(message = "La facultad es obligatoria")
    private Integer facultadId;

    @NotNull(message = "La geografica es obligatoria")
    private Integer geograficaId;

    @NotNull(message = "La categoria es obligatoria")
    private Integer categoriaId;

    private BigDecimal cuentaSueldos;

    private BigDecimal cuentaAportes;
}
