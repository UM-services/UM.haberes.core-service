package um.haberes.core.hexagonal.cursos.curso.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;

public interface GetCursosByFacultadIdUseCase {

    List<Curso> getCursosByFacultadId(Integer facultadId);
}
