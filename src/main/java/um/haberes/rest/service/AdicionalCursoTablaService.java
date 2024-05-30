/**
 *
 */
package um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.AdicionalCursoTablaException;
import um.haberes.rest.kotlin.model.AdicionalCursoTabla;
import um.haberes.rest.repository.IAdicionalCursoTablaRepository;
import um.haberes.rest.util.Periodo;

/**
 * @author daniel
 *
 */
@Service
public class AdicionalCursoTablaService {

    private final IAdicionalCursoTablaRepository repository;

    @Autowired
    public AdicionalCursoTablaService(IAdicionalCursoTablaRepository repository) {
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

}
