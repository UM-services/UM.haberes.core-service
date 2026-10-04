package um.haberes.core.hexagonal.cursos.curso_cargo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetTopCursoCargosByPeriodoUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out.CursoCargoRepository;

@Component
@RequiredArgsConstructor
public class GetTopCursoCargosByPeriodoUseCaseImpl implements GetTopCursoCargosByPeriodoUseCase {

    private final CursoCargoRepository cursoCargoRepository;

    @Override
    public List<CursoCargo> getTopCursoCargosByPeriodo(Integer anho, Integer mes) {
        return cursoCargoRepository.findTopByAnhoAndMes(anho, mes);
    }
}
