package um.haberes.core.hexagonal.liquidaciones.general.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.general.domain.model.InicioProcesoGeneral;

/** Inicia la eliminación masiva de liquidaciones del período. */
public interface IniciarProcesoEliminarUseCase {

    InicioProcesoGeneral eliminar(Integer anho, Integer mes, boolean forzar, Long legajoSolicitante);
}
