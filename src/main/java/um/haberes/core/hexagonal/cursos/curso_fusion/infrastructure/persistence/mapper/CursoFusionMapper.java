package um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.persistence.mapper.CargoTipoMapper;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.model.CursoFusion;
import um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.persistence.entity.CursoFusionEntity;
import um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.persistence.mapper.DesignacionTipoMapper;
import um.haberes.core.hexagonal.facultad.infrastructure.persistence.mapper.FacultadMapper;
import um.haberes.core.hexagonal.geografica.infrastructure.persistence.mapper.GeograficaMapper;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.mapper.CategoriaMapper;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.mapper.PersonaMapper;

@Component
@RequiredArgsConstructor
public class CursoFusionMapper {

    private final CategoriaMapper categoriaMapper;

    private final PersonaMapper personaMapper;

    private final FacultadMapper facultadMapper;

    private final GeograficaMapper geograficaMapper;

    private final CargoTipoMapper cargoTipoMapper;

    private final DesignacionTipoMapper designacionTipoMapper;

    public CursoFusionEntity toEntity(CursoFusion domain) {
        if (domain == null) {
            return null;
        }
        CursoFusionEntity entity = new CursoFusionEntity();
        entity.setCursoFusionId(domain.getCursoFusionId());
        entity.setLegajoId(domain.getLegajoId());
        if (domain.getAnho() != null) {
            entity.setAnho(domain.getAnho());
        }
        if (domain.getMes() != null) {
            entity.setMes(domain.getMes());
        }
        entity.setFacultadId(domain.getFacultadId());
        entity.setGeograficaId(domain.getGeograficaId());
        entity.setCargoTipoId(domain.getCargoTipoId());
        entity.setDesignacionTipoId(domain.getDesignacionTipoId());
        if (domain.getAnual() != null) {
            entity.setAnual(domain.getAnual());
        }
        entity.setCategoriaId(domain.getCategoriaId());
        return entity;
    }

    public CursoFusion toDomain(CursoFusionEntity entity) {
        if (entity == null) {
            return null;
        }
        return CursoFusion.builder()
                .cursoFusionId(entity.getCursoFusionId())
                .legajoId(entity.getLegajoId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .facultadId(entity.getFacultadId())
                .geograficaId(entity.getGeograficaId())
                .cargoTipoId(entity.getCargoTipoId())
                .designacionTipoId(entity.getDesignacionTipoId())
                .anual(entity.getAnual())
                .categoriaId(entity.getCategoriaId())
                .persona(personaMapper.toDomain(entity.getPersona()))
                .facultad(facultadMapper.toDomainModel(entity.getFacultad()))
                .geografica(geograficaMapper.toDomainModel(entity.getGeografica()))
                .cargoTipo(cargoTipoMapper.toDomain(entity.getCargoTipo()))
                .designacionTipo(designacionTipoMapper.toDomain(entity.getDesignacionTipo()))
                .categoria(categoriaMapper.toDomain(entity.getCategoria()))
                .build();
    }
}
