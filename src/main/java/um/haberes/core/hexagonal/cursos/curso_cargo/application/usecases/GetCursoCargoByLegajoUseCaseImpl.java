package um.haberes.core.hexagonal.cursos.curso_cargo.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetCursoCargoByLegajoUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out.CursoCargoRepository;

@Component
@RequiredArgsConstructor
public class GetCursoCargoByLegajoUseCaseImpl implements GetCursoCargoByLegajoUseCase {

    private final CursoCargoRepository cursoCargoRepository;

    @Override
    public Optional<CursoCargo> getCursoCargoByLegajo(Long cursoId, Integer anho, Integer mes, Long legajoId) {
        return cursoCargoRepository.findByCursoIdAndAnhoAndMesAndLegajoId(cursoId, anho, mes, legajoId);
    }
}
