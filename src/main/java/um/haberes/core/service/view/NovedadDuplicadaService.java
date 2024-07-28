/**
 * 
 */
package um.haberes.core.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.kotlin.model.view.NovedadDuplicada;
import um.haberes.core.repository.view.INovedadDuplicadaRepository;

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
