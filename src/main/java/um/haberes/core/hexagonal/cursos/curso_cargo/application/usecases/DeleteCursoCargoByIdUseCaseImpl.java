package um.haberes.core.hexagonal.cursos.curso_cargo.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.DeleteCursoCargoByIdUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out.CursoCargoRepository;

@Component
@RequiredArgsConstructor
public class DeleteCursoCargoByIdUseCaseImpl implements DeleteCursoCargoByIdUseCase {

    private final CursoCargoRepository cursoCargoRepository;

    @Transactional
    @Override
    public void deleteCursoCargoById(Long cursoCargoId) {
        cursoCargoRepository.deleteByCursoCargoId(cursoCargoId);
    }
}
