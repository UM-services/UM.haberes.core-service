package um.haberes.core.hexagonal.cursos.curso_fusion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.model.CursoFusion;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in.GetCursoFusionsByLegajoUseCase;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.out.CursoFusionRepository;

@Component
@RequiredArgsConstructor
public class GetCursoFusionsByLegajoUseCaseImpl implements GetCursoFusionsByLegajoUseCase {

    private final CursoFusionRepository cursoFusionRepository;

    @Override
    public List<CursoFusion> getCursoFusionsByLegajo(Long legajoId, Integer anho, Integer mes) {
        return cursoFusionRepository.findAllByLegajo(legajoId, anho, mes);
    }
}
