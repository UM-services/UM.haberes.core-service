package um.haberes.core.hexagonal.cursos.curso_cargo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByPeriodoUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out.CursoCargoRepository;

@Component
@RequiredArgsConstructor
public class GetAllCursoCargosByPeriodoUseCaseImpl implements GetAllCursoCargosByPeriodoUseCase {

    private final CursoCargoRepository cursoCargoRepository;

    @Override
    public List<CursoCargo> getAllCursoCargosByPeriodo(Integer anho, Integer mes) {
        return cursoCargoRepository.findAllByAnhoAndMes(anho, mes);
    }
}
