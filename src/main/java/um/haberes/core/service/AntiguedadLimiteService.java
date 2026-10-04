/**
 * 
 */
package um.haberes.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.AntiguedadLimiteException;
import um.haberes.core.model.AntiguedadLimiteEntity;
import um.haberes.core.repository.JpaAntiguedadLimiteRepository;

/**
 * @author daniel
 *
 */
@Service
public class AntiguedadLimiteService {

	private final JpaAntiguedadLimiteRepository repository;

	@Autowired
	public AntiguedadLimiteService(JpaAntiguedadLimiteRepository repository) {
		this.repository = repository;
	}

	public AntiguedadLimiteEntity findByMeses(Integer meses_docentes) {
		return repository.findByDesdeLessThanEqualAndHastaGreaterThanEqual(meses_docentes, meses_docentes)
				.orElseThrow(() -> new AntiguedadLimiteException(meses_docentes));
	}

}
