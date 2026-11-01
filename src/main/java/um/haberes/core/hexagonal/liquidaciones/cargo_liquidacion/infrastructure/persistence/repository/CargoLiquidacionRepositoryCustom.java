package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.persistence.repository;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.persistence.entity.CargoLiquidacionEntity;

public interface CargoLiquidacionRepositoryCustom {

    List<CargoLiquidacionEntity> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);
}
