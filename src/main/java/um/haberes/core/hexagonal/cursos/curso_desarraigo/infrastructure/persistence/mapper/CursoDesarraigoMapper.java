package um.haberes.core.hexagonal.cursos.curso_desarraigo.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.model.CursoDesarraigo;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.infrastructure.persistence.entity.CursoDesarraigoEntity;

@Component
public class CursoDesarraigoMapper {

    public CursoDesarraigoEntity toEntity(CursoDesarraigo domain) {
        if (domain == null) {
            return null;
        }
        CursoDesarraigoEntity entity = new CursoDesarraigoEntity();
        entity.setCursoDesarraigoId(domain.getCursoDesarraigoId());
        entity.setLegajoId(domain.getLegajoId());
        entity.setAnho(domain.getAnho());
        entity.setMes(domain.getMes());
        entity.setCursoId(domain.getCursoId());
        entity.setGeograficaId(domain.getGeograficaId());
        entity.setVersion(domain.getVersion());
        if (domain.getImporte() != null) {
            entity.setImporte(domain.getImporte());
        }
        return entity;
    }

    public CursoDesarraigo toDomain(CursoDesarraigoEntity entity) {
        if (entity == null) {
            return null;
        }
        CursoDesarraigo.CursoDesarraigoBuilder builder = CursoDesarraigo.builder()
                .cursoDesarraigoId(entity.getCursoDesarraigoId())
                .legajoId(entity.getLegajoId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .cursoId(entity.getCursoId())
                .geograficaId(entity.getGeograficaId())
                .version(entity.getVersion());
        if (entity.getImporte() != null) {
            builder.importe(entity.getImporte());
        }
        return builder.build();
    }
}
