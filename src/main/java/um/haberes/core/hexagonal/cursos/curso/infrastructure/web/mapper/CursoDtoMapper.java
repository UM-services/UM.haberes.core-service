package um.haberes.core.hexagonal.cursos.curso.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;
import um.haberes.core.hexagonal.cursos.curso.infrastructure.web.dto.CursoRequest;
import um.haberes.core.hexagonal.cursos.curso.infrastructure.web.dto.CursoResponse;
import um.haberes.core.hexagonal.facultad.infrastructure.web.mapper.FacultadDtoMapper;
import um.haberes.core.hexagonal.geografica.infrastructure.web.mapper.GeograficaDtoMapper;

@Component
@RequiredArgsConstructor
public class CursoDtoMapper {

    private final FacultadDtoMapper facultadDtoMapper;
    private final GeograficaDtoMapper geograficaDtoMapper;

    public Curso toDomain(CursoRequest request) {
        if (request == null) {
            return null;
        }
        Curso.CursoBuilder builder = Curso.builder()
                .facultadId(request.getFacultadId())
                .geograficaId(request.getGeograficaId())
                .nivelId(request.getNivelId());
        if (request.getNombre() != null) {
            builder.nombre(request.getNombre());
        }
        if (request.getAnual() != null) {
            builder.anual(request.getAnual());
        }
        if (request.getSemestre1() != null) {
            builder.semestre1(request.getSemestre1());
        }
        if (request.getSemestre2() != null) {
            builder.semestre2(request.getSemestre2());
        }
        if (request.getAdicionalCargaHoraria() != null) {
            builder.adicionalCargaHoraria(request.getAdicionalCargaHoraria());
        }
        return builder.build();
    }

    public CursoResponse toResponse(Curso domain) {
        if (domain == null) {
            return null;
        }
        return CursoResponse.builder()
                .cursoId(domain.getCursoId())
                .nombre(domain.getNombre())
                .facultadId(domain.getFacultadId())
                .geograficaId(domain.getGeograficaId())
                .anual(domain.getAnual())
                .semestre1(domain.getSemestre1())
                .semestre2(domain.getSemestre2())
                .nivelId(domain.getNivelId())
                .adicionalCargaHoraria(domain.getAdicionalCargaHoraria())
                .facultad(facultadDtoMapper.toResponse(domain.getFacultad()))
                .geografica(geograficaDtoMapper.toResponse(domain.getGeografica()))
                .build();
    }

}
