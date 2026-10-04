/**
 * 
 */
package um.haberes.core.service.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.view.AntiguedadPeriodoException;
import um.haberes.core.model.view.AntiguedadPeriodo;
import um.haberes.core.repository.view.JpaAntiguedadPeriodoRepository;

/**
 * @author daniel
 *
 */
@Service
public class AntiguedadPeriodoService {

	@Autowired
	private JpaAntiguedadPeriodoRepository repository;

	public AntiguedadPeriodo findLastByUnique(Long legajoId, Integer anho, Integer mes) {
		return repository.findTopByLegajoIdAndPeriodoLessThanEqualOrderByPeriodoDesc(legajoId, anho * 100L + mes)
				.orElseThrow(() -> new AntiguedadPeriodoException(legajoId, anho, mes));
	}

	public AntiguedadPeriodo findLastByPeriodoLess(Long legajoId, Integer anho, Integer mes) {
		return repository.findTopByLegajoIdAndPeriodoLessThanOrderByPeriodoDesc(legajoId, anho * 100L + mes)
				.orElseThrow(() -> new AntiguedadPeriodoException(legajoId, anho, mes));
	}

}
