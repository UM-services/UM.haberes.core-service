package um.haberes.core.hexagonal.cursos.curso_desarraigo.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.application.exception.CursoDesarraigoException;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.model.CursoDesarraigo;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in.CreateCursoDesarraigoUseCase;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in.DeleteCursoDesarraigoByIdUseCase;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in.GetAllCursoDesarraigosByLegajoUseCase;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in.GetAllCursoDesarraigosUseCase;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in.GetAllCursoDesarraigosByVersionUseCase;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in.GetCursoDesarraigoByIdUseCase;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in.GetCursoDesarraigoByUniqueUseCase;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in.SaveAllCursoDesarraigosUseCase;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in.UpdateCursoDesarraigoUseCase;

@Service
@RequiredArgsConstructor
public class CursoDesarraigoService {

    private final GetAllCursoDesarraigosUseCase getAllCursoDesarraigosUseCase;
    private final GetAllCursoDesarraigosByLegajoUseCase getAllCursoDesarraigosByLegajoUseCase;
    private final GetAllCursoDesarraigosByVersionUseCase getAllCursoDesarraigosByVersionUseCase;
    private final GetCursoDesarraigoByIdUseCase getCursoDesarraigoByIdUseCase;
    private final GetCursoDesarraigoByUniqueUseCase getCursoDesarraigoByUniqueUseCase;
    private final CreateCursoDesarraigoUseCase createCursoDesarraigoUseCase;
    private final UpdateCursoDesarraigoUseCase updateCursoDesarraigoUseCase;
    private final SaveAllCursoDesarraigosUseCase saveAllCursoDesarraigosUseCase;
    private final DeleteCursoDesarraigoByIdUseCase deleteCursoDesarraigoByIdUseCase;

    public List<CursoDesarraigo> findAll() {
        return getAllCursoDesarraigosUseCase.getAllCursoDesarraigos();
    }

    public List<CursoDesarraigo> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes) {
        return getAllCursoDesarraigosByLegajoUseCase.getAllCursoDesarraigosByLegajo(legajoId, anho, mes);
    }

    public List<CursoDesarraigo> findAllByVersion(Long legajoId, Integer anho, Integer mes, Integer version) {
        return getAllCursoDesarraigosByVersionUseCase.getAllCursoDesarraigosByVersion(legajoId, anho, mes, version);
    }

    public CursoDesarraigo findByCursoDesarraigoId(Long cursoDesarraigoId) {
        return getCursoDesarraigoByIdUseCase.getCursoDesarraigoById(cursoDesarraigoId)
                .orElseThrow(() -> new CursoDesarraigoException(cursoDesarraigoId));
    }

    public CursoDesarraigo findByUnique(Long legajoId, Integer anho, Integer mes, Long cursoId) {
        return getCursoDesarraigoByUniqueUseCase.getCursoDesarraigoByUnique(legajoId, anho, mes, cursoId)
                .orElseThrow(() -> new CursoDesarraigoException(legajoId, anho, mes, cursoId));
    }

    public void delete(Long cursoDesarraigoId) {
        deleteCursoDesarraigoByIdUseCase.deleteCursoDesarraigoById(cursoDesarraigoId);
    }

    public CursoDesarraigo add(CursoDesarraigo cursoDesarraigo) {
        return createCursoDesarraigoUseCase.createCursoDesarraigo(cursoDesarraigo);
    }

    public CursoDesarraigo update(CursoDesarraigo cursoDesarraigo, Long cursoDesarraigoId) {
        return updateCursoDesarraigoUseCase.updateCursoDesarraigo(cursoDesarraigoId, cursoDesarraigo)
                .orElseThrow(() -> new CursoDesarraigoException(cursoDesarraigoId));
    }

    public List<CursoDesarraigo> saveAll(List<CursoDesarraigo> cursoDesarraigos) {
        return saveAllCursoDesarraigosUseCase.saveAllCursoDesarraigos(cursoDesarraigos);
    }
}
