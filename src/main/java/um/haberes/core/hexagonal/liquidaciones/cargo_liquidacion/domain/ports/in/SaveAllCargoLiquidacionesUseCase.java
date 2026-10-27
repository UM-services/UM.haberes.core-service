package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;

public interface SaveAllCargoLiquidacionesUseCase {

    /**
     * Guarda los cargos y, siempre, una copia en {@code cargo_liquidacion_version}. Si
     * {@code version} es {@code null} se usa el siguiente número por legajo/período.
     */
    List<CargoLiquidacion> saveAllCargoLiquidaciones(List<CargoLiquidacion> cargos, Integer version);
}
