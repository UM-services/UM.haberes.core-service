package um.haberes.core.hexagonal.cursos.curso.domain.ports.in;

import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;

public interface CreateCursoUseCase {

    Curso createCurso(Curso curso);
}
