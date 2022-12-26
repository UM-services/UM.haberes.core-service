/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.GeograficaNotFoundException;
import ar.edu.um.haberes.rest.model.Geografica;
import ar.edu.um.haberes.rest.repository.IGeograficaRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dquinteros
 *
 */
@Service
@Slf4j
public class GeograficaService {
	@Autowired
	private IGeograficaRepository repository;

	public List<Geografica> findAll() {
		return repository.findAll(Sort.by("nombre").ascending());
	}

	public List<Geografica> findAllByGeograficaIdIn(List<Integer> ids) {
		return repository.findAllByGeograficaIdIn(ids);
	}

	public Geografica findByGeograficaId(Integer geograficaId) {
		return repository.findByGeograficaId(geograficaId)
				.orElseThrow(() -> new GeograficaNotFoundException(geograficaId));
	}

	public Geografica update(Geografica newgeografica, Integer geograficaId) {
		return repository.findByGeograficaId(geograficaId).map(geografica -> {
			geografica = new Geografica(geograficaId, newgeografica.getNombre(), newgeografica.getReducido(),
					newgeografica.getDesarraigo(), newgeografica.getGeograficaIdReemplazo());
			repository.save(geografica);
			log.debug("Geografica -> " + geografica);
			return geografica;
		}).orElseThrow(() -> new GeograficaNotFoundException(geograficaId));
	}

}
