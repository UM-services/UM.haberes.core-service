/**
 *
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.AdicionalCursoTablaException;
import um.haberes.core.kotlin.model.AdicionalCursoTabla;
import um.haberes.core.repository.AdicionalCursoTablaRepository;
import java.util.Set;
import um.haberes.core.util.Periodo;

/**
 * @author daniel
 *
 */
@Service
public class AdicionalCursoTablaService {

    private final AdicionalCursoTablaRepository repository;

    @Autowired
    public AdicionalCursoTablaService(AdicionalCursoTablaRepository repository) {
        this.repository = repository;
    }

    public List<AdicionalCursoTabla> findAll() {
        return repository.findAll();
    }

    public AdicionalCursoTabla findByAdicionalCursoTablaId(Long adicionalCursoTablaId) {
        return repository.findByAdicionalCursoTablaId(adicionalCursoTablaId)
                .orElseThrow(() -> new AdicionalCursoTablaException(adicionalCursoTablaId));
    }

    public AdicionalCursoTabla findByFacultadIdAndPeriodo(Integer facultadId, Integer anho, Integer mes) {
        return repository
                .findByFacultadIdAndGeograficaIdIsNullAndPeriodoDesdeLessThanEqualAndPeriodoHastaGreaterThanEqual(facultadId,
                        Periodo.toLong(anho, mes), Periodo.toLong(anho, mes))
                .orElseThrow(() -> new AdicionalCursoTablaException(facultadId, anho, mes));
    }

    public AdicionalCursoTabla findByFacultadIdAndGeograficaIdAndPeriodo(Integer facultadId, Integer geograficaId, Integer anho, Integer mes) {
        return repository
                .findByFacultadIdAndGeograficaIdAndPeriodoDesdeLessThanEqualAndPeriodoHastaGreaterThanEqual(facultadId,
                        geograficaId, Periodo.toLong(anho, mes), Periodo.toLong(anho, mes))
                .orElseThrow(() -> new AdicionalCursoTablaException(facultadId, geograficaId, anho, mes));
    }

    public List<AdicionalCursoTabla> findAllByFacultadesAndPeriodo(Set<Integer> facultadIds, Integer anho, Integer mes) {
        return repository
                .findAllByFacultadIdInAndPeriodoDesdeLessThanEqualAndPeriodoHastaGreaterThanEqual(facultadIds,
                        Periodo.toLong(anho, mes), Periodo.toLong(anho, mes));
    }

}
