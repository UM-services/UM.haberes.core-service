/**
 * 
 */
package um.haberes.core.service.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.view.AntiguedadPeriodoNotFoundException;
import um.haberes.core.kotlin.model.view.AntiguedadPeriodo;
import um.haberes.core.repository.view.AntiguedadPeriodoRepository;

/**
 * @author daniel
 *
 */
@Service
public class AntiguedadPeriodoService {

	@Autowired
	private AntiguedadPeriodoRepository repository;

	public AntiguedadPeriodo findLastByUnique(Long legajoId, Integer anho, Integer mes) {
		return repository.findTopByLegajoIdAndPeriodoLessThanEqualOrderByPeriodoDesc(legajoId, anho * 100L + mes)
				.orElseThrow(() -> new AntiguedadPeriodoNotFoundException(legajoId, anho, mes));
	}

	public AntiguedadPeriodo findLastByPeriodoLess(Long legajoId, Integer anho, Integer mes) {
		return repository.findTopByLegajoIdAndPeriodoLessThanOrderByPeriodoDesc(legajoId, anho * 100L + mes)
				.orElseThrow(() -> new AntiguedadPeriodoNotFoundException(legajoId, anho, mes));
	}

}
