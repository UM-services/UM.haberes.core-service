/**
 *
 */
package um.haberes.core.service;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.CursoCargoException;
import um.haberes.core.kotlin.model.CursoCargo;
import um.haberes.core.repository.CursoCargoRepository;

/**
 * @author daniel
 */
@Service
@Slf4j
public class CursoCargoService {

    private final CursoCargoRepository repository;

    @Autowired
    public CursoCargoService(CursoCargoRepository repository) {
        this.repository = repository;
    }

    public List<CursoCargo> findAllByLegajoAndNivel(Long legajoId, Integer anho, Integer mes, Integer nivelId) {
        List<CursoCargo> cursoCargos = this.findAllByLegajo(legajoId, anho, mes);
        return cursoCargos.stream().filter(c -> c.getCurso().getNivelId().equals(nivelId)).collect(Collectors.toList());
    }

    public List<CursoCargo> findAllByLegajoAndNivelIds(Long legajoId, Integer anho, Integer mes, List<Integer> nivelIds) {
        List<CursoCargo> cursoCargos = this.findAllByLegajo(legajoId, anho, mes);
        return cursoCargos.stream().filter(c -> nivelIds.contains(c.getCurso().getNivelId())).collect(Collectors.toList());
    }

    public List<CursoCargo> findAllByLegajo(Long legajoId, Integer anho, Integer mes) {
        return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes,
                Sort.by("curso.facultadId").ascending().and(Sort.by("curso.geograficaId").ascending())
                        .and(Sort.by("cargoTipoId").ascending()).and(Sort.by("curso.nombre").ascending()));
    }

    public List<CursoCargo> findAllByLegajoDesarraigo(Long legajoId, Integer anho, Integer mes) {
        return repository.findAllByLegajoIdAndAnhoAndMesAndDesarraigo(legajoId, anho, mes, (byte) 1);
    }

    public List<CursoCargo> findAnyByCursoId(Long cursoId) {
        return repository.findTopByCursoId(cursoId);
    }

    public List<CursoCargo> findAllByCurso(Long cursoId, Integer anho, Integer mes) {
        return repository.findAllByCursoIdAndAnhoAndMes(cursoId, anho, mes,
                Sort.by("cargoTipo.aCargo").descending().and(Sort.by("cargoTipoId").ascending()));
    }

    public List<CursoCargo> findAllByFacultad(Long legajoId, Integer anho, Integer mes, Integer facultadId) {
        return repository.findAllByLegajoIdAndAnhoAndMesAndCursoFacultadId(legajoId, anho, mes, facultadId);
//        return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes, null).stream()
//                .filter(c -> c.getCurso().getFacultadId().equals(facultadId)).collect(Collectors.toList());
    }

    public List<CursoCargo> findAllByCargoTipo(Long legajoId, Integer anho, Integer mes, Integer facultadId,
                                               Integer geograficaId, Byte anual, Byte semestre1, Byte semestre2, Integer cargoTipoId) {
        return repository.findAllByLegajoIdAndAnhoAndMesAndCargoTipoId(legajoId, anho, mes, cargoTipoId).stream()
                .filter(c -> c.getCurso().getFacultadId().equals(facultadId)
                        && c.getCurso().getGeograficaId().equals(geograficaId) && c.getCurso().getAnual() == anual
                        && c.getCurso().getSemestre1() == semestre1 && c.getCurso().getSemestre2() == semestre2)
                .collect(Collectors.toList());
    }

    public List<CursoCargo> findAnyByAnhoAndMes(Integer anho, Integer mes) {
        return repository.findTopByAnhoAndMes(anho, mes);
    }

    public List<CursoCargo> findAllByAnhoAndMes(Integer anho, Integer mes) {
        return repository.findAllByAnhoAndMes(anho, mes);
    }

    public List<CursoCargo> findAllByAnhoAndMesAndDesarraigo(Integer anho, Integer mes, Byte desarraigo) {
        return repository.findAllByAnhoAndMesAndDesarraigo(anho, mes, desarraigo);
    }

    public List<CursoCargo> findAllByLegajoWithAdicional(Long legajoId, Integer anho, Integer mes) {
        return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes, Sort.by("cursoCargoId")).stream()
                .filter(cursoCargo -> cursoCargo.getCurso().getAdicionalCargaHoraria() == 1)
                .collect(Collectors.toList());
    }

    public List<CursoCargo> findAllByCursoIdIn(List<Long> cursoIds) {
        return repository.findAllByCursoIdIn(cursoIds);
    }

    public CursoCargo findByCursoCargoId(Long cursoCargoId) {
        return repository.findByCursoCargoId(cursoCargoId)
                .orElseThrow(() -> new CursoCargoException(cursoCargoId));
    }

    public CursoCargo findByUnique(Long cursoId, Integer anho, Integer mes, Integer cargoTipoId, Long legajoId) {
        return repository.findByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(cursoId, anho, mes, cargoTipoId, legajoId)
                .orElseThrow(() -> new CursoCargoException(cursoId, anho, mes, cargoTipoId, legajoId));
    }

    public CursoCargo findByLegajo(Long cursoId, Integer anho, Integer mes, Long legajoId) {
        var cursoCargo = repository.findByCursoIdAndAnhoAndMesAndLegajoId(cursoId, anho, mes, legajoId)
                .orElseThrow(() -> new CursoCargoException(cursoId, anho, mes, legajoId));
        try {
            log.debug("CursoCargo -> " + JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(cursoCargo));
        } catch (JsonProcessingException e) {
            log.debug("CursoCargo -> null {}", e.getMessage());
        }
        return cursoCargo;
    }

    public CursoCargo add(CursoCargo cursoCargo) {
        repository.save(cursoCargo);
        return cursoCargo;
    }

    public CursoCargo update(CursoCargo newCursoCargo, Long cursoCargoId) {
        return repository.findByCursoCargoId(cursoCargoId).map(cursoCargo -> {
            cursoCargo = new CursoCargo(cursoCargoId, newCursoCargo.getCursoId(), newCursoCargo.getAnho(),
                    newCursoCargo.getMes(), newCursoCargo.getCargoTipoId(), newCursoCargo.getLegajoId(),
                    newCursoCargo.getHorasSemanales(), newCursoCargo.getHorasTotales(),
                    newCursoCargo.getDesignacionTipoId(), newCursoCargo.getCategoriaId(), newCursoCargo.getDesarraigo(),
                    newCursoCargo.getCursoCargoNovedadId(), newCursoCargo.getCurso(), newCursoCargo.getCargoTipo(),
                    newCursoCargo.getPersona(), newCursoCargo.getDesignacionTipo(), newCursoCargo.getCategoria());
            repository.save(cursoCargo);
            return cursoCargo;
        }).orElseThrow(() -> new CursoCargoException(cursoCargoId));
    }

    @Transactional
    public void deleteByCursoCargoId(Long cursoCargoId) {
        repository.deleteByCursoCargoId(cursoCargoId);
    }

    @Transactional
    public void deleteByUnique(Long cursoId, Integer anho, Integer mes, Integer cargoTipoId, Long legajoId) {
        repository.deleteByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(cursoId, anho, mes, cargoTipoId, legajoId);
    }

    @Transactional
    public List<CursoCargo> saveall(List<CursoCargo> cursoCargos) {
        cursoCargos = repository.saveAll(cursoCargos);
        return cursoCargos;
    }

}
