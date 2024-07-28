/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.ClaseException;
import um.haberes.core.kotlin.model.Clase;
import um.haberes.core.kotlin.repository.ClaseRepository;

/**
 * @author daniel
 *
 */
@Service
public class ClaseService {

	private final ClaseRepository repository;

	@Autowired
	public ClaseService(ClaseRepository repository) {
		this.repository = repository;
	}

	public List<Clase> findAll() {
		return repository.findAll();
	}

	public Clase findLast() {
		return repository.findTopByOrderByClaseIdDesc().orElseThrow(() -> new ClaseException());
	}

	public Clase findByClaseId(Integer claseId) {
		return repository.findByClaseId(claseId).orElseThrow(() -> new ClaseException(claseId));
	}

	public void delete(Integer claseId) {
		repository.deleteById(claseId);
	}

	public Clase add(Clase clase) {
		repository.save(clase);
		return clase;
	}

	public Clase update(Clase newClase, Integer claseId) {
		return repository.findByClaseId(claseId).map(clase -> {
			clase = new Clase(claseId, newClase.getNombre(), newClase.getValorHora());
			repository.save(clase);
			return clase;
		}).orElseThrow(() -> new ClaseException(claseId));
	}
}
