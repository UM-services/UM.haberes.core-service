package um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;

public interface UpdateCursoCargoUseCase {

    Optional<CursoCargo> updateCursoCargo(Long cursoCargoId, CursoCargo cursoCargo);
}
