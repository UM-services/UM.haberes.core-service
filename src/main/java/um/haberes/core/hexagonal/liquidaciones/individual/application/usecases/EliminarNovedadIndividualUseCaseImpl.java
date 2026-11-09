package um.haberes.core.hexagonal.liquidaciones.individual.application.usecases;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.crossslice.liquidacion.LiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;
import um.haberes.core.hexagonal.crossslice.novedad.NovedadCrossSlice;
import um.haberes.core.hexagonal.crossslice.novedad.model.NovedadView;
import um.haberes.core.hexagonal.liquidaciones.individual.application.service.LiquidacionIndividualReglas;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.EstadoProcesoIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResultadoProcesoIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.ports.in.EliminarNovedadIndividualUseCase;

@Component
@RequiredArgsConstructor
public class EliminarNovedadIndividualUseCaseImpl implements EliminarNovedadIndividualUseCase {

    private final LiquidacionCrossSlice liquidacionCrossSlice;

    private final NovedadCrossSlice novedadCrossSlice;

    private final LiquidacionIndividualReglas reglas;

    @Override
    public ResultadoProcesoIndividual eliminar(Long legajoId, Integer anho, Integer mes, Long novedadId,
                                               boolean forzar) {
        Optional<LiquidacionView> liquidacion = liquidacionCrossSlice.porClaveUnica(legajoId, anho, mes);
        Optional<ResultadoProcesoIndividual> bloqueo = reglas.bloquearSiCorresponde(liquidacion, forzar);
        if (bloqueo.isPresent()) {
            return bloqueo.get();
        }
        boolean pertenece = novedadCrossSlice.porLegajo(legajoId, anho, mes).stream()
                .map(NovedadView::novedadId)
                .anyMatch(id -> Objects.equals(id, novedadId));
        if (!pertenece) {
            return ResultadoProcesoIndividual.de(EstadoProcesoIndividual.SIN_NOVEDAD,
                    "La novedad no pertenece al legajo y período indicados.");
        }
        novedadCrossSlice.eliminar(novedadId);
        return ResultadoProcesoIndividual.de(EstadoProcesoIndividual.ELIMINADA, "Novedad eliminada.");
    }
}
