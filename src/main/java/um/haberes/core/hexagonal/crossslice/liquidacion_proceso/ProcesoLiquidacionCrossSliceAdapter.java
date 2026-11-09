package um.haberes.core.hexagonal.crossslice.liquidacion_proceso;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.service.facade.MakeLiquidacionService;

/**
 * Adaptador cross-slice contra el motor de liquidación existente.
 *
 * <p>El motor legacy queda congelado: este adaptador sólo lo invoca y no altera su firma ni su
 * comportamiento. El consumidor valida antes y relee después para no depender de su {@code void}.
 */
@Component
@RequiredArgsConstructor
public class ProcesoLiquidacionCrossSliceAdapter implements ProcesoLiquidacionCrossSlice {

    private final MakeLiquidacionService makeLiquidacionService;

    @Override
    public void liquidar(Long legajoId, Integer anho, Integer mes, boolean forzar) {
        makeLiquidacionService.liquidacionByLegajoId(legajoId, anho, mes, forzar);
    }

    @Override
    public void eliminarLiquidacion(Long legajoId, Integer anho, Integer mes, boolean forzar) {
        makeLiquidacionService.deleteLiquidacionByLegajoId(legajoId, anho, mes, forzar);
    }

    @Override
    public void generarCargos(Long legajoId, Integer anho, Integer mes) {
        makeLiquidacionService.generateCargosNoDocentes(legajoId, anho, mes);
        makeLiquidacionService.generateCargosDocentes(legajoId, anho, mes);
    }
}
