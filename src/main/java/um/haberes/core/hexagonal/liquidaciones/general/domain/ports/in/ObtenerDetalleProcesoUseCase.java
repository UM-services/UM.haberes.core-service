package um.haberes.core.hexagonal.liquidaciones.general.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.general.domain.model.EstadoLegajoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.ResultadoLegajoGeneral;

/** Detalle por legajo de un proceso masivo. */
public interface ObtenerDetalleProcesoUseCase {

    List<ResultadoLegajoGeneral> obtener(String procesoId, EstadoLegajoGeneral resultado);
}
