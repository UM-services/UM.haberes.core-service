/**
 * 
 */
package ar.edu.um.haberes.rest.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.model.view.NovedadDuplicada;
import ar.edu.um.haberes.rest.repository.view.INovedadDuplicadaRepository;

/**
 * @author daniel
 *
 */
@Service
public class NovedadDuplicadaService {

	@Autowired
	private INovedadDuplicadaRepository repository;

	public List<NovedadDuplicada> findAllByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}

}
