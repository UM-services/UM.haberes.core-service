package um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.cursos.curso_fusion.domain.model.CursoFusion;

public interface GetCursoFusionsByLegajoFacultadUseCase {

    List<CursoFusion> getCursoFusionsByLegajoFacultad(Long legajoId, Integer anho, Integer mes, Integer facultadId);
}
