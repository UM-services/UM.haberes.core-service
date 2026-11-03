package um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.web.mapper.CargoTipoDtoMapper;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.model.CursoFusion;
import um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.web.dto.CursoFusionRequest;
import um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.web.dto.CursoFusionResponse;
import um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.web.mapper.DesignacionTipoDtoMapper;
import um.haberes.core.hexagonal.facultad.infrastructure.web.mapper.FacultadDtoMapper;
import um.haberes.core.hexagonal.geografica.infrastructure.web.mapper.GeograficaDtoMapper;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.mapper.CategoriaDtoMapper;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.mapper.PersonaDtoMapper;

@Component
@RequiredArgsConstructor
public class CursoFusionDtoMapper {

    private final PersonaDtoMapper personaDtoMapper;

    private final FacultadDtoMapper facultadDtoMapper;

    private final GeograficaDtoMapper geograficaDtoMapper;

    private final CargoTipoDtoMapper cargoTipoDtoMapper;

    private final DesignacionTipoDtoMapper designacionTipoDtoMapper;

    private final CategoriaDtoMapper categoriaDtoMapper;

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
                .persona(personaDtoMapper.toResponse(domain.getPersona()))
                .facultad(facultadDtoMapper.toResponse(domain.getFacultad()))
                .geografica(geograficaDtoMapper.toResponse(domain.getGeografica()))
                .cargoTipo(cargoTipoDtoMapper.toResponse(domain.getCargoTipo()))
                .designacionTipo(designacionTipoDtoMapper.toResponse(domain.getDesignacionTipo()))
                .categoria(categoriaDtoMapper.toResponse(domain.getCategoria()))
                .build();
    }
}
