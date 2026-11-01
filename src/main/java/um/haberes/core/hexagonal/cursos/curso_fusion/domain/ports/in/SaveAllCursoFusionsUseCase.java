package um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.cursos.curso_fusion.domain.model.CursoFusion;

public interface SaveAllCursoFusionsUseCase {

    List<CursoFusion> saveAllCursoFusions(List<CursoFusion> cursoFusiones);
}
