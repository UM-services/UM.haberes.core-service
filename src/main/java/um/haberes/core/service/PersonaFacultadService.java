/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.model.PersonaFacultadEntity;
import um.haberes.core.repository.JpaPersonaFacultadRepository;

/**
 * @author daniel
 *
 */
@Service
public class PersonaFacultadService {

	@Autowired
	private JpaPersonaFacultadRepository repository;

	public List<PersonaFacultadEntity> findAllByFacultad(Integer facultadId) {
		return repository.findAllByFacultadId(facultadId);
	}

	public List<PersonaFacultadEntity> findAllByPersona(Long legajoId) {
		return repository.findAllByLegajoId(legajoId);
	}

	public PersonaFacultadEntity add(PersonaFacultadEntity personaFacultad) {
		personaFacultad = repository.save(personaFacultad);
		return personaFacultad;
	}

	@Transactional
	public void deleteByUnique(Long legajoId, Integer facultadId) {
		repository.deleteByLegajoIdAndFacultadId(legajoId, facultadId);
	}

}
