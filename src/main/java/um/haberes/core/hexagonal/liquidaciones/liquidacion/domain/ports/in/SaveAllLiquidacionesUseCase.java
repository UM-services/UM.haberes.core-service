package um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;

public interface SaveAllLiquidacionesUseCase {

    List<Liquidacion> saveAllLiquidaciones(List<Liquidacion> liquidaciones, Integer version);
}
