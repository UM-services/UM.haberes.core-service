package um.haberes.core.hexagonal.cursos.curso.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.in.CreateCursoUseCase;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.out.CursoRepository;

@Component
@RequiredArgsConstructor
public class CreateCursoUseCaseImpl implements CreateCursoUseCase {

    private final CursoRepository cursoRepository;

    @Override
    public Curso createCurso(Curso curso) {
        return cursoRepository.save(curso);
    }
}
