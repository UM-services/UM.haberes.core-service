/**
 * 
 */
package ar.edu.um.haberes.rest.service.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.view.ImputadoTotalNotFoundException;
import ar.edu.um.haberes.rest.model.view.ImputadoTotal;
import ar.edu.um.haberes.rest.repository.view.IImputadoTotalRepository;

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
