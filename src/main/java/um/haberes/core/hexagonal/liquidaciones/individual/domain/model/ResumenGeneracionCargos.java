package um.haberes.core.hexagonal.liquidaciones.individual.domain.model;

/** Resumen de la generación de cargos de un legajo/período. */
public record ResumenGeneracionCargos(EstadoProcesoIndividual resultado, String mensaje, int docentes,
                                      int noDocentes) {
}
