package um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Pedido de liquidación de un legajo/período. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LiquidarIndividualRequest {

    private Boolean forzar;
}
