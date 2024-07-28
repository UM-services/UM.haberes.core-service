/**
 * 
 */
package um.haberes.core.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.kotlin.model.view.LegajoCursoCantidad;
import um.haberes.core.repository.view.ILegajoCursoCantidadRepository;

/**
 * @author daniel
 *
 */
@Service
public class LegajoCursoCantidadService {

	@Autowired
	private ILegajoCursoCantidadRepository repository;

	public List<LegajoCursoCantidad> findAllByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}

}
