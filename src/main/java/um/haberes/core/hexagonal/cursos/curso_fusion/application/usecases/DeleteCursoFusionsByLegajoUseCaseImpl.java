package um.haberes.core.hexagonal.cursos.curso_fusion.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in.DeleteCursoFusionsByLegajoUseCase;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.out.CursoFusionRepository;

@Component
@RequiredArgsConstructor
public class DeleteCursoFusionsByLegajoUseCaseImpl implements DeleteCursoFusionsByLegajoUseCase {

    private final CursoFusionRepository cursoFusionRepository;

    @Override
    @Transactional
    public void deleteCursoFusionsByLegajo(Long legajoId, Integer anho, Integer mes) {
        cursoFusionRepository.deleteAllByLegajo(legajoId, anho, mes);
    }
}
