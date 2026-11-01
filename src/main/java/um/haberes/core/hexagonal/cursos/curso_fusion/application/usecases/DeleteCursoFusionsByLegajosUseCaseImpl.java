package um.haberes.core.hexagonal.cursos.curso_fusion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in.DeleteCursoFusionsByLegajosUseCase;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.out.CursoFusionRepository;

@Component
@RequiredArgsConstructor
public class DeleteCursoFusionsByLegajosUseCaseImpl implements DeleteCursoFusionsByLegajosUseCase {

    private final CursoFusionRepository cursoFusionRepository;

    @Override
    @Transactional
    public void deleteCursoFusionsByLegajos(List<Long> legajoIds, Integer anho, Integer mes) {
        if (legajoIds == null || legajoIds.isEmpty()) {
            return;
        }
        cursoFusionRepository.deleteAllByLegajos(legajoIds, anho, mes);
    }
}
