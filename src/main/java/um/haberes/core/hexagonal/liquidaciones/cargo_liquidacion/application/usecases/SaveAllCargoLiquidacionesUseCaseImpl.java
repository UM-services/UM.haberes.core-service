package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.audit.AuditBulkScope;
import um.haberes.core.audit.AuditBulkService;
import um.haberes.core.audit.BulkContext;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacionVersion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.SaveAllCargoLiquidacionesUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionVersionRepository;

@Component
@RequiredArgsConstructor
public class SaveAllCargoLiquidacionesUseCaseImpl implements SaveAllCargoLiquidacionesUseCase {

    private final CargoLiquidacionRepository cargoLiquidacionRepository;
    private final CargoLiquidacionVersionRepository cargoLiquidacionVersionRepository;
    private final AuditBulkService auditBulk;

    @Transactional
    @Override
    public List<CargoLiquidacion> saveAllCargoLiquidaciones(List<CargoLiquidacion> cargos, Integer version) {
        if (cargos.isEmpty()) {
            return cargoLiquidacionRepository.saveAll(cargos);
        }

        try (AuditBulkScope ignored = auditBulk.begin(context(cargos))) {
            List<CargoLiquidacion> saved = cargoLiquidacionRepository.saveAll(cargos);
            Map<Group, Integer> versions = new HashMap<>();
            List<CargoLiquidacionVersion> backups = saved.stream()
                    .map(cargo -> toVersion(cargo, versions.computeIfAbsent(
                            Group.of(cargo), group -> resolveVersion(cargo, version))))
                    .collect(Collectors.toList());
            cargoLiquidacionVersionRepository.saveAll(backups);
            return saved;
        }
    }

    /**
     * Si el caller pasa una versión explícita (endpoint legacy) se respeta. Si pasa
     * {@code null} —el camino de la generación— se usa el siguiente número por
     * legajo/período, para que cada generación quede identificada sin ambigüedad.
     */
    private int resolveVersion(CargoLiquidacion cargo, Integer version) {
        if (version != null) {
            return version;
        }
        return cargoLiquidacionVersionRepository
                .findLastVersion(cargo.getLegajoId(), cargo.getAnho(), cargo.getMes())
                .map(last -> last.getVersion() == null ? 1 : last.getVersion() + 1)
                .orElse(1);
    }

    private BulkContext context(List<CargoLiquidacion> cargos) {
        CargoLiquidacion first = cargos.getFirst();
        boolean samePeriod = cargos.stream()
                .allMatch(cargo -> Objects.equals(cargo.getAnho(), first.getAnho())
                        && Objects.equals(cargo.getMes(), first.getMes()));
        boolean sameLegajo = cargos.stream()
                .allMatch(cargo -> Objects.equals(cargo.getLegajoId(), first.getLegajoId()));
        return BulkContext.of("generacion-cargos-liquidacion",
                samePeriod ? first.getAnho() : null,
                samePeriod ? first.getMes() : null,
                sameLegajo ? first.getLegajoId() : null);
    }

    private CargoLiquidacionVersion toVersion(CargoLiquidacion cargo, Integer version) {
        CargoLiquidacionVersion.CargoLiquidacionVersionBuilder builder = CargoLiquidacionVersion.builder()
                .legajoId(cargo.getLegajoId())
                .anho(cargo.getAnho())
                .mes(cargo.getMes())
                .version(version)
                .dependenciaId(cargo.getDependenciaId())
                .fechaDesde(cargo.getFechaDesde())
                .fechaHasta(cargo.getFechaHasta())
                .categoriaId(cargo.getCategoriaId())
                .jornada(cargo.getJornada())
                .presentismo(cargo.getPresentismo())
                .situacion(cargo.getSituacion());
        if (cargo.getCategoriaBasico() != null) {
            builder.basico(cargo.getCategoriaBasico());
        }
        if (cargo.getEstadoDocente() != null) {
            builder.estadoDocente(cargo.getEstadoDocente());
        }
        if (cargo.getHorasJornada() != null) {
            builder.horasJornada(cargo.getHorasJornada());
        }
        return builder.build();
    }

    private record Group(Long legajoId, Integer anho, Integer mes) {
        static Group of(CargoLiquidacion cargo) {
            return new Group(cargo.getLegajoId(), cargo.getAnho(), cargo.getMes());
        }
    }
}
