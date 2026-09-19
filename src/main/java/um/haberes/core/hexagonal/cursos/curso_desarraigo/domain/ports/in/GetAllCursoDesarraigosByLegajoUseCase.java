package um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.model.CursoDesarraigo;

public interface GetAllCursoDesarraigosByLegajoUseCase {

    List<CursoDesarraigo> getAllCursoDesarraigosByLegajo(Long legajoId, Integer anho, Integer mes);
}
