/**
 * 
 */
package ar.edu.um.haberes.rest.service.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.view.AntiguedadPeriodoNotFoundException;
import ar.edu.um.haberes.rest.model.view.AntiguedadPeriodo;
import ar.edu.um.haberes.rest.repository.view.IAntiguedadPeriodoRepository;

/**
 * @author daniel
 *
 */
@Service
public class AntiguedadPeriodoService {

	@Autowired
	private IAntiguedadPeriodoRepository repository;

	public AntiguedadPeriodo findLastByUnique(Long legajoId, Integer anho, Integer mes) {
		return repository.findTopByLegajoIdAndPeriodoLessThanEqualOrderByPeriodoDesc(legajoId, anho * 100L + mes)
				.orElseThrow(() -> new AntiguedadPeriodoNotFoundException(legajoId, anho, mes));
	}

	public AntiguedadPeriodo findLastByPeriodoLess(Long legajoId, Integer anho, Integer mes) {
		return repository.findTopByLegajoIdAndPeriodoLessThanOrderByPeriodoDesc(legajoId, anho * 100L + mes)
				.orElseThrow(() -> new AntiguedadPeriodoNotFoundException(legajoId, anho, mes));
	}

}
