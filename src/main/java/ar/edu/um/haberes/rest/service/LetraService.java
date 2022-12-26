/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.LetraNotFoundException;
import ar.edu.um.haberes.rest.model.Letra;
import ar.edu.um.haberes.rest.repository.ILetraRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class LetraService {
	@Autowired
	private ILetraRepository repository;

	public Letra findByUnique(Long legajoId, Integer anho, Integer mes) {
		return repository.findByLegajoIdAndAnhoAndMes(legajoId, anho, mes)
				.orElseThrow(() -> new LetraNotFoundException(legajoId, anho, mes));
	}

	public Letra add(Letra letra) {
		repository.save(letra);
		log.debug(letra.toString());
		return letra;
	}

	public Letra update(Letra newLetra, Long letraId) {
		return repository.findByLetraId(letraId).map(letra -> {
			letra.setNeto(newLetra.getNeto());
			letra.setCadena(newLetra.getCadena());
			repository.save(letra);
			log.debug(letra.toString());
			return letra;
		}).orElseThrow(() -> new LetraNotFoundException(letraId));
	}

	@Transactional
	public void deleteByPeriodo(Integer anho, Integer mes) {
		repository.deleteAllByAnhoAndMes(anho, mes);
	}

	public List<Letra> findAllByPeriodo(Integer anho, Integer mes, Integer limit) {
		return repository.findAllByAnhoAndMes(anho, mes, PageRequest.of(0, limit));
	}

	@Transactional
	public List<Letra> saveAll(List<Letra> letras) {
		letras = repository.saveAll(letras);
		return letras;
	}

	@Transactional
	public void deleteByUnique(Long legajoId, Integer anho, Integer mes) {
		repository.deleteByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
	}

}
