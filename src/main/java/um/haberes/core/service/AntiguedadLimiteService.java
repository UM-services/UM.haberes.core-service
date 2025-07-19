/**
 * 
 */
package um.haberes.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.AntiguedadLimiteException;
import um.haberes.core.kotlin.model.AntiguedadLimite;
import um.haberes.core.repository.AntiguedadLimiteRepository;

/**
 * @author daniel
 *
 */
@Service
public class AntiguedadLimiteService {

	private final AntiguedadLimiteRepository repository;

	@Autowired
	public AntiguedadLimiteService(AntiguedadLimiteRepository repository) {
		this.repository = repository;
	}

	public AntiguedadLimite findByMeses(Integer meses_docentes) {
		return repository.findByDesdeLessThanEqualAndHastaGreaterThanEqual(meses_docentes, meses_docentes)
				.orElseThrow(() -> new AntiguedadLimiteException(meses_docentes));
	}

}
