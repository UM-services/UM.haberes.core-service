package um.haberes.core.hexagonal.cursos.curso_cargo.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetCursoCargoByUniqueUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out.CursoCargoRepository;

@Component
@RequiredArgsConstructor
public class GetCursoCargoByUniqueUseCaseImpl implements GetCursoCargoByUniqueUseCase {

    private final CursoCargoRepository cursoCargoRepository;

    @Override
    public Optional<CursoCargo> getCursoCargoByUnique(Long cursoId, Integer anho, Integer mes, Integer cargoTipoId,
            Long legajoId) {
        return cursoCargoRepository.findByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(cursoId, anho, mes,
                cargoTipoId, legajoId);
    }
}
