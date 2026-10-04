package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.persistence.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacionVersion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionVersionRepository;
import um.haberes.core.model.CargoLiquidacionVersionEntity;
import um.haberes.core.repository.JpaCargoLiquidacionVersionRepository;

@Component
@RequiredArgsConstructor
public class JpaCargoLiquidacionVersionRepositoryAdapter implements CargoLiquidacionVersionRepository {

    private final JpaCargoLiquidacionVersionRepository jpaCargoLiquidacionVersionRepository;

    @Override
    public CargoLiquidacionVersion save(CargoLiquidacionVersion cargoLiquidacionVersion) {
        return toDomain(jpaCargoLiquidacionVersionRepository.save(toEntity(cargoLiquidacionVersion)));
    }

    @Override
    public List<CargoLiquidacionVersion> saveAll(List<CargoLiquidacionVersion> cargoLiquidacionVersions) {
        List<CargoLiquidacionVersionEntity> entities = cargoLiquidacionVersions.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        return jpaCargoLiquidacionVersionRepository.saveAll(entities).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private CargoLiquidacionVersionEntity toEntity(CargoLiquidacionVersion domain) {
        if (domain == null) {
            return null;
        }
        return new CargoLiquidacionVersionEntity(null,
                domain.getLegajoId(),
                domain.getAnho(),
                domain.getMes(),
                domain.getVersion(),
                domain.getDependenciaId(),
                domain.getCategoriaId(),
                domain.getBasico(),
                domain.getEstadoDocente(),
                domain.getHorasJornada(),
                domain.getJornada(),
                domain.getPresentismo(),
                domain.getFechaDesde(),
                domain.getFechaHasta(),
                domain.getSituacion());
    }

    private CargoLiquidacionVersion toDomain(CargoLiquidacionVersionEntity entity) {
        if (entity == null) {
            return null;
        }
        return CargoLiquidacionVersion.builder()
                .cargoLiquidacionVersionId(entity.getCargoLiquidacionVersionId())
                .legajoId(entity.getLegajoId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .version(entity.getVersion())
                .dependenciaId(entity.getDependenciaId())
                .categoriaId(entity.getCategoriaId())
                .basico(entity.getBasico())
                .estadoDocente(entity.getEstadoDocente())
                .horasJornada(entity.getHorasJornada())
                .jornada(entity.getJornada())
                .presentismo(entity.getPresentismo())
                .fechaDesde(entity.getFechaDesde())
                .fechaHasta(entity.getFechaHasta())
                .situacion(entity.getSituacion())
                .build();
    }
}
