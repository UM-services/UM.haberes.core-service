/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.ContactoNotFoundException;
import ar.edu.um.haberes.rest.model.Contacto;
import ar.edu.um.haberes.rest.repository.IContactoRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class ContactoService {
	
	@Autowired
	private IContactoRepository repository;

	public List<Contacto> findAll() {
		return repository.findAll();
	}

	public List<Contacto> findAllLegajos(List<Long> legajoIds) {
		return repository.findAllByLegajoIdIn(legajoIds);
	}

	public Contacto findByLegajoId(Long legajoId) {
		return repository.findByLegajoId(legajoId).orElseThrow(() -> new ContactoNotFoundException(legajoId));
	}

	public Contacto add(Contacto contacto) {
		repository.save(contacto);
		log.debug("Contacto -> " + contacto);
		return contacto;
	}

	public Contacto update(Contacto newContacto, Long legajoId) {
		return repository.findByLegajoId(legajoId).map(contacto -> {
			contacto.setFijo(newContacto.getFijo());
			contacto.setMovil(newContacto.getMovil());
			contacto.setMailPersonal(newContacto.getMailPersonal());
			contacto.setMailInstitucional(newContacto.getMailInstitucional());
			repository.save(contacto);
			log.debug("Contacto -> " + contacto);
			return contacto;
		}).orElseThrow(() -> new ContactoNotFoundException(legajoId));
	}

	@Transactional
	public List<Contacto> saveAll(List<Contacto> contactos) {
		contactos = repository.saveAll(contactos);
		return contactos;
	}

	public void delete(Long legajoId) {
		repository.deleteByLegajoId(legajoId);
	}

}
