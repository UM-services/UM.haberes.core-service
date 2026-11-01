package um.haberes.core.hexagonal.cursos.curso.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso.application.exception.CursoException;
import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.in.CreateCursoUseCase;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.in.DeleteCursoByIdUseCase;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.in.GetAllCursosUseCase;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.in.GetCursoByIdUseCase;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.in.GetCursosByCursoIdsUseCase;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.in.GetCursosByFacultadIdAndGeograficaIdAndConditionsUseCase;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.in.GetCursosByFacultadIdAndGeograficaIdAndPeriodoUseCase;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.in.GetCursosByFacultadIdAndGeograficaIdUseCase;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.in.GetCursosByFacultadIdUseCase;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.in.UpdateCursoUseCase;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final GetAllCursosUseCase getAllCursosUseCase;
    private final GetCursosByFacultadIdAndGeograficaIdAndConditionsUseCase getCursosByFacultadIdAndGeograficaIdAndConditionsUseCase;
    private final GetCursosByFacultadIdAndGeograficaIdUseCase getCursosByFacultadIdAndGeograficaIdUseCase;
    private final GetCursosByCursoIdsUseCase getCursosByCursoIdsUseCase;
    private final GetCursosByFacultadIdUseCase getCursosByFacultadIdUseCase;
    private final GetCursosByFacultadIdAndGeograficaIdAndPeriodoUseCase getCursosByFacultadIdAndGeograficaIdAndPeriodoUseCase;
    private final GetCursoByIdUseCase getCursoByIdUseCase;
    private final CreateCursoUseCase createCursoUseCase;
    private final UpdateCursoUseCase updateCursoUseCase;
    private final DeleteCursoByIdUseCase deleteCursoByIdUseCase;

    public List<Curso> findAll() {
        return getAllCursosUseCase.getAllCursos();
    }

    public List<Curso> findAllByGeograficaAndConditions(Integer facultadId, Integer geograficaId,
            List<String> conditions) {
        return getCursosByFacultadIdAndGeograficaIdAndConditionsUseCase
                .getCursosByFacultadIdAndGeograficaIdAndConditions(facultadId, geograficaId, conditions);
    }

    public List<Curso> findAllByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId) {
        return getCursosByFacultadIdAndGeograficaIdUseCase.getCursosByFacultadIdAndGeograficaId(facultadId,
                geograficaId);
    }

    public List<Curso> findAllByCursoIdIn(List<Long> ids) {
        return getCursosByCursoIdsUseCase.getCursosByCursoIds(ids);
    }

    public List<Curso> findAllByFacultadId(Integer facultadId) {
        return getCursosByFacultadIdUseCase.getCursosByFacultadId(facultadId);
    }

    public List<Curso> findAllByFacultadIdAndGeograficaIdAndAnhoAndMes(Integer facultadId, Integer geograficaId,
            Integer anho, Integer mes) {
        return getCursosByFacultadIdAndGeograficaIdAndPeriodoUseCase
                .getCursosByFacultadIdAndGeograficaIdAndPeriodo(facultadId, geograficaId, anho, mes);
    }

    public Curso findByCursoId(Long cursoId) {
        return getCursoByIdUseCase.getCursoById(cursoId).orElseThrow(() -> new CursoException(cursoId));
    }

    public Curso add(Curso curso) {
        return createCursoUseCase.createCurso(curso);
    }

    public Curso update(Curso curso, Long cursoId) {
        return updateCursoUseCase.updateCurso(cursoId, curso).orElseThrow(() -> new CursoException(cursoId));
    }

    public void deleteByCursoId(Long cursoId) {
        deleteCursoByIdUseCase.deleteCursoById(cursoId);
    }

}
