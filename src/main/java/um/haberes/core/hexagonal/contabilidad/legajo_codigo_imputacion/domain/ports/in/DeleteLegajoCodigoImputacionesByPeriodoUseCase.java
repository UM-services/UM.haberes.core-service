package um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.ports.in;

public interface DeleteLegajoCodigoImputacionesByPeriodoUseCase {

    void deleteLegajoCodigoImputacionesByPeriodo(Integer anho, Integer mes);
}
