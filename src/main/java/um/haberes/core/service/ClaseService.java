/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.ClaseException;
import um.haberes.core.model.ClaseEntity;
import um.haberes.core.repository.JpaClaseRepository;

/**
 * @author daniel
 *
 */
@Service
public class ClaseService {

	private final JpaClaseRepository repository;

	@Autowired
	public ClaseService(JpaClaseRepository repository) {
		this.repository = repository;
	}

	public List<ClaseEntity> findAll() {
		return repository.findAll();
	}

	public ClaseEntity findLast() {
		return repository.findTopByOrderByClaseIdDesc().orElseThrow(() -> new ClaseException());
	}

	public ClaseEntity findByClaseId(Integer claseId) {
		return repository.findByClaseId(claseId).orElseThrow(() -> new ClaseException(claseId));
	}

	public void delete(Integer claseId) {
		repository.deleteById(claseId);
	}

	public ClaseEntity add(ClaseEntity clase) {
		repository.save(clase);
		return clase;
	}

	public ClaseEntity update(ClaseEntity newClase, Integer claseId) {
		return repository.findByClaseId(claseId).map(clase -> {
			clase = new ClaseEntity(claseId, newClase.getNombre(), newClase.getValorHora());
			repository.save(clase);
			return clase;
		}).orElseThrow(() -> new ClaseException(claseId));
	}
}
