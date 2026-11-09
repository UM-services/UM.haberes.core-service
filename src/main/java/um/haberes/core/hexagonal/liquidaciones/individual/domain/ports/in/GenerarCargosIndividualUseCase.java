package um.haberes.core.hexagonal.liquidaciones.individual.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResumenGeneracionCargos;

/** Regeneración de los cargos docentes y no docentes de un legajo/período. */
public interface GenerarCargosIndividualUseCase {

    ResumenGeneracionCargos generar(Long legajoId, Integer anho, Integer mes);
}
