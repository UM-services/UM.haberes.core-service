package um.haberes.core.hexagonal.cursos.curso_fusion.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.model.CursoFusion;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in.SaveCursoFusionUseCase;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.out.CursoFusionRepository;

@Component
@RequiredArgsConstructor
public class SaveCursoFusionUseCaseImpl implements SaveCursoFusionUseCase {

    private final CursoFusionRepository cursoFusionRepository;

    @Override
    @Transactional
    public CursoFusion saveCursoFusion(CursoFusion cursoFusion) {
        return cursoFusionRepository.save(cursoFusion);
    }
}
