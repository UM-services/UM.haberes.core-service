package um.haberes.core.hexagonal.personas.persona.infrastructure.adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.CursoRepository;
import um.haberes.core.hexagonal.cursos.curso.application.service.CursoService;

@Component
@RequiredArgsConstructor
public class CursoServiceAdapter implements CursoRepository {

    private final CursoService cursoService;

    @Override
    public List<Long> findCursoIdsByFacultadId(Integer facultadId) {
        return cursoService.findAllByFacultadId(facultadId).stream()
                .map(Curso::getCursoId)
                .toList();
    }
}
