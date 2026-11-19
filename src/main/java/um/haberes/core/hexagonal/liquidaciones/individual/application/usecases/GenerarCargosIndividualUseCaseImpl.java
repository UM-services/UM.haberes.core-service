package um.haberes.core.hexagonal.liquidaciones.individual.application.usecases;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.CargoLiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.model.CargoLiquidacionView;
import um.haberes.core.hexagonal.crossslice.liquidacion.LiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;
import um.haberes.core.hexagonal.crossslice.liquidacion_proceso.ProcesoLiquidacionCrossSlice;
import um.haberes.core.hexagonal.liquidaciones.individual.application.service.LiquidacionIndividualReglas;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.EstadoProcesoIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResumenGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResultadoProcesoIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.ports.in.GenerarCargosIndividualUseCase;

@Component
@RequiredArgsConstructor
public class GenerarCargosIndividualUseCaseImpl implements GenerarCargosIndividualUseCase {

    private final LiquidacionCrossSlice liquidacionCrossSlice;

    private final CargoLiquidacionCrossSlice cargoLiquidacionCrossSlice;

    private final ProcesoLiquidacionCrossSlice procesoLiquidacionCrossSlice;

    private final LiquidacionIndividualReglas reglas;

    @Override
    public ResumenGeneracionCargos generar(Long legajoId, Integer anho, Integer mes) {
        Optional<LiquidacionView> liquidacion = liquidacionCrossSlice.porClaveUnica(legajoId, anho, mes);

        // RN-01: con la liquidación bloqueada no se regeneran cargos, ni siquiera con «Forzar».
        Optional<ResultadoProcesoIndividual> bloqueo = reglas.bloquearSiCorresponde(liquidacion, false);
        if (bloqueo.isPresent()) {
            return new ResumenGeneracionCargos(bloqueo.get().resultado(), bloqueo.get().mensaje(), 0, 0);
        }
        // RN-02: sin control del período la generación no tiene modo de liquidación.
        if (!reglas.hayControl(anho, mes)) {
            return new ResumenGeneracionCargos(EstadoProcesoIndividual.SIN_CONTROL,
                    "Faltan datos de control del período.", 0, 0);
        }

        procesoLiquidacionCrossSlice.generarCargos(legajoId, anho, mes);

        List<CargoLiquidacionView> cargos = cargoLiquidacionCrossSlice.porLegajo(legajoId, anho, mes);
        int docentes = (int) cargos.stream().filter(CargoLiquidacionView::docente).count();
        int noDocentes = cargos.size() - docentes;
        return new ResumenGeneracionCargos(EstadoProcesoIndividual.GENERADOS,
                "Cargos generados: " + docentes + " docente(s) y " + noDocentes + " no docente(s).",
                docentes, noDocentes);
    }
}
