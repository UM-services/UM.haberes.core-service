package um.haberes.core.hexagonal.liquidaciones.individual.domain.model;

import um.haberes.core.hexagonal.crossslice.novedad.model.NovedadView;

/** Resultado de registrar una novedad. */
public record NovedadRegistradaIndividual(EstadoProcesoIndividual resultado, String mensaje,
                                          NovedadView novedad) {
}
