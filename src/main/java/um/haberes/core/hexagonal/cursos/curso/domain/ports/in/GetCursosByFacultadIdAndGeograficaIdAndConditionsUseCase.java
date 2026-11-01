package um.haberes.core.hexagonal.cursos.curso.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;

public interface GetCursosByFacultadIdAndGeograficaIdAndConditionsUseCase {

    List<Curso> getCursosByFacultadIdAndGeograficaIdAndConditions(Integer facultadId, Integer geograficaId,
            List<String> conditions);
}
