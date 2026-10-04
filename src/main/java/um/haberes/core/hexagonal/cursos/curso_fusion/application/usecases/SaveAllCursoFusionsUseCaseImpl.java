package um.haberes.core.hexagonal.cursos.curso_fusion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.model.CursoFusion;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in.SaveAllCursoFusionsUseCase;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.out.CursoFusionRepository;

@Component
@RequiredArgsConstructor
public class SaveAllCursoFusionsUseCaseImpl implements SaveAllCursoFusionsUseCase {

    private final CursoFusionRepository cursoFusionRepository;

    @Override
    @Transactional
    public List<CursoFusion> saveAllCursoFusions(List<CursoFusion> cursoFusiones) {
        return cursoFusionRepository.saveAll(cursoFusiones);
    }
}
