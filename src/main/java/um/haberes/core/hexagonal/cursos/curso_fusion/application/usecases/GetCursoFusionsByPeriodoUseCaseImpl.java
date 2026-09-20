package um.haberes.core.hexagonal.cursos.curso_fusion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.model.CursoFusion;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in.GetCursoFusionsByPeriodoUseCase;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.out.CursoFusionRepository;

@Component
@RequiredArgsConstructor
public class GetCursoFusionsByPeriodoUseCaseImpl implements GetCursoFusionsByPeriodoUseCase {

    private final CursoFusionRepository cursoFusionRepository;

    @Override
    public List<CursoFusion> getCursoFusionsByPeriodo(Integer anho, Integer mes) {
        return cursoFusionRepository.findAllByPeriodo(anho, mes);
    }
}
