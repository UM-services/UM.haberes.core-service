/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.FacultadNotFoundException;
import ar.edu.um.haberes.rest.model.Facultad;
import ar.edu.um.haberes.rest.repository.IFacultadRepository;

/**
 * @author daniel
 *
 */
@Service
public class FacultadService {
	@Autowired
	private IFacultadRepository repository;

	public List<Facultad> findAll() {
		return repository.findAll();
	}

	public List<Facultad> findAllFacultades() {
		List<Integer> codigos = List.of(1, 2, 3, 4, 5, 14, 15);
		return repository.findAllByFacultadIdIn(codigos);
	}

	public List<Facultad> findAllByFacultadIdIn(List<Integer> ids) {
		return repository.findAllByFacultadIdIn(ids);
	}

	public Facultad findByFacultadId(Integer facultadId) {
		return repository.findByFacultadId(facultadId).orElseThrow(() -> new FacultadNotFoundException(facultadId));
	}

}
