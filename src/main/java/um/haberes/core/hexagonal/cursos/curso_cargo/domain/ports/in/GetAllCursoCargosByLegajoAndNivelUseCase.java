package um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;

public interface GetAllCursoCargosByLegajoAndNivelUseCase {

    List<CursoCargo> getAllCursoCargosByLegajoAndNivel(Long legajoId, Integer anho, Integer mes, Integer nivelId);
}
