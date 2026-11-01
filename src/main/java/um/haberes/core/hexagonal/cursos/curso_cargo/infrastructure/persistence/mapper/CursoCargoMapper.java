package um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.persistence.mapper.CargoTipoMapper;
import um.haberes.core.hexagonal.cursos.curso.infrastructure.persistence.mapper.CursoMapper;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.persistence.entity.CursoCargoEntity;
import um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.persistence.mapper.DesignacionTipoMapper;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.mapper.CategoriaMapper;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.mapper.PersonaMapper;

@Component
@RequiredArgsConstructor
public class CursoCargoMapper {

    private final CursoMapper cursoMapper;

    private final CargoTipoMapper cargoTipoMapper;

    private final DesignacionTipoMapper designacionTipoMapper;

    private final CategoriaMapper categoriaMapper;

    private final PersonaMapper personaMapper;

    public CursoCargoEntity toEntity(CursoCargo domain) {
        if (domain == null) {
            return null;
        }
        CursoCargoEntity entity = new CursoCargoEntity();
        entity.setCursoCargoId(domain.getCursoCargoId());
        entity.setCursoId(domain.getCursoId());
        entity.setAnho(domain.getAnho());
        entity.setMes(domain.getMes());
        entity.setCargoTipoId(domain.getCargoTipoId());
        entity.setLegajoId(domain.getLegajoId());
        entity.setDesignacionTipoId(domain.getDesignacionTipoId());
        entity.setCategoriaId(domain.getCategoriaId());
        entity.setCursoCargoNovedadId(domain.getCursoCargoNovedadId());
        if (domain.getHorasSemanales() != null) {
            entity.setHorasSemanales(domain.getHorasSemanales());
        }
        if (domain.getHorasTotales() != null) {
            entity.setHorasTotales(domain.getHorasTotales());
        }
        if (domain.getDesarraigo() != null) {
            entity.setDesarraigo(domain.getDesarraigo());
        }
        return entity;
    }

    public CursoCargo toDomain(CursoCargoEntity entity) {
        if (entity == null) {
            return null;
        }
        CursoCargo.CursoCargoBuilder builder = CursoCargo.builder()
                .cursoCargoId(entity.getCursoCargoId())
                .cursoId(entity.getCursoId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .cargoTipoId(entity.getCargoTipoId())
                .legajoId(entity.getLegajoId())
                .designacionTipoId(entity.getDesignacionTipoId())
                .categoriaId(entity.getCategoriaId())
                .cursoCargoNovedadId(entity.getCursoCargoNovedadId())
                .curso(cursoMapper.toDomain(entity.getCurso()))
                .cargoTipo(cargoTipoMapper.toDomain(entity.getCargoTipo()))
                .persona(personaMapper.toDomain(entity.getPersona()))
                .designacionTipo(designacionTipoMapper.toDomain(entity.getDesignacionTipo()))
                .categoria(categoriaMapper.toDomain(entity.getCategoria()));
        if (entity.getHorasSemanales() != null) {
            builder.horasSemanales(entity.getHorasSemanales());
        }
        if (entity.getHorasTotales() != null) {
            builder.horasTotales(entity.getHorasTotales());
        }
        if (entity.getDesarraigo() != null) {
            builder.desarraigo(entity.getDesarraigo());
        }
        return builder.build();
    }
}
