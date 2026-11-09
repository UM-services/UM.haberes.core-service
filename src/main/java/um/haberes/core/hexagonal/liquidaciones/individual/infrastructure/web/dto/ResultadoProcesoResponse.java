package um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Resultado de una acción de liquidación individual. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoProcesoResponse {

    private String resultado;

    private String mensaje;
}
