package um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;

public interface GetLiquidacionesBySemestreLegajoUseCase {

    List<Liquidacion> getLiquidacionesBySemestreLegajo(Integer anho, Integer semestre, Long legajoId, Integer limit);
}
