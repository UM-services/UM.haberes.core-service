package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.persistence.entity.CargoLiquidacionEntity;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.mapper.CategoriaMapper;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.persistence.mapper.DependenciaMapper;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.mapper.PersonaMapper;

@Component
@RequiredArgsConstructor
public class CargoLiquidacionMapper {

    private final PersonaMapper personaMapper;

    private final DependenciaMapper dependenciaMapper;

    private final CategoriaMapper categoriaMapper;

    public CargoLiquidacionEntity toEntity(CargoLiquidacion domain) {
        if (domain == null) {
            return null;
        }
        CargoLiquidacionEntity entity = new CargoLiquidacionEntity();
        entity.setCargoLiquidacionId(domain.getCargoLiquidacionId());
        entity.setLegajoId(domain.getLegajoId());
        entity.setAnho(domain.getAnho());
        entity.setMes(domain.getMes());
        entity.setDependenciaId(domain.getDependenciaId());
        entity.setFechaDesde(domain.getFechaDesde());
        entity.setFechaHasta(domain.getFechaHasta());
        entity.setCategoriaId(domain.getCategoriaId());
        if (domain.getCategoriaNombre() != null) {
            entity.setCategoriaNombre(domain.getCategoriaNombre());
        }
        if (domain.getCategoriaBasico() != null) {
            entity.setCategoriaBasico(domain.getCategoriaBasico());
        }
        if (domain.getEstadoDocente() != null) {
            entity.setEstadoDocente(domain.getEstadoDocente());
        }
        if (domain.getHorasJornada() != null) {
            entity.setHorasJornada(domain.getHorasJornada());
        }
        entity.setJornada(domain.getJornada());
        entity.setPresentismo(domain.getPresentismo());
        entity.setSituacion(domain.getSituacion());
        return entity;
    }

    public CargoLiquidacion toDomain(CargoLiquidacionEntity entity) {
        if (entity == null) {
            return null;
        }
        CargoLiquidacion.CargoLiquidacionBuilder builder = CargoLiquidacion.builder()
                .cargoLiquidacionId(entity.getCargoLiquidacionId())
                .legajoId(entity.getLegajoId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .dependenciaId(entity.getDependenciaId())
                .fechaDesde(entity.getFechaDesde())
                .fechaHasta(entity.getFechaHasta())
                .categoriaId(entity.getCategoriaId())
                .jornada(entity.getJornada())
                .presentismo(entity.getPresentismo())
                .situacion(entity.getSituacion())
                .persona(personaMapper.toDomain(entity.getPersona()))
                .dependencia(dependenciaMapper.toDomain(entity.getDependencia()))
                .categoria(categoriaMapper.toDomain(entity.getCategoria()));
        if (entity.getCategoriaNombre() != null) {
            builder.categoriaNombre(entity.getCategoriaNombre());
        }
        if (entity.getCategoriaBasico() != null) {
            builder.categoriaBasico(entity.getCategoriaBasico());
        }
        if (entity.getEstadoDocente() != null) {
            builder.estadoDocente(entity.getEstadoDocente());
        }
        if (entity.getHorasJornada() != null) {
            builder.horasJornada(entity.getHorasJornada());
        }
        return builder.build();
    }
}
