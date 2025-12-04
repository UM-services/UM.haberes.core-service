/**
 * 
 */
package um.haberes.core.service.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.view.TotalSalidaException;
import um.haberes.core.kotlin.model.view.TotalSalida;
import um.haberes.core.repository.view.TotalSalidaRepository;

/**
 * @author daniel
 *
 */
@Service
public class TotalSalidaService {

	@Autowired
	private TotalSalidaRepository repository;

	public TotalSalida findByPeriodo(Integer anho, Integer mes) {
		return repository.findByAnhoAndMes(anho, mes).orElseThrow(() -> new TotalSalidaException(anho, mes));
	}

}
