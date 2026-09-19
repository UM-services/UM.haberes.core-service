package um.haberes.core.hexagonal.cursos.curso_fusion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.model.CursoFusion;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in.GetCursoFusionsByLegajoFacultadUseCase;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.out.CursoFusionRepository;

@Component
@RequiredArgsConstructor
public class GetCursoFusionsByLegajoFacultadUseCaseImpl implements GetCursoFusionsByLegajoFacultadUseCase {

    private final CursoFusionRepository cursoFusionRepository;

    @Override
    public List<CursoFusion> getCursoFusionsByLegajoFacultad(Long legajoId, Integer anho, Integer mes,
            Integer facultadId) {
        return cursoFusionRepository.findAllByLegajoAndFacultad(legajoId, anho, mes, facultadId);
    }
}
