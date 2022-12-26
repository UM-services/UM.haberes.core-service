/**
 * 
 */
package ar.edu.um.haberes.rest.service.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.view.TotalSalidaNotFoundException;
import ar.edu.um.haberes.rest.model.view.TotalSalida;
import ar.edu.um.haberes.rest.repository.view.ITotalSalidaRepository;

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
