package um.haberes.core.hexagonal.cursos.curso.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.in.GetCursosByFacultadIdUseCase;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.out.CursoRepository;

@Component
@RequiredArgsConstructor
public class GetCursosByFacultadIdUseCaseImpl implements GetCursosByFacultadIdUseCase {

    private final CursoRepository cursoRepository;

    @Override
    public List<Curso> getCursosByFacultadId(Integer facultadId) {
        return cursoRepository.findAllByFacultadId(facultadId);
    }
}
