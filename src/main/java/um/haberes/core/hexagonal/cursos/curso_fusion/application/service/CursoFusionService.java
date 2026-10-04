package um.haberes.core.hexagonal.cursos.curso_fusion.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.model.CursoFusion;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in.DeleteCursoFusionUseCase;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in.DeleteCursoFusionsByFacultadUseCase;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in.DeleteCursoFusionsByLegajoUseCase;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in.DeleteCursoFusionsByLegajosUseCase;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in.GetCursoFusionsByLegajoFacultadUseCase;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in.GetCursoFusionsByLegajoUseCase;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in.GetCursoFusionsByPeriodoUseCase;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in.SaveAllCursoFusionsUseCase;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.ports.in.SaveCursoFusionUseCase;

@Service
@RequiredArgsConstructor
public class CursoFusionService {

    private final GetCursoFusionsByLegajoUseCase getCursoFusionsByLegajoUseCase;
    private final GetCursoFusionsByLegajoFacultadUseCase getCursoFusionsByLegajoFacultadUseCase;
    private final GetCursoFusionsByPeriodoUseCase getCursoFusionsByPeriodoUseCase;
    private final SaveCursoFusionUseCase saveCursoFusionUseCase;
    private final SaveAllCursoFusionsUseCase saveAllCursoFusionsUseCase;
    private final DeleteCursoFusionUseCase deleteCursoFusionUseCase;
    private final DeleteCursoFusionsByFacultadUseCase deleteCursoFusionsByFacultadUseCase;
    private final DeleteCursoFusionsByLegajosUseCase deleteCursoFusionsByLegajosUseCase;
    private final DeleteCursoFusionsByLegajoUseCase deleteCursoFusionsByLegajoUseCase;

    public List<CursoFusion> findAllByLegajoId(Long legajoId, Integer anho, Integer mes) {
        return getCursoFusionsByLegajoUseCase.getCursoFusionsByLegajo(legajoId, anho, mes);
    }

    public List<CursoFusion> findAllByLegajoIdAndFacultadId(Long legajoId, Integer anho, Integer mes,
            Integer facultadId) {
        return getCursoFusionsByLegajoFacultadUseCase.getCursoFusionsByLegajoFacultad(legajoId, anho, mes, facultadId);
    }

    public List<CursoFusion> findAllByAnhoAndMes(Integer anho, Integer mes) {
        return getCursoFusionsByPeriodoUseCase.getCursoFusionsByPeriodo(anho, mes);
    }

    public CursoFusion add(CursoFusion cursoFusion) {
        return saveCursoFusionUseCase.saveCursoFusion(cursoFusion);
    }

    public List<CursoFusion> saveAll(List<CursoFusion> cursoFusiones) {
        return saveAllCursoFusionsUseCase.saveAllCursoFusions(cursoFusiones);
    }

    public void deleteByCursoFusionId(Long cursoFusionId) {
        deleteCursoFusionUseCase.deleteCursoFusion(cursoFusionId);
    }

    public void deleteAllByLegajoIdAndAnhoAndMesAndFacultadIdAndGeograficaId(Long legajoId, Integer anho, Integer mes,
            Integer facultadId, Integer geograficaId) {
        deleteCursoFusionsByFacultadUseCase.deleteCursoFusionsByFacultad(legajoId, anho, mes, facultadId,
                geograficaId);
    }

    public void deleteAllByLegajoIdInAndAnhoAndMes(List<Long> legajos, Integer anho, Integer mes) {
        deleteCursoFusionsByLegajosUseCase.deleteCursoFusionsByLegajos(legajos, anho, mes);
    }

    public void deleteAllByLegajoIdAndPeriodo(Long legajoId, Integer anho, Integer mes) {
        deleteCursoFusionsByLegajoUseCase.deleteCursoFusionsByLegajo(legajoId, anho, mes);
    }
}
