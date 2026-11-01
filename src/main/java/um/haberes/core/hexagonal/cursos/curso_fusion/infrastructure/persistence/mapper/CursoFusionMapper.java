package um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.model.CursoFusion;
import um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.persistence.entity.CursoFusionEntity;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.mapper.CategoriaMapper;

@Component
@RequiredArgsConstructor
public class CursoFusionMapper {

    private final CategoriaMapper categoriaMapper;

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
                .categoria(categoriaMapper.toDomain(entity.getCategoria()))
                .build();
    }
}
