package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacionVersion;

public interface CargoLiquidacionVersionRepository {

    CargoLiquidacionVersion save(CargoLiquidacionVersion cargoLiquidacionVersion);

    List<CargoLiquidacionVersion> saveAll(List<CargoLiquidacionVersion> cargoLiquidacionVersions);

    /** Última versión guardada del legajo/período (para calcular la siguiente). */
    Optional<CargoLiquidacionVersion> findLastVersion(Long legajoId, Integer anho, Integer mes);
}
