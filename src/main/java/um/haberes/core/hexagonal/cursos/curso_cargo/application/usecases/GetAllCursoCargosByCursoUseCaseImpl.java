package um.haberes.core.hexagonal.cursos.curso_cargo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByCursoUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out.CursoCargoRepository;

@Component
@Slf4j
@RequiredArgsConstructor
public class GetAllCursoCargosByCursoUseCaseImpl implements GetAllCursoCargosByCursoUseCase {

    private final CursoCargoRepository cursoCargoRepository;

    @Override
    public List<CursoCargo> getAllCursoCargosByCurso(Long cursoId, Integer anho, Integer mes) {
        log.debug("Processing GetAllCursoCargosByCurso({}, {}, {})", cursoId, anho, mes);
        return cursoCargoRepository.findAllByCursoIdAndAnhoAndMes(cursoId, anho, mes);
    }
}
