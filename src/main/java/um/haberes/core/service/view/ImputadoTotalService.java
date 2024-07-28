/**
 * 
 */
package um.haberes.core.service.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.view.ImputadoTotalNotFoundException;
import um.haberes.core.kotlin.model.view.ImputadoTotal;
import um.haberes.core.repository.view.IImputadoTotalRepository;

/**
 * @author daniel
 *
 */
@Service
public class ImputadoTotalService {
	@Autowired
	private IImputadoTotalRepository repository;

	public ImputadoTotal findByPeriodo(Integer anho, Integer mes) {
		return repository.findByAnhoAndMes(anho, mes).orElseThrow(() -> new ImputadoTotalNotFoundException(anho, mes));
	}

}
