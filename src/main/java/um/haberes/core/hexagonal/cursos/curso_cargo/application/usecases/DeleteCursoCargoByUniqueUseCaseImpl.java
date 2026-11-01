package um.haberes.core.hexagonal.cursos.curso_cargo.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.DeleteCursoCargoByUniqueUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out.CursoCargoRepository;

@Component
@RequiredArgsConstructor
public class DeleteCursoCargoByUniqueUseCaseImpl implements DeleteCursoCargoByUniqueUseCase {

    private final CursoCargoRepository cursoCargoRepository;

    @Transactional
    @Override
    public void deleteCursoCargoByUnique(Long cursoId, Integer anho, Integer mes, Integer cargoTipoId, Long legajoId) {
        cursoCargoRepository.deleteByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(cursoId, anho, mes, cargoTipoId,
                legajoId);
    }
}
