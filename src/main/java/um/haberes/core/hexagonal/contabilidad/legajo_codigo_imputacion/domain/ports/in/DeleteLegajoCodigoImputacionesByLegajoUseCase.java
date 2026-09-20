package um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.ports.in;

public interface DeleteLegajoCodigoImputacionesByLegajoUseCase {

    void deleteLegajoCodigoImputacionesByLegajo(Long legajoId, Integer anho, Integer mes);
}
