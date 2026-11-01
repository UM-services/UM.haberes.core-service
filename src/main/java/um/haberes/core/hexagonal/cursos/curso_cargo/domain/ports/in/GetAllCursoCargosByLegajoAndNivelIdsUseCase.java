package um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;

public interface GetAllCursoCargosByLegajoAndNivelIdsUseCase {

    List<CursoCargo> getAllCursoCargosByLegajoAndNivelIds(Long legajoId, Integer anho, Integer mes,
            List<Integer> nivelIds);
}
