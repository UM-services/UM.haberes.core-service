package um.haberes.core.hexagonal.liquidaciones.individual.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResultadoProcesoIndividual;

/** Eliminación de la liquidación de un legajo/período. */
public interface EliminarLiquidacionIndividualUseCase {

    ResultadoProcesoIndividual eliminar(Long legajoId, Integer anho, Integer mes, boolean forzar);
}
