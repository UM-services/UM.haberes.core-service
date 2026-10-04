package um.haberes.core.hexagonal.cursos.curso.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;

public interface UpdateCursoUseCase {

    Optional<Curso> updateCurso(Long cursoId, Curso curso);
}
