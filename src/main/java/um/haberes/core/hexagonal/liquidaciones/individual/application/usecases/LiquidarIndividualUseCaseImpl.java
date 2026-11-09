package um.haberes.core.hexagonal.liquidaciones.individual.application.usecases;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.crossslice.liquidacion.LiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;
import um.haberes.core.hexagonal.crossslice.liquidacion_proceso.ProcesoLiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.novedad.NovedadCrossSlice;
import um.haberes.core.hexagonal.crossslice.novedad.model.NovedadView;
import um.haberes.core.hexagonal.liquidaciones.individual.application.service.LiquidacionIndividualReglas;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.EstadoProcesoIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResultadoProcesoIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.ports.in.LiquidarIndividualUseCase;

@Component
@RequiredArgsConstructor
public class LiquidarIndividualUseCaseImpl implements LiquidarIndividualUseCase {

    private final LiquidacionCrossSlice liquidacionCrossSlice;

    private final NovedadCrossSlice novedadCrossSlice;

    private final ProcesoLiquidacionCrossSlice procesoLiquidacionCrossSlice;

    private final LiquidacionIndividualReglas reglas;

    @Override
    public ResultadoProcesoIndividual liquidar(Long legajoId, Integer anho, Integer mes, boolean forzar) {
        Optional<LiquidacionView> liquidacion = liquidacionCrossSlice.porClaveUnica(legajoId, anho, mes);

        Optional<ResultadoProcesoIndividual> bloqueo = reglas.bloquearSiCorresponde(liquidacion, forzar);
        if (bloqueo.isPresent()) {
            return bloqueo.get();
        }
        if (!reglas.hayControl(anho, mes)) {
            return ResultadoProcesoIndividual.de(EstadoProcesoIndividual.SIN_CONTROL,
                    "Faltan datos de control del período.");
        }
        Optional<ResultadoProcesoIndividual> persona = reglas.validarPersona(legajoId, anho, mes);
        if (persona.isPresent()) {
            return persona.get();
        }
        List<NovedadView> novedades = novedadCrossSlice.porLegajo(legajoId, anho, mes);
        if (!reglas.periodoLiquidable(legajoId, anho, mes, novedades)) {
            return ResultadoProcesoIndividual.de(EstadoProcesoIndividual.PERIODO_NO_LIQUIDABLE,
                    "El período no tiene cargos ni novedades liquidables.");
        }

        procesoLiquidacionCrossSlice.liquidar(legajoId, anho, mes, forzar);

        // El motor no informa el motivo: se verifica el efecto antes de declarar éxito.
        if (liquidacionCrossSlice.porClaveUnica(legajoId, anho, mes).isEmpty()) {
            return ResultadoProcesoIndividual.de(EstadoProcesoIndividual.NO_LIQUIDABLE,
                    "No se generó la liquidación del legajo; revise los datos del período.");
        }
        return ResultadoProcesoIndividual.de(EstadoProcesoIndividual.LIQUIDADO,
                "Liquidación generada correctamente.");
    }
}
