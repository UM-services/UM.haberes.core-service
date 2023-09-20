/**
 * 
 */
package um.haberes.rest.service.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.view.AntiguedadPeriodoNotFoundException;
import um.haberes.rest.kotlin.view.AntiguedadPeriodo;
import um.haberes.rest.repository.view.IAntiguedadPeriodoRepository;

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
