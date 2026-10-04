package um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;

public interface GetLiquidacionesByDependenciaUseCase {

    List<Liquidacion> getLiquidacionesByDependencia(Integer dependenciaId, Integer anho, Integer mes, String salida);
}
