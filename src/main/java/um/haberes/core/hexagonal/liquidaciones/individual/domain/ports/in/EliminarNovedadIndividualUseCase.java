package um.haberes.core.hexagonal.liquidaciones.individual.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResultadoProcesoIndividual;

/** Baja de una novedad del legajo/período. */
public interface EliminarNovedadIndividualUseCase {

    ResultadoProcesoIndividual eliminar(Long legajoId, Integer anho, Integer mes, Long novedadId,
                                        boolean forzar);
}
