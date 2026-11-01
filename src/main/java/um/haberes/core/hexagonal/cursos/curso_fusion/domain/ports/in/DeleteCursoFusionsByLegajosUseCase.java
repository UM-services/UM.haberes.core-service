package um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in;

import java.util.List;

public interface DeleteCursoFusionsByLegajosUseCase {

    void deleteCursoFusionsByLegajos(List<Long> legajoIds, Integer anho, Integer mes);
}
