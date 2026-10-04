package um.haberes.core.hexagonal.cursos.curso_desarraigo.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.model.CursoDesarraigo;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.infrastructure.web.dto.CursoDesarraigoRequest;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.infrastructure.web.dto.CursoDesarraigoResponse;

@Component
public class CursoDesarraigoDtoMapper {

    public CursoDesarraigo toDomain(CursoDesarraigoRequest request) {
        if (request == null) {
            return null;
        }
        CursoDesarraigo.CursoDesarraigoBuilder builder = CursoDesarraigo.builder()
                .legajoId(request.getLegajoId())
                .anho(request.getAnho())
                .mes(request.getMes())
                .cursoId(request.getCursoId())
                .geograficaId(request.getGeograficaId())
                .version(request.getVersion());
        if (request.getImporte() != null) {
            builder.importe(request.getImporte());
        }
        return builder.build();
    }

    public CursoDesarraigoResponse toResponse(CursoDesarraigo domain) {
        if (domain == null) {
            return null;
        }
        return CursoDesarraigoResponse.builder()
                .cursoDesarraigoId(domain.getCursoDesarraigoId())
                .legajoId(domain.getLegajoId())
                .anho(domain.getAnho())
                .mes(domain.getMes())
                .cursoId(domain.getCursoId())
                .geograficaId(domain.getGeograficaId())
                .importe(domain.getImporte())
                .version(domain.getVersion())
                .build();
    }
}
