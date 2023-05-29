/**
 * 
 */
package um.haberes.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.AntiguedadLimiteNotFoundException;
import um.haberes.rest.model.AntiguedadLimite;
import um.haberes.rest.repository.IAntiguedadLimiteRepository;

/**
 * @author daniel
 *
 */
@Service
public class AntiguedadLimiteService {

	@Autowired
	private IAntiguedadLimiteRepository repository;

	public AntiguedadLimite findByMeses(Integer meses_docentes) {
		return repository.findByDesdeLessThanEqualAndHastaGreaterThanEqual(meses_docentes, meses_docentes)
				.orElseThrow(() -> new AntiguedadLimiteNotFoundException(meses_docentes));
	}

}
