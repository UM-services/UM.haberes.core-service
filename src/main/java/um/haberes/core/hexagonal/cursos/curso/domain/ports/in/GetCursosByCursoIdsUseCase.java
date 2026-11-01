package um.haberes.core.hexagonal.cursos.curso.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;

public interface GetCursosByCursoIdsUseCase {

    List<Curso> getCursosByCursoIds(List<Long> cursoIds);
}
