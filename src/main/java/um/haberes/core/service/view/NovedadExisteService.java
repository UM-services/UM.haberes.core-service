/**
 * 
 */
package um.haberes.core.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.kotlin.model.view.NovedadExiste;
import um.haberes.core.repository.view.NovedadExisteRepository;

/**
 * @author daniel
 *
 */
@Service
public class NovedadExisteService {

	@Autowired
	private NovedadExisteRepository repository;

	public List<NovedadExiste> findAllByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}

}
