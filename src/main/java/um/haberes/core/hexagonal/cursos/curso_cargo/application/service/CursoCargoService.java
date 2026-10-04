package um.haberes.core.hexagonal.cursos.curso_cargo.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.application.exception.CursoCargoException;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.CreateCursoCargoUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.DeleteCursoCargoByIdUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.DeleteCursoCargoByUniqueUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByCursoIdsUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByCursoUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByCargoTipoUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByFacultadUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByLegajoAndNivelIdsUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByLegajoAndNivelUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByLegajoDesarraigoUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByLegajoUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByLegajoWithAdicionalUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByPeriodoAndDesarraigoUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByPeriodoUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetCursoCargoByIdUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetCursoCargoByLegajoUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetCursoCargoByUniqueUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetTopCursoCargosByCursoUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetTopCursoCargosByPeriodoUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.SaveAllCursoCargosUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.UpdateCursoCargoUseCase;

@Service
@RequiredArgsConstructor
public class CursoCargoService {

    private final GetAllCursoCargosByLegajoUseCase getAllCursoCargosByLegajoUseCase;
    private final GetAllCursoCargosByLegajoAndNivelUseCase getAllCursoCargosByLegajoAndNivelUseCase;
    private final GetAllCursoCargosByLegajoAndNivelIdsUseCase getAllCursoCargosByLegajoAndNivelIdsUseCase;
    private final GetAllCursoCargosByLegajoDesarraigoUseCase getAllCursoCargosByLegajoDesarraigoUseCase;
    private final GetAllCursoCargosByCursoUseCase getAllCursoCargosByCursoUseCase;
    private final GetTopCursoCargosByCursoUseCase getTopCursoCargosByCursoUseCase;
    private final GetAllCursoCargosByFacultadUseCase getAllCursoCargosByFacultadUseCase;
    private final GetAllCursoCargosByCargoTipoUseCase getAllCursoCargosByCargoTipoUseCase;
    private final GetTopCursoCargosByPeriodoUseCase getTopCursoCargosByPeriodoUseCase;
    private final GetAllCursoCargosByPeriodoUseCase getAllCursoCargosByPeriodoUseCase;
    private final GetAllCursoCargosByPeriodoAndDesarraigoUseCase getAllCursoCargosByPeriodoAndDesarraigoUseCase;
    private final GetAllCursoCargosByLegajoWithAdicionalUseCase getAllCursoCargosByLegajoWithAdicionalUseCase;
    private final GetAllCursoCargosByCursoIdsUseCase getAllCursoCargosByCursoIdsUseCase;
    private final GetCursoCargoByIdUseCase getCursoCargoByIdUseCase;
    private final GetCursoCargoByUniqueUseCase getCursoCargoByUniqueUseCase;
    private final GetCursoCargoByLegajoUseCase getCursoCargoByLegajoUseCase;
    private final CreateCursoCargoUseCase createCursoCargoUseCase;
    private final UpdateCursoCargoUseCase updateCursoCargoUseCase;
    private final SaveAllCursoCargosUseCase saveAllCursoCargosUseCase;
    private final DeleteCursoCargoByIdUseCase deleteCursoCargoByIdUseCase;
    private final DeleteCursoCargoByUniqueUseCase deleteCursoCargoByUniqueUseCase;

    public List<CursoCargo> findAllByLegajoAndNivel(Long legajoId, Integer anho, Integer mes, Integer nivelId) {
        return getAllCursoCargosByLegajoAndNivelUseCase.getAllCursoCargosByLegajoAndNivel(legajoId, anho, mes,
                nivelId);
    }

    public List<CursoCargo> findAllByLegajoAndNivelIds(Long legajoId, Integer anho, Integer mes,
            List<Integer> nivelIds) {
        return getAllCursoCargosByLegajoAndNivelIdsUseCase.getAllCursoCargosByLegajoAndNivelIds(legajoId, anho, mes,
                nivelIds);
    }

    public List<CursoCargo> findAllByLegajo(Long legajoId, Integer anho, Integer mes) {
        return getAllCursoCargosByLegajoUseCase.getAllCursoCargosByLegajo(legajoId, anho, mes);
    }

    public List<CursoCargo> findAllByLegajoDesarraigo(Long legajoId, Integer anho, Integer mes) {
        return getAllCursoCargosByLegajoDesarraigoUseCase.getAllCursoCargosByLegajoDesarraigo(legajoId, anho, mes);
    }

