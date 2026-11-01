package um.haberes.core.hexagonal.cursos.curso_desarraigo.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in.DeleteCursoDesarraigoByIdUseCase;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.out.CursoDesarraigoRepository;

@Component
@RequiredArgsConstructor
public class DeleteCursoDesarraigoByIdUseCaseImpl implements DeleteCursoDesarraigoByIdUseCase {

    private final CursoDesarraigoRepository cursoDesarraigoRepository;

    @Override
    public void deleteCursoDesarraigoById(Long cursoDesarraigoId) {
        cursoDesarraigoRepository.deleteByCursoDesarraigoId(cursoDesarraigoId);
    }
}
