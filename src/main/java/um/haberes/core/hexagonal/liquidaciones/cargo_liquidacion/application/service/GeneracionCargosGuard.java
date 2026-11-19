package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.service;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.crossslice.liquidacion.LiquidacionCrossSlice;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.exception.CargoLiquidacionException;

/**
 * Guarda de generación sobre períodos ya cerrados.
 *
 * <p>Sólo la aplican los circuitos nuevos de generación ETEC: una vez acreditado el período, generar
 * cargos dejaría filas que no se corresponden con lo pagado. El flujo de liquidación individual
 * conserva su propia regla de bloqueo ({@code liquidacion.bloqueado}).</p>
 */
@Component
@RequiredArgsConstructor
public class GeneracionCargosGuard {

    private final LiquidacionCrossSlice liquidacionCrossSlice;

    public void validarLegajo(Long legajoId, Integer anho, Integer mes) {
        liquidacionCrossSlice.porClaveUnica(legajoId, anho, mes)
                .filter(liquidacion -> liquidacion.fechaAcreditacion() != null)
                .ifPresent(liquidacion -> {
                    throw new CargoLiquidacionException("El período " + anho + "/" + mes
                            + " ya está acreditado para el legajo " + legajoId
                            + "; no se pueden generar cargos");
                });
    }

    public void validarPeriodo(Integer anho, Integer mes) {
        if (liquidacionCrossSlice.hayAcreditadasEnPeriodo(anho, mes)) {
            throw new CargoLiquidacionException("El período " + anho + "/" + mes
                    + " ya tiene liquidaciones acreditadas; no se pueden generar cargos");
        }
    }
}
