/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.model.PersonaFacultad;
import ar.edu.um.haberes.rest.repository.IPersonaFacultadRepository;

/**
 * @author daniel
 *
 */
@Service
public class PersonaFacultadService {

	@Autowired
	private IPersonaFacultadRepository repository;

	public List<PersonaFacultad> findAllByFacultad(Integer facultadId) {
		return repository.findAllByFacultadId(facultadId);
	}

	public List<PersonaFacultad> findAllByPersona(Long legajoId) {
		return repository.findAllByLegajoId(legajoId);
	}

	public PersonaFacultad add(PersonaFacultad personaFacultad) {
		personaFacultad = repository.save(personaFacultad);
		return personaFacultad;
	}

	@Transactional
	public void deleteByUnique(Long legajoId, Integer facultadId) {
		repository.deleteByLegajoIdAndFacultadId(legajoId, facultadId);
	}

}
