/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import um.haberes.core.kotlin.model.Contacto;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.ContactoException;
import um.haberes.core.repository.ContactoRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class ContactoService {
	
	@Autowired
	private ContactoRepository repository;

	public List<Contacto> findAll() {
		return repository.findAll();
	}

	public List<Contacto> findAllLegajos(List<Long> legajoIds) {
		return repository.findAllByLegajoIdIn(legajoIds);
	}

	public Contacto findByLegajoId(Long legajoId) {
		return repository.findByLegajoId(legajoId).orElseThrow(() -> new ContactoException(legajoId));
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
		}).orElseThrow(() -> new ContactoException(legajoId));
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
