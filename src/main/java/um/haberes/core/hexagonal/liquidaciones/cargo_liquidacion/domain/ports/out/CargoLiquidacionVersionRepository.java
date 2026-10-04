package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacionVersion;

public interface CargoLiquidacionVersionRepository {

    CargoLiquidacionVersion save(CargoLiquidacionVersion cargoLiquidacionVersion);

    List<CargoLiquidacionVersion> saveAll(List<CargoLiquidacionVersion> cargoLiquidacionVersions);
}
