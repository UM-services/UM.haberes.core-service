package um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.model.CursoDesarraigo;

public interface GetCursoDesarraigoByUniqueUseCase {

    Optional<CursoDesarraigo> getCursoDesarraigoByUnique(Long legajoId, Integer anho, Integer mes, Long cursoId);
}
