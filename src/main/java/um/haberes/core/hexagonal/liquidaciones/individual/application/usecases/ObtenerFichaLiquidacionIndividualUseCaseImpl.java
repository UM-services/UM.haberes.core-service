package um.haberes.core.hexagonal.liquidaciones.individual.application.usecases;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.crossslice.antiguedad.AntiguedadCrossSlice;
import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.CargoLiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.model.CargoLiquidacionView;
import um.haberes.core.hexagonal.crossslice.cargos_clase.CargoClaseCrossSlice;
import um.haberes.core.hexagonal.crossslice.control.ControlCrossSlice;
import um.haberes.core.hexagonal.crossslice.control.model.ControlView;
import um.haberes.core.hexagonal.crossslice.item.ItemCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.LiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;
import um.haberes.core.hexagonal.crossslice.novedad.NovedadCrossSlice;
import um.haberes.core.hexagonal.crossslice.novedad.model.NovedadView;
import um.haberes.core.hexagonal.crossslice.persona.PersonaCrossSlice;
import um.haberes.core.hexagonal.crossslice.persona.model.PersonaView;
import um.haberes.core.hexagonal.liquidaciones.individual.application.service.LiquidacionIndividualReglas;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.FichaLiquidacionIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.ports.in.ObtenerFichaLiquidacionIndividualUseCase;

@Component
@RequiredArgsConstructor
public class ObtenerFichaLiquidacionIndividualUseCaseImpl implements ObtenerFichaLiquidacionIndividualUseCase {

    private final PersonaCrossSlice personaCrossSlice;

    private final ControlCrossSlice controlCrossSlice;

    private final LiquidacionCrossSlice liquidacionCrossSlice;

    private final AntiguedadCrossSlice antiguedadCrossSlice;

    private final CargoLiquidacionCrossSlice cargoLiquidacionCrossSlice;

    private final CargoClaseCrossSlice cargoClaseCrossSlice;

    private final NovedadCrossSlice novedadCrossSlice;

    private final ItemCrossSlice itemCrossSlice;

    private final LiquidacionIndividualReglas reglas;

    @Override
    public Optional<FichaLiquidacionIndividual> obtener(Long legajoId, Integer anho, Integer mes) {
        Optional<PersonaView> persona = personaCrossSlice.porLegajo(legajoId);
        if (persona.isEmpty()) {
            return Optional.empty();
        }
        Optional<ControlView> control = controlCrossSlice.porPeriodo(anho, mes);
        Optional<LiquidacionView> liquidacion = liquidacionCrossSlice.porClaveUnica(legajoId, anho, mes);

        List<CargoLiquidacionView> cargos = cargoLiquidacionCrossSlice.porLegajo(legajoId, anho, mes);
        List<CargoLiquidacionView> docentes = cargos.stream().filter(CargoLiquidacionView::docente).toList();
        List<CargoLiquidacionView> noDocentes = cargos.stream().filter(cargo -> !cargo.docente()).toList();
        List<NovedadView> novedades = novedadCrossSlice.porLegajo(legajoId, anho, mes);

        return Optional.of(new FichaLiquidacionIndividual(
                legajoId, anho, mes,
                persona.get(),
                control.orElse(null),
                liquidacion.orElse(null),
                antiguedadCrossSlice.porLegajo(legajoId, anho, mes),
                docentes,
                noDocentes,
                cargoClaseCrossSlice.porLegajo(legajoId, anho, mes),
                novedades,
                itemCrossSlice.porLegajo(legajoId, anho, mes),
                reglas.acciones(liquidacion, control.isPresent())));
    }
}
