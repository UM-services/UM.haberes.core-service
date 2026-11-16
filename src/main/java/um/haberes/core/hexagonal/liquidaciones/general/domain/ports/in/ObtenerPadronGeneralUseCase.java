package um.haberes.core.hexagonal.liquidaciones.general.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.general.domain.model.PadronLiquidacionGeneral;

/** Padrón agregado de liquidación general de un período. */
public interface ObtenerPadronGeneralUseCase {

    PadronLiquidacionGeneral obtener(Integer anho, Integer mes);
}
