package um.haberes.core.hexagonal.cursos.curso.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.in.DeleteCursoByIdUseCase;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.out.CursoRepository;

@Component
@RequiredArgsConstructor
public class DeleteCursoByIdUseCaseImpl implements DeleteCursoByIdUseCase {

    private final CursoRepository cursoRepository;

    @Transactional
    @Override
    public void deleteCursoById(Long cursoId) {
        cursoRepository.deleteByCursoId(cursoId);
    }
}
