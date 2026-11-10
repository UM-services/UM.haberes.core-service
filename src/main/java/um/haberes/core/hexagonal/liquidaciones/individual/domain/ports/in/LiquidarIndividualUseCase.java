package um.haberes.core.hexagonal.liquidaciones.individual.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResultadoProcesoIndividual;

/** Cálculo y persistencia de la liquidación de un legajo/período. */
public interface LiquidarIndividualUseCase {

    ResultadoProcesoIndividual liquidar(Long legajoId, Integer anho, Integer mes, boolean forzar);
}
