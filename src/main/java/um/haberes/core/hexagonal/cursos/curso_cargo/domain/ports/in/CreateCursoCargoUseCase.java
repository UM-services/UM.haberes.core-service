package um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in;

import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;

public interface CreateCursoCargoUseCase {

    CursoCargo createCursoCargo(CursoCargo cursoCargo);
}
