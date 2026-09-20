package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;

public interface GetCargosDocentesByLegajoAndFacultadUseCase {

    List<CargoLiquidacion> getCargosDocentesByLegajoAndFacultad(Long legajoId, Integer anho, Integer mes,
            Integer facultadId);
}
