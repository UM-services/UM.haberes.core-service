package um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Resultado de la generación de cargos, con los conteos por tipo. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumenGeneracionCargosResponse {

    private String resultado;

    private String mensaje;

    private Integer docentes;

    private Integer noDocentes;
}
