package um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in;

import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.model.CursoDesarraigo;

public interface CreateCursoDesarraigoUseCase {

    CursoDesarraigo createCursoDesarraigo(CursoDesarraigo cursoDesarraigo);
}
