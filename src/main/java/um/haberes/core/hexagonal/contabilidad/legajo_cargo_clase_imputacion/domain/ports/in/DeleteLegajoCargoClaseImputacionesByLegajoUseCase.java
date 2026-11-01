package um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.ports.in;

public interface DeleteLegajoCargoClaseImputacionesByLegajoUseCase {

    void deleteLegajoCargoClaseImputacionesByLegajo(Long legajoId, Integer anho, Integer mes);
}
