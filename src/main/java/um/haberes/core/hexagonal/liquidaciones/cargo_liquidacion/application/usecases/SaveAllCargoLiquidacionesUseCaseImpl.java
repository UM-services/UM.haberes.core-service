package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    @Override
    public List<CargoLiquidacion> saveAllCargoLiquidaciones(List<CargoLiquidacion> cargos, Integer version,
            Boolean withVersion) {
        List<CargoLiquidacion> saved = cargoLiquidacionRepository.saveAll(cargos);
        if (Boolean.TRUE.equals(withVersion)) {
            List<CargoLiquidacionVersion> backups = saved.stream()
                    .map(cargo -> toVersion(cargo, version))
                    .collect(Collectors.toList());
            cargoLiquidacionVersionRepository.saveAll(backups);
        }
        return saved;
    }

    private CargoLiquidacionVersion toVersion(CargoLiquidacion cargo, Integer version) {
        CargoLiquidacionVersion.CargoLiquidacionVersionBuilder builder = CargoLiquidacionVersion.builder()
                .legajoId(cargo.getLegajoId())
                .anho(cargo.getAnho())
                .mes(cargo.getMes())
                .version(version)
                .dependenciaId(cargo.getDependenciaId())
                .categoriaId(cargo.getCategoriaId())
                .jornada(cargo.getJornada())
                .presentismo(cargo.getPresentismo())
                .fechaDesde(cargo.getFechaDesde())
                .fechaHasta(cargo.getFechaHasta())
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
}
