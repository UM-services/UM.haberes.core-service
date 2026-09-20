package um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;

public interface GetLiquidacionesBySemestreUseCase {

    List<Liquidacion> getLiquidacionesBySemestre(Integer anho, Integer semestre, Integer limit);
}
