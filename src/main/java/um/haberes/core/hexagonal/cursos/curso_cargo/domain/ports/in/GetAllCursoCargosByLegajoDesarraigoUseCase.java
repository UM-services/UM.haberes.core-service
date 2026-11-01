package um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;

public interface GetAllCursoCargosByLegajoDesarraigoUseCase {

    List<CursoCargo> getAllCursoCargosByLegajoDesarraigo(Long legajoId, Integer anho, Integer mes);
}
