package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.web.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Reemplazo atómico de cargos de liquidación para un conjunto de categorías.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReemplazoCargosRequest {

    @NotEmpty
    @NotNull
    private List<Integer> categoriaIds;

    @NotNull
    @Valid
    private List<CargoLiquidacionRequest> cargos;
}
