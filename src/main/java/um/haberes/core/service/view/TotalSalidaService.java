/**
 * 
 */
package um.haberes.core.service.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.view.TotalSalidaNotFoundException;
import um.haberes.core.kotlin.model.view.TotalSalida;
import um.haberes.core.repository.view.ITotalSalidaRepository;

/**
 * @author daniel
 *
 */
@Service
public class TotalSalidaService {

	@Autowired
	private ITotalSalidaRepository repository;

	public TotalSalida findByPeriodo(Integer anho, Integer mes) {
		return repository.findByAnhoAndMes(anho, mes).orElseThrow(() -> new TotalSalidaNotFoundException(anho, mes));
	}

}
