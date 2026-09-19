package um.haberes.core.hexagonal.cursos.curso.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.in.GetCursosByFacultadIdAndGeograficaIdUseCase;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.out.CursoRepository;

@Component
@RequiredArgsConstructor
public class GetCursosByFacultadIdAndGeograficaIdUseCaseImpl implements GetCursosByFacultadIdAndGeograficaIdUseCase {

    private final CursoRepository cursoRepository;

    @Override
    public List<Curso> getCursosByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId) {
        return cursoRepository.findAllByFacultadIdAndGeograficaId(facultadId, geograficaId);
    }
}
