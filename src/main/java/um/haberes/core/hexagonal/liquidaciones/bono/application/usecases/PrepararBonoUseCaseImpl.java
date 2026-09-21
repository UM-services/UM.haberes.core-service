package um.haberes.core.hexagonal.liquidaciones.bono.application.usecases;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.bono.application.exception.BonoException;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.Actividad;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.PeriodoBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.PrepararBonoUseCase;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.ActividadRepository;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.CargoClaseDetalleQueryPort;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.CargoLiquidacionQueryPort;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.LiquidacionQueryPort;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.PersonaQueryPort;

@Component
@RequiredArgsConstructor
public class PrepararBonoUseCaseImpl implements PrepararBonoUseCase {

    private static final Byte ACTIVO = 1;
    private static final Byte INACTIVO = 0;

    private final LiquidacionQueryPort liquidacionQueryPort;
    private final ActividadRepository actividadRepository;
    private final CargoLiquidacionQueryPort cargoLiquidacionQueryPort;
    private final CargoClaseDetalleQueryPort cargoClaseDetalleQueryPort;
    private final PersonaQueryPort personaQueryPort;

    @Override
    @Transactional
    public Actividad preparar(PeriodoBono periodo) {
        if (!liquidacionQueryPort.existsByLegajoIdAndAnhoAndMes(periodo.getLegajoId(), periodo.getAnho(),
                periodo.getMes())) {
            throw new BonoException("Cannot prepare Bono: falta LIQUIDACION para legajo " + periodo.getLegajoId()
                    + " periodo " + periodo.getAnho() + "/" + periodo.getMes());
        }
        Actividad actividad = actividadRepository
                .findByLegajoIdAndAnhoAndMes(periodo.getLegajoId(), periodo.getAnho(), periodo.getMes())
                .orElseGet(() -> Actividad.builder()
                        .legajoId(periodo.getLegajoId())
                        .anho(periodo.getAnho())
                        .mes(periodo.getMes())
                        .dependenciaId(personaQueryPort.findDependenciaIdByLegajoId(periodo.getLegajoId())
                                .orElse(null))
                        .build());

        boolean docente = cargoLiquidacionQueryPort
                .findBasicoCargosDocentesByLegajoAndPeriodo(periodo.getLegajoId(), periodo.getAnho(), periodo.getMes())
                .stream()
                .anyMatch(this::tieneBasico);
        boolean otras = cargoLiquidacionQueryPort
                .findBasicoCargosNoDocentesByLegajoAndPeriodo(periodo.getLegajoId(), periodo.getAnho(),
                        periodo.getMes())
                .stream()
                .anyMatch(this::tieneBasico);
        boolean clases = cargoClaseDetalleQueryPort.existsByLegajoIdAndAnhoAndMes(periodo.getLegajoId(),
                periodo.getAnho(), periodo.getMes());

        actividad.setDocente(docente ? ACTIVO : INACTIVO);
        actividad.setOtras(otras ? ACTIVO : INACTIVO);
        actividad.setClases(clases ? ACTIVO : INACTIVO);

        return actividadRepository.save(actividad);
    }

    private boolean tieneBasico(BigDecimal categoriaBasico) {
        return categoriaBasico != null && categoriaBasico.compareTo(BigDecimal.ZERO) != 0;
    }
}
