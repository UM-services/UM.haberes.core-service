/**
 *
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.AdicionalCursoTablaException;
import um.haberes.core.model.AdicionalCursoTablaEntity;
import um.haberes.core.repository.JpaAdicionalCursoTablaRepository;
import java.util.Set;
import um.haberes.core.util.Periodo;

/**
 * @author daniel
 *
 */
@Service
public class AdicionalCursoTablaService {

    private final JpaAdicionalCursoTablaRepository repository;

    @Autowired
    public AdicionalCursoTablaService(JpaAdicionalCursoTablaRepository repository) {
        this.repository = repository;
    }

    public List<AdicionalCursoTablaEntity> findAll() {
        return repository.findAll();
    }

    public AdicionalCursoTablaEntity findByAdicionalCursoTablaId(Long adicionalCursoTablaId) {
        return repository.findByAdicionalCursoTablaId(adicionalCursoTablaId)
                .orElseThrow(() -> new AdicionalCursoTablaException(adicionalCursoTablaId));
    }

    public AdicionalCursoTablaEntity findByFacultadIdAndPeriodo(Integer facultadId, Integer anho, Integer mes) {
        return repository
                .findByFacultadIdAndGeograficaIdIsNullAndPeriodoDesdeLessThanEqualAndPeriodoHastaGreaterThanEqual(facultadId,
                        Periodo.toLong(anho, mes), Periodo.toLong(anho, mes))
                .orElseThrow(() -> new AdicionalCursoTablaException(facultadId, anho, mes));
    }

    public AdicionalCursoTablaEntity findByFacultadIdAndGeograficaIdAndPeriodo(Integer facultadId, Integer geograficaId, Integer anho, Integer mes) {
        return repository
                .findByFacultadIdAndGeograficaIdAndPeriodoDesdeLessThanEqualAndPeriodoHastaGreaterThanEqual(facultadId,
                        geograficaId, Periodo.toLong(anho, mes), Periodo.toLong(anho, mes))
                .orElseThrow(() -> new AdicionalCursoTablaException(facultadId, geograficaId, anho, mes));
    }

    public List<AdicionalCursoTablaEntity> findAllByFacultadesAndPeriodo(Set<Integer> facultadIds, Integer anho, Integer mes) {
        return repository
                .findAllByFacultadIdInAndPeriodoDesdeLessThanEqualAndPeriodoHastaGreaterThanEqual(facultadIds,
                        Periodo.toLong(anho, mes), Periodo.toLong(anho, mes));
    }

}
