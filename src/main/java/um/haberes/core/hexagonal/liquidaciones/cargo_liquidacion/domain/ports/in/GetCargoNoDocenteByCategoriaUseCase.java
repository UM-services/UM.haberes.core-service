package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;

public interface GetCargoNoDocenteByCategoriaUseCase {

    Optional<CargoLiquidacion> getCargoNoDocenteByCategoria(Long legajoId, Integer anho, Integer mes,
            Integer categoriaId);
}
