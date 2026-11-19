package um.haberes.core.hexagonal.crossslice.liquidacion;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;

/** Contrato de cross-slice para la liquidación de un legajo. */
public interface LiquidacionCrossSlice {

    Optional<LiquidacionView> porClaveUnica(Long legajoId, Integer anho, Integer mes);

    Optional<LiquidacionView> porPeriodoAnterior(Long legajoId, Integer anho, Integer mes);

    /** Todas las liquidaciones del período, sin límite. */
    List<LiquidacionView> porPeriodo(Integer anho, Integer mes);

    /** {@code true} si el período tiene al menos una liquidación acreditada. */
    boolean hayAcreditadasEnPeriodo(Integer anho, Integer mes);
}
