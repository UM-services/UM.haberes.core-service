package um.haberes.core.hexagonal.liquidaciones.general.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.general.domain.model.InicioProcesoGeneral;

/** Inicia la liquidación masiva del período. */
public interface IniciarProcesoLiquidarUseCase {

    InicioProcesoGeneral iniciar(Integer anho, Integer mes, boolean forzar, Long legajoSolicitante);
}
