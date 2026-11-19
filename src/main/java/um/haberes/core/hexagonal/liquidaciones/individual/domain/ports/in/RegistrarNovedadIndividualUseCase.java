package um.haberes.core.hexagonal.liquidaciones.individual.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.NovedadRegistradaIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.RegistroNovedadIndividual;

/** Alta o actualización de una novedad del legajo/período. */
public interface RegistrarNovedadIndividualUseCase {

    NovedadRegistradaIndividual registrar(Long legajoId, Integer anho, Integer mes,
                                          RegistroNovedadIndividual registro);
}
