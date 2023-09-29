/**
 * 
 */
package um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.AdicionalCursoTablaNotFoundException;
import um.haberes.rest.kotlin.model.AdicionalCursoTabla;
import um.haberes.rest.repository.IAdicionalCursoTablaRepository;
import um.haberes.rest.util.Periodo;

/**
 * @author daniel
 *
 */
@Service
public class AdicionalCursoTablaService {

	@Autowired
	private IAdicionalCursoTablaRepository repository;

	public List<AdicionalCursoTabla> findAll() {
		return repository.findAll();
	}

	public AdicionalCursoTabla findByAdicionalCursoTablaId(Long adicionalCursoTablaId) {
		return repository.findByAdicionalCursoTablaId(adicionalCursoTablaId)
				.orElseThrow(() -> new AdicionalCursoTablaNotFoundException(adicionalCursoTablaId));
	}

	public AdicionalCursoTabla findByFacultadIdAndPeriodo(Integer facultadId, Integer anho, Integer mes) {
		return repository
				.findByFacultadIdAndPeriodoDesdeLessThanEqualAndPeriodoHastaGreaterThanEqual(facultadId,
						Periodo.toLong(anho, mes), Periodo.toLong(anho, mes))
				.orElseThrow(() -> new AdicionalCursoTablaNotFoundException(facultadId, anho, mes));
	}

}