    public List<CursoCargo> findAllByCurso(Long cursoId, Integer anho, Integer mes) {
        return getAllCursoCargosByCursoUseCase.getAllCursoCargosByCurso(cursoId, anho, mes);
    }

    public List<CursoCargo> findAnyByCursoId(Long cursoId) {
        return getTopCursoCargosByCursoUseCase.getTopCursoCargosByCurso(cursoId);
    }

    public List<CursoCargo> findAllByFacultad(Long legajoId, Integer anho, Integer mes, Integer facultadId) {
        return getAllCursoCargosByFacultadUseCase.getAllCursoCargosByFacultad(legajoId, anho, mes, facultadId);
    }

    public List<CursoCargo> findAllByCargoTipo(Long legajoId, Integer anho, Integer mes, Integer facultadId,
            Integer geograficaId, Byte anual, Byte semestre1, Byte semestre2, Integer cargoTipoId) {
        return getAllCursoCargosByCargoTipoUseCase.getAllCursoCargosByCargoTipo(legajoId, anho, mes, facultadId,
                geograficaId, anual, semestre1, semestre2, cargoTipoId);
    }

    public List<CursoCargo> findAnyByAnhoAndMes(Integer anho, Integer mes) {
        return getTopCursoCargosByPeriodoUseCase.getTopCursoCargosByPeriodo(anho, mes);
    }

    public List<CursoCargo> findAllByAnhoAndMes(Integer anho, Integer mes) {
        return getAllCursoCargosByPeriodoUseCase.getAllCursoCargosByPeriodo(anho, mes);
    }

    public List<CursoCargo> findAllByAnhoAndMesAndDesarraigo(Integer anho, Integer mes, Byte desarraigo) {
        return getAllCursoCargosByPeriodoAndDesarraigoUseCase.getAllCursoCargosByPeriodoAndDesarraigo(anho, mes,
                desarraigo);
    }

    public List<CursoCargo> findAllByLegajoWithAdicional(Long legajoId, Integer anho, Integer mes) {
        return getAllCursoCargosByLegajoWithAdicionalUseCase.getAllCursoCargosByLegajoWithAdicional(legajoId, anho,
                mes);
    }

    public List<CursoCargo> findAllByCursoIdIn(List<Long> cursoIds) {
        return getAllCursoCargosByCursoIdsUseCase.getAllCursoCargosByCursoIds(cursoIds);
    }

    public CursoCargo findByCursoCargoId(Long cursoCargoId) {
        return getCursoCargoByIdUseCase.getCursoCargoById(cursoCargoId)
                .orElseThrow(() -> new CursoCargoException(cursoCargoId));
    }

    public CursoCargo findByUnique(Long cursoId, Integer anho, Integer mes, Integer cargoTipoId, Long legajoId) {
        return getCursoCargoByUniqueUseCase.getCursoCargoByUnique(cursoId, anho, mes, cargoTipoId, legajoId)
                .orElseThrow(() -> new CursoCargoException(cursoId, anho, mes, cargoTipoId, legajoId));
    }

    public CursoCargo findByLegajo(Long cursoId, Integer anho, Integer mes, Long legajoId) {
        return getCursoCargoByLegajoUseCase.getCursoCargoByLegajo(cursoId, anho, mes, legajoId)
                .orElseThrow(() -> new CursoCargoException(cursoId, anho, mes, legajoId));
    }

    public CursoCargo add(CursoCargo cursoCargo) {
        return createCursoCargoUseCase.createCursoCargo(cursoCargo);
    }

    public CursoCargo update(CursoCargo cursoCargo, Long cursoCargoId) {
        return updateCursoCargoUseCase.updateCursoCargo(cursoCargoId, cursoCargo)
                .orElseThrow(() -> new CursoCargoException(cursoCargoId));
    }

    public List<CursoCargo> saveall(List<CursoCargo> cursoCargos) {
        return saveAllCursoCargosUseCase.saveAllCursoCargos(cursoCargos);
    }

    public void deleteByCursoCargoId(Long cursoCargoId) {
        deleteCursoCargoByIdUseCase.deleteCursoCargoById(cursoCargoId);
    }

    public void deleteByUnique(Long cursoId, Integer anho, Integer mes, Integer cargoTipoId, Long legajoId) {
        deleteCursoCargoByUniqueUseCase.deleteCursoCargoByUnique(cursoId, anho, mes, cargoTipoId, legajoId);
    }
}
