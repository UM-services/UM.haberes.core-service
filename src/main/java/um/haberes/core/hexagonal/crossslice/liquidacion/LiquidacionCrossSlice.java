package um.haberes.core.hexagonal.crossslice.liquidacion;

import java.util.Optional;

import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;

/** Contrato de cross-slice para la liquidación de un legajo. */
public interface LiquidacionCrossSlice {

    Optional<LiquidacionView> porClaveUnica(Long legajoId, Integer anho, Integer mes);

    Optional<LiquidacionView> porPeriodoAnterior(Long legajoId, Integer anho, Integer mes);
}
