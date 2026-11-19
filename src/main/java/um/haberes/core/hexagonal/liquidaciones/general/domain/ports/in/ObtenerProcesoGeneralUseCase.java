package um.haberes.core.hexagonal.liquidaciones.general.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.general.domain.model.ProcesoGeneral;

/** Consulta del estado de un proceso masivo. */
public interface ObtenerProcesoGeneralUseCase {

    Optional<ProcesoGeneral> obtener(String procesoId);
}
