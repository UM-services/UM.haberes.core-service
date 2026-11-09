package um.haberes.core.hexagonal.crossslice.liquidacion_proceso;

/**
 * Contrato de cross-slice con el motor de liquidación existente.
 *
 * <p>Sólo expone las operaciones; las reglas de habilitación las decide el slice consumidor,
 * que además relee el estado para detectar abortos (el motor legacy no informa el motivo).
 */
public interface ProcesoLiquidacionCrossSlice {

    void liquidar(Long legajoId, Integer anho, Integer mes, boolean forzar);

    void eliminarLiquidacion(Long legajoId, Integer anho, Integer mes, boolean forzar);

    void generarCargos(Long legajoId, Integer anho, Integer mes);
}
