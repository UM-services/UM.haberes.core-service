/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import um.haberes.core.model.ContactoEntity;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.ContactoException;
import um.haberes.core.repository.JpaContactoRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class ContactoService {
	
	@Autowired
	private JpaContactoRepository repository;

	public List<ContactoEntity> findAll() {
		return repository.findAll();
	}

	public List<ContactoEntity> findAllLegajos(List<Long> legajoIds) {
		return repository.findAllByLegajoIdIn(legajoIds);
	}

	public ContactoEntity findByLegajoId(Long legajoId) {
		return repository.findByLegajoId(legajoId).orElseThrow(() -> new ContactoException(legajoId));
	}

	public ContactoEntity add(ContactoEntity contacto) {
		repository.save(contacto);
		log.debug("ContactoEntity -> " + contacto);
		return contacto;
	}

	public ContactoEntity update(ContactoEntity newContacto, Long legajoId) {
		return repository.findByLegajoId(legajoId).map(contacto -> {
			contacto.setFijo(newContacto.getFijo());
			contacto.setMovil(newContacto.getMovil());
			contacto.setMailPersonal(newContacto.getMailPersonal());
			contacto.setMailInstitucional(newContacto.getMailInstitucional());
			repository.save(contacto);
			log.debug("ContactoEntity -> " + contacto);
			return contacto;
		}).orElseThrow(() -> new ContactoException(legajoId));
	}

	@Transactional
	public List<ContactoEntity> saveAll(List<ContactoEntity> contactos) {
		contactos = repository.saveAll(contactos);
		return contactos;
	}

	public void delete(Long legajoId) {
		repository.deleteByLegajoId(legajoId);
	}

}
