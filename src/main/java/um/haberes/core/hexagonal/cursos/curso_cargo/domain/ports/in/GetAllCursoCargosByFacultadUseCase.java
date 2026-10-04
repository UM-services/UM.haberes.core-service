package um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;

public interface GetAllCursoCargosByFacultadUseCase {

    List<CursoCargo> getAllCursoCargosByFacultad(Long legajoId, Integer anho, Integer mes, Integer facultadId);
}
