package um.haberes.core.hexagonal.cursos.curso_cargo.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetCursoCargoByIdUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out.CursoCargoRepository;

@Component
@RequiredArgsConstructor
public class GetCursoCargoByIdUseCaseImpl implements GetCursoCargoByIdUseCase {

    private final CursoCargoRepository cursoCargoRepository;

    @Override
    public Optional<CursoCargo> getCursoCargoById(Long cursoCargoId) {
        return cursoCargoRepository.findByCursoCargoId(cursoCargoId);
    }
}
