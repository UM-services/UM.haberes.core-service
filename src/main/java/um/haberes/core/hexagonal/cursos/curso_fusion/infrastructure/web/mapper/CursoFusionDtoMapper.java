package um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.cursos.curso_fusion.domain.model.CursoFusion;
import um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.web.dto.CursoFusionRequest;
import um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.web.dto.CursoFusionResponse;

@Component
public class CursoFusionDtoMapper {

    public CursoFusion toDomain(CursoFusionRequest request) {
        if (request == null) {
            return null;
        }
        return CursoFusion.builder()
                .legajoId(request.getLegajoId())
                .anho(request.getAnho())
                .mes(request.getMes())
                .facultadId(request.getFacultadId())
                .geograficaId(request.getGeograficaId())
                .cargoTipoId(request.getCargoTipoId())
                .designacionTipoId(request.getDesignacionTipoId())
                .anual(request.getAnual())
                .categoriaId(request.getCategoriaId())
                .build();
    }

    public CursoFusionResponse toResponse(CursoFusion domain) {
        if (domain == null) {
            return null;
        }
        return CursoFusionResponse.builder()
                .cursoFusionId(domain.getCursoFusionId())
                .legajoId(domain.getLegajoId())
                .anho(domain.getAnho())
                .mes(domain.getMes())
                .facultadId(domain.getFacultadId())
                .geograficaId(domain.getGeograficaId())
                .cargoTipoId(domain.getCargoTipoId())
                .designacionTipoId(domain.getDesignacionTipoId())
                .anual(domain.getAnual())
                .categoriaId(domain.getCategoriaId())
                .build();
    }
}
