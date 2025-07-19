/**
 * 
 */
package um.haberes.core.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.kotlin.model.view.ImputadoSueldo;
import um.haberes.core.repository.view.ImputadoSueldoRepository;

/**
 * @author daniel
 *
 */
@Service
public class ImputadoSueldoService {
	@Autowired
	private ImputadoSueldoRepository repository;
	
	public List<ImputadoSueldo> findAllByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}
}
