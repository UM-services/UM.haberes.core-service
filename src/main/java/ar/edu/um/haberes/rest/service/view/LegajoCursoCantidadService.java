/**
 * 
 */
package ar.edu.um.haberes.rest.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.model.view.LegajoCursoCantidad;
import ar.edu.um.haberes.rest.repository.view.ILegajoCursoCantidadRepository;

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
