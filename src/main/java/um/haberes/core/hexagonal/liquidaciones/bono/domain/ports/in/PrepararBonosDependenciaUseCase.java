package um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.ResumenPreparacionBonos;

/**
 * Prepara la impresión masiva de los bonos de una dependencia: recalcula la actividad y
 * registra la auditoría de cada legajo, resolviendo la IP y el solicitante en el servidor.
 */
public interface PrepararBonosDependenciaUseCase {

    ResumenPreparacionBonos preparar(Integer dependenciaId, Integer anho, Integer mes, String salida,
                                     Long legajoIdSolicitud, String ipAddress);
}
