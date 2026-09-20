package um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.web.mapper.CargoTipoDtoMapper;
import um.haberes.core.hexagonal.cursos.curso.infrastructure.web.mapper.CursoDtoMapper;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.web.dto.CursoCargoRequest;
import um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.web.dto.CursoCargoResponse;
import um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.web.mapper.DesignacionTipoDtoMapper;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.mapper.CategoriaDtoMapper;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.mapper.PersonaDtoMapper;

@Component
@RequiredArgsConstructor
public class CursoCargoDtoMapper {

    private final CursoDtoMapper cursoDtoMapper;

    private final CargoTipoDtoMapper cargoTipoDtoMapper;

    private final PersonaDtoMapper personaDtoMapper;

    private final DesignacionTipoDtoMapper designacionTipoDtoMapper;

    private final CategoriaDtoMapper categoriaDtoMapper;

    public CursoCargo toDomain(CursoCargoRequest request) {
        if (request == null) {
            return null;
        }
        CursoCargo.CursoCargoBuilder builder = CursoCargo.builder()
                .cursoId(request.getCursoId())
                .anho(request.getAnho())
                .mes(request.getMes())
                .cargoTipoId(request.getCargoTipoId())
                .legajoId(request.getLegajoId())
                .designacionTipoId(request.getDesignacionTipoId())
                .categoriaId(request.getCategoriaId())
                .cursoCargoNovedadId(request.getCursoCargoNovedadId());
        if (request.getHorasSemanales() != null) {
            builder.horasSemanales(request.getHorasSemanales());
        }
        if (request.getHorasTotales() != null) {
            builder.horasTotales(request.getHorasTotales());
        }
        if (request.getDesarraigo() != null) {
            builder.desarraigo(request.getDesarraigo());
        }
        return builder.build();
    }

    public CursoCargoResponse toResponse(CursoCargo domain) {
        if (domain == null) {
            return null;
        }
        return CursoCargoResponse.builder()
                .cursoCargoId(domain.getCursoCargoId())
                .cursoId(domain.getCursoId())
                .anho(domain.getAnho())
                .mes(domain.getMes())
                .cargoTipoId(domain.getCargoTipoId())
                .legajoId(domain.getLegajoId())
                .horasSemanales(domain.getHorasSemanales())
                .horasTotales(domain.getHorasTotales())
                .designacionTipoId(domain.getDesignacionTipoId())
                .categoriaId(domain.getCategoriaId())
                .desarraigo(domain.getDesarraigo())
                .cursoCargoNovedadId(domain.getCursoCargoNovedadId())
                .curso(cursoDtoMapper.toResponse(domain.getCurso()))
                .cargoTipo(cargoTipoDtoMapper.toResponse(domain.getCargoTipo()))
                .persona(personaDtoMapper.toResponse(domain.getPersona()))
                .designacionTipo(designacionTipoDtoMapper.toResponse(domain.getDesignacionTipo()))
                .categoria(categoriaDtoMapper.toResponse(domain.getCategoria()))
                .build();
    }
}
