package um.haberes.core.hexagonal.liquidaciones.general.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.general.domain.model.ProcesoGeneral;

/** Procesos masivos ejecutados en un período. */
public interface ListarProcesosPeriodoUseCase {

    List<ProcesoGeneral> listar(Integer anho, Integer mes);
}
