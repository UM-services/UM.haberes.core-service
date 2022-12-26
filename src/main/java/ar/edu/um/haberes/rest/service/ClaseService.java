/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.ClaseNotFoundException;
import ar.edu.um.haberes.rest.model.Clase;
import ar.edu.um.haberes.rest.repository.IClaseRepository;

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
		return repository.findTopByOrderByClaseIdDesc().orElseThrow(() -> new ClaseNotFoundException());
	}

	public Clase findByClaseId(Integer claseId) {
		return repository.findByClaseId(claseId).orElseThrow(() -> new ClaseNotFoundException(claseId));
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
		}).orElseThrow(() -> new ClaseNotFoundException(claseId));
	}
}
