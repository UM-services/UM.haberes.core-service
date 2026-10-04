package um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;

public interface GetTopCursoCargosByCursoUseCase {

    List<CursoCargo> getTopCursoCargosByCurso(Long cursoId);
}
