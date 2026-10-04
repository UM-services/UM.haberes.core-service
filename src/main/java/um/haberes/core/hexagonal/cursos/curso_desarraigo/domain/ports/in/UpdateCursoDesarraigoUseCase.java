package um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.model.CursoDesarraigo;

public interface UpdateCursoDesarraigoUseCase {

    Optional<CursoDesarraigo> updateCursoDesarraigo(Long cursoDesarraigoId, CursoDesarraigo cursoDesarraigo);
}
