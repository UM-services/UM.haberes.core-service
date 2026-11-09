package um.haberes.core.hexagonal.liquidaciones.individual.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.FichaLiquidacionIndividual;

/** Lectura agregada de la pantalla de liquidación individual. */
public interface ObtenerFichaLiquidacionIndividualUseCase {

    Optional<FichaLiquidacionIndividual> obtener(Long legajoId, Integer anho, Integer mes);
}
