package um.haberes.core.hexagonal.liquidaciones.individual.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.crossslice.liquidacion.LiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;
import um.haberes.core.hexagonal.crossslice.liquidacion_proceso.ProcesoLiquidacionCrossSlice;
import um.haberes.core.hexagonal.liquidaciones.individual.application.service.LiquidacionIndividualReglas;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.EstadoProcesoIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResultadoProcesoIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.ports.in.EliminarLiquidacionIndividualUseCase;

@Component
@RequiredArgsConstructor
public class EliminarLiquidacionIndividualUseCaseImpl implements EliminarLiquidacionIndividualUseCase {

    private final LiquidacionCrossSlice liquidacionCrossSlice;

    private final ProcesoLiquidacionCrossSlice procesoLiquidacionCrossSlice;

    private final LiquidacionIndividualReglas reglas;

    @Override
    public ResultadoProcesoIndividual eliminar(Long legajoId, Integer anho, Integer mes, boolean forzar) {
        Optional<LiquidacionView> liquidacion = liquidacionCrossSlice.porClaveUnica(legajoId, anho, mes);
        if (liquidacion.isEmpty()) {
            return ResultadoProcesoIndividual.de(EstadoProcesoIndividual.SIN_LIQUIDACION,
                    "No hay liquidación para eliminar en el período.");
        }
        Optional<ResultadoProcesoIndividual> bloqueo = reglas.bloquearSiCorresponde(liquidacion, forzar);
        if (bloqueo.isPresent()) {
            return bloqueo.get();
        }

        procesoLiquidacionCrossSlice.eliminarLiquidacion(legajoId, anho, mes, forzar);

        if (liquidacionCrossSlice.porClaveUnica(legajoId, anho, mes).isPresent()) {
            return ResultadoProcesoIndividual.de(EstadoProcesoIndividual.NO_ELIMINADA,
                    "La liquidación no se pudo eliminar; revise el bloqueo del período.");
        }
        return ResultadoProcesoIndividual.de(EstadoProcesoIndividual.ELIMINADA,
                "Liquidación eliminada correctamente.");
    }
}
