package um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.BonoLote;

/**
 * Arma en una sola pasada los datos de todos los bonos de una dependencia. Reemplaza el
 * recorrido legajo por legajo del reporte, que repetía catálogos y viajes de red.
 */
public interface ObtenerBonoLoteUseCase {

    BonoLote obtener(Integer dependenciaId, Integer anho, Integer mes, String salida);
}
