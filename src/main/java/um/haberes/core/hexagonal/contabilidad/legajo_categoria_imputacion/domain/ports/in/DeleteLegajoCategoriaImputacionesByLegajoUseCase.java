package um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.ports.in;

public interface DeleteLegajoCategoriaImputacionesByLegajoUseCase {

    void deleteLegajoCategoriaImputacionesByLegajo(Long legajoId, Integer anho, Integer mes);
}
