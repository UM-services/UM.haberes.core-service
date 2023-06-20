/**
 * 
 */
package um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.DependenciaException;
import um.haberes.rest.kotlin.model.Dependencia;
import um.haberes.rest.repository.IDependenciaRepository;

/**
 * @author daniel
 *
 */
@Service
public class DependenciaService {
	@Autowired
	private IDependenciaRepository repository;

	public List<Dependencia> findAll() {
		return repository.findAll();
	}

	public List<Dependencia> findAllByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId) {
		return repository.findAllByFacultadIdAndGeograficaId(facultadId, geograficaId);
	}

	public Dependencia findByDependenciaId(Integer dependenciaId) {
		return repository.findByDependenciaId(dependenciaId).orElseThrow(() -> new DependenciaException(dependenciaId));
	}

	public Dependencia findFirstByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId) {
		return repository.findFirstByFacultadIdAndGeograficaId(facultadId, geograficaId).orElseThrow(() -> new DependenciaException(facultadId, geograficaId));
	}
}
