package um.haberes.core.hexagonal.cursos.curso.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.in.GetCursosByFacultadIdAndGeograficaIdAndPeriodoUseCase;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.out.CursoRepository;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByPeriodoUseCase;

@Component
@RequiredArgsConstructor
public class GetCursosByFacultadIdAndGeograficaIdAndPeriodoUseCaseImpl
        implements GetCursosByFacultadIdAndGeograficaIdAndPeriodoUseCase {

    private final CursoRepository cursoRepository;
    private final GetAllCursoCargosByPeriodoUseCase getAllCursoCargosByPeriodoUseCase;

    @Override
    public List<Curso> getCursosByFacultadIdAndGeograficaIdAndPeriodo(Integer facultadId, Integer geograficaId,
            Integer anho, Integer mes) {
        List<Long> cursoIds = getAllCursoCargosByPeriodoUseCase.getAllCursoCargosByPeriodo(anho, mes).stream()
                .map(CursoCargo::getCursoId)
                .toList();
        return cursoRepository.findAllByFacultadIdAndGeograficaIdAndCursoIdIn(facultadId, geograficaId, cursoIds);
    }
}
