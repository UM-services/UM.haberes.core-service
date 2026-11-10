package um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Resultado de registrar una novedad, con la novedad resultante. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NovedadRegistradaResponse {

    private String resultado;

    private String mensaje;

    private FichaLiquidacionIndividualResponse.Novedad novedad;
}
