/**
 * 
 */
package um.haberes.core.service.view;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.view.ImputadoTotalException;
import um.haberes.core.kotlin.model.view.ImputadoTotal;
import um.haberes.core.repository.view.ImputadoTotalRepository;

/**
 * @author daniel
 *
 */
@Service
@RequiredArgsConstructor
public class ImputadoTotalService {

	private final ImputadoTotalRepository repository;

	public ImputadoTotal findByPeriodo(Integer anho, Integer mes) {
		return repository.findByAnhoAndMes(anho, mes).orElseThrow(() -> new ImputadoTotalException(anho, mes));
	}

}
