package um.haberes.core.hexagonal.cursos.curso_fusion.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in.DeleteCursoFusionUseCase;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.out.CursoFusionRepository;

@Component
@RequiredArgsConstructor
public class DeleteCursoFusionUseCaseImpl implements DeleteCursoFusionUseCase {

    private final CursoFusionRepository cursoFusionRepository;

    @Override
    @Transactional
    public void deleteCursoFusion(Long cursoFusionId) {
        cursoFusionRepository.deleteByCursoFusionId(cursoFusionId);
    }
}
