package um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.ports.in;

public interface DeleteLegajoCategoriaImputacionesByPeriodoUseCase {

    void deleteLegajoCategoriaImputacionesByPeriodo(Integer anho, Integer mes);
}
