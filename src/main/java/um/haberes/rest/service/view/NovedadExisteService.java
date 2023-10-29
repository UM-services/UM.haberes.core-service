/**
 * 
 */
package um.haberes.rest.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.kotlin.model.view.NovedadExiste;
import um.haberes.rest.repository.view.INovedadExisteRepository;

/**
 * @author daniel
 *
 */
@Service
public class NovedadExisteService {

	@Autowired
	private INovedadExisteRepository repository;

	public List<NovedadExiste> findAllByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}

}
