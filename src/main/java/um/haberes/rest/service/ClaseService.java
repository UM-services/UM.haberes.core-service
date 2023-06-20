/**
 * 
 */
package um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.ClaseException;
import um.haberes.rest.model.Clase;
import um.haberes.rest.repository.IClaseRepository;

/**
 * @author daniel
 *
 */
@Service
public class ClaseService {

	@Autowired
	private IClaseRepository repository;

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
