package um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in;

import um.haberes.core.hexagonal.cursos.curso_fusion.domain.model.CursoFusion;

public interface SaveCursoFusionUseCase {

    CursoFusion saveCursoFusion(CursoFusion cursoFusion);
}
