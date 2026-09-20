package um.haberes.core.hexagonal.liquidaciones.cargo.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;
import um.haberes.core.hexagonal.liquidaciones.cargo.infrastructure.persistence.entity.CargoEntity;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.mapper.CategoriaMapper;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.persistence.mapper.DependenciaMapper;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.mapper.PersonaMapper;

@Component
@RequiredArgsConstructor
public class CargoMapper {

    private final PersonaMapper personaMapper;

    private final DependenciaMapper dependenciaMapper;

    private final CategoriaMapper categoriaMapper;

    public CargoEntity toEntity(Cargo domain) {
        if (domain == null) {
            return null;
        }
        CargoEntity entity = new CargoEntity();
        entity.setCargoId(domain.getCargoId());
        entity.setLegajoId(domain.getLegajoId());
        entity.setFechaAlta(domain.getFechaAlta());
        entity.setFechaBaja(domain.getFechaBaja());
        entity.setDependenciaId(domain.getDependenciaId());
        entity.setCategoriaId(domain.getCategoriaId());
        entity.setJornada(domain.getJornada());
        entity.setPresentismo(domain.getPresentismo());
        if (domain.getHorasJornada() != null) {
            entity.setHorasJornada(domain.getHorasJornada());
        }
        return entity;
    }

    public Cargo toDomain(CargoEntity entity) {
        if (entity == null) {
            return null;
        }
        Cargo.CargoBuilder builder = Cargo.builder()
                .cargoId(entity.getCargoId())
                .legajoId(entity.getLegajoId())
                .fechaAlta(entity.getFechaAlta())
                .fechaBaja(entity.getFechaBaja())
                .dependenciaId(entity.getDependenciaId())
                .categoriaId(entity.getCategoriaId())
                .jornada(entity.getJornada())
                .presentismo(entity.getPresentismo())
                .persona(personaMapper.toDomain(entity.getPersona()))
                .dependencia(dependenciaMapper.toDomain(entity.getDependencia()))
                .categoria(categoriaMapper.toDomain(entity.getCategoria()));
        if (entity.getHorasJornada() != null) {
            builder.horasJornada(entity.getHorasJornada());
        }
        return builder.build();
    }
}
