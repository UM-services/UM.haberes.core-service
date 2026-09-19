package um.haberes.core.hexagonal.cursos.curso_fusion.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in.DeleteCursoFusionsByFacultadUseCase;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.out.CursoFusionRepository;

@Component
@RequiredArgsConstructor
public class DeleteCursoFusionsByFacultadUseCaseImpl implements DeleteCursoFusionsByFacultadUseCase {

    private final CursoFusionRepository cursoFusionRepository;

    @Override
    @Transactional
    public void deleteCursoFusionsByFacultad(Long legajoId, Integer anho, Integer mes, Integer facultadId,
            Integer geograficaId) {
        cursoFusionRepository.deleteAllByLegajoAndFacultad(legajoId, anho, mes, facultadId, geograficaId);
    }
}
