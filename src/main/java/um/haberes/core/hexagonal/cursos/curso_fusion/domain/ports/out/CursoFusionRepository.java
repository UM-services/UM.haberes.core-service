package um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.out;

import java.util.List;

import um.haberes.core.hexagonal.cursos.curso_fusion.domain.model.CursoFusion;

public interface CursoFusionRepository {

    List<CursoFusion> findAllByLegajo(Long legajoId, Integer anho, Integer mes);

    List<CursoFusion> findAllByLegajoAndFacultad(Long legajoId, Integer anho, Integer mes, Integer facultadId);

    List<CursoFusion> findAllByPeriodo(Integer anho, Integer mes);

    CursoFusion save(CursoFusion cursoFusion);

    List<CursoFusion> saveAll(List<CursoFusion> cursoFusiones);

    void deleteByCursoFusionId(Long cursoFusionId);

    void deleteAllByLegajoAndFacultad(Long legajoId, Integer anho, Integer mes, Integer facultadId,
            Integer geograficaId);

    void deleteAllByLegajos(List<Long> legajoIds, Integer anho, Integer mes);

    void deleteAllByLegajo(Long legajoId, Integer anho, Integer mes);
}
