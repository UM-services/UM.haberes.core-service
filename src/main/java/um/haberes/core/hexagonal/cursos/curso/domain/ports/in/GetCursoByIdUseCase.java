package um.haberes.core.hexagonal.cursos.curso.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;

public interface GetCursoByIdUseCase {

    Optional<Curso> getCursoById(Long cursoId);
}
