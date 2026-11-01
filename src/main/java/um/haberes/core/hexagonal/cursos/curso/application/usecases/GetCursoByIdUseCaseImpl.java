package um.haberes.core.hexagonal.cursos.curso.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.in.GetCursoByIdUseCase;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.out.CursoRepository;

@Component
@RequiredArgsConstructor
public class GetCursoByIdUseCaseImpl implements GetCursoByIdUseCase {

    private final CursoRepository cursoRepository;

    @Override
    public Optional<Curso> getCursoById(Long cursoId) {
        return cursoRepository.findByCursoId(cursoId);
    }
}
