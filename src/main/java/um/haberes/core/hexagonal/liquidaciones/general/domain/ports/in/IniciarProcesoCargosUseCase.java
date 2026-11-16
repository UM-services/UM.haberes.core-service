package um.haberes.core.hexagonal.liquidaciones.general.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.general.domain.model.InicioProcesoGeneral;

/** Inicia la generación masiva de cargos del período. */
public interface IniciarProcesoCargosUseCase {

    InicioProcesoGeneral iniciar(Integer anho, Integer mes, Long legajoSolicitante);
}
