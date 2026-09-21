package um.haberes.core.hexagonal.liquidaciones.bono.application.usecases;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.FaltanteBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.IntegridadBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.PeriodoBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.VerificarIntegridadBonoUseCase;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.ItemQueryPort;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.LegajoControlQueryPort;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.LiquidacionQueryPort;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.PersonaQueryPort;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.ActividadRepository;

@Component
@RequiredArgsConstructor
public class VerificarIntegridadBonoUseCaseImpl implements VerificarIntegridadBonoUseCase {

    private final PersonaQueryPort personaQueryPort;
    private final LiquidacionQueryPort liquidacionQueryPort;
    private final ItemQueryPort itemQueryPort;
    private final ActividadRepository actividadRepository;
    private final LegajoControlQueryPort legajoControlQueryPort;

    @Override
    public IntegridadBono verificarIntegridad(PeriodoBono periodo) {
        List<FaltanteBono> faltantes = new ArrayList<>();
        if (personaQueryPort.findDependenciaIdByLegajoId(periodo.getLegajoId()).isEmpty()) {
            faltantes.add(FaltanteBono.DEPENDENCIA);
        }
        if (!liquidacionQueryPort.existsByLegajoIdAndAnhoAndMes(periodo.getLegajoId(), periodo.getAnho(),
                periodo.getMes())) {
            faltantes.add(FaltanteBono.LIQUIDACION);
        }
        if (!itemQueryPort.existsByLegajoIdAndAnhoAndMes(periodo.getLegajoId(), periodo.getAnho(), periodo.getMes())) {
            faltantes.add(FaltanteBono.ITEM);
        }
        if (actividadRepository.findByLegajoIdAndAnhoAndMes(periodo.getLegajoId(), periodo.getAnho(),
                periodo.getMes()).isEmpty()) {
            faltantes.add(FaltanteBono.ACTIVIDAD);
        }
        if (!legajoControlQueryPort.existsByLegajoIdAndAnhoAndMes(periodo.getLegajoId(), periodo.getAnho(),
                periodo.getMes())) {
            faltantes.add(FaltanteBono.LEGAJO_CONTROL);
        }
        return IntegridadBono.builder()
                .legajoId(periodo.getLegajoId())
                .anho(periodo.getAnho())
                .mes(periodo.getMes())
                .ok(faltantes.isEmpty())
                .faltantes(faltantes)
                .build();
    }
}
