package um.haberes.core.hexagonal.cursos.curso_cargo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByCursoIdsUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out.CursoCargoRepository;

@Component
@RequiredArgsConstructor
public class GetAllCursoCargosByCursoIdsUseCaseImpl implements GetAllCursoCargosByCursoIdsUseCase {

    private final CursoCargoRepository cursoCargoRepository;

    @Override
    public List<CursoCargo> getAllCursoCargosByCursoIds(List<Long> cursoIds) {
        return cursoCargoRepository.findAllByCursoIdIn(cursoIds);
    }
}
