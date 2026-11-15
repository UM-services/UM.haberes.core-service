package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in;

import java.util.Set;

import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ResultadoGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.UniversoCargos;

/**
 * Generación de los cargos de un legajo. Los métodos filtrados existen para los endpoints legacy
 * que generaban docentes y no docentes por separado.
 */
public interface GenerarCargosLegajoUseCase {

    /** Genera todos los universos del legajo/período. */
    ResultadoGeneracionCargos generar(Long legajoId, Integer anho, Integer mes);

    /** Genera sólo los universos indicados del legajo/período. */
    ResultadoGeneracionCargos generar(Set<UniversoCargos> universos, Long legajoId, Integer anho, Integer mes);
}
