package um.haberes.core.hexagonal.cursos.curso_cargo.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.CreateCursoCargoUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out.CursoCargoRepository;

@Component
@RequiredArgsConstructor
public class CreateCursoCargoUseCaseImpl implements CreateCursoCargoUseCase {

    private final CursoCargoRepository cursoCargoRepository;

    @Override
    public CursoCargo createCursoCargo(CursoCargo cursoCargo) {
        return cursoCargoRepository.save(cursoCargo);
    }
}
