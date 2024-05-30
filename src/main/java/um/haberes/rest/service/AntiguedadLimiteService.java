/**
 * 
 */
package um.haberes.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.AntiguedadLimiteException;
import um.haberes.rest.kotlin.model.AntiguedadLimite;
import um.haberes.rest.repository.IAntiguedadLimiteRepository;

/**
 * @author daniel
 *
 */
@Service
public class AntiguedadLimiteService {

	private final IAntiguedadLimiteRepository repository;

	@Autowired
	public AntiguedadLimiteService(IAntiguedadLimiteRepository repository) {
		this.repository = repository;
	}

	public AntiguedadLimite findByMeses(Integer meses_docentes) {
		return repository.findByDesdeLessThanEqualAndHastaGreaterThanEqual(meses_docentes, meses_docentes)
				.orElseThrow(() -> new AntiguedadLimiteException(meses_docentes));
	}

}
