/**
 * 
 */
package um.haberes.core.service.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.view.TotalSalidaException;
import um.haberes.core.model.view.TotalSalida;
import um.haberes.core.repository.view.JpaTotalSalidaRepository;

/**
 * @author daniel
 *
 */
@Service
public class TotalSalidaService {

	@Autowired
	private JpaTotalSalidaRepository repository;

	public TotalSalida findByPeriodo(Integer anho, Integer mes) {
		return repository.findByAnhoAndMes(anho, mes).orElseThrow(() -> new TotalSalidaException(anho, mes));
	}

}
