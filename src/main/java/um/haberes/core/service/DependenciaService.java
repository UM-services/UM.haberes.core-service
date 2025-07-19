/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.cache.annotation.Cacheable;
import java.util.Set;
import um.haberes.core.exception.DependenciaException;
import um.haberes.core.kotlin.model.Dependencia;
import um.haberes.core.repository.DependenciaRepository;

/**
 * @author daniel
 *
 */
@Service
public class DependenciaService {
	@Autowired
	private DependenciaRepository repository;

	@Cacheable("dependencias")
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

	public List<Dependencia> findAllByIds(Set<Integer> dependenciaIds) {
		return repository.findAllByDependenciaIdIn(dependenciaIds);
	}
}
