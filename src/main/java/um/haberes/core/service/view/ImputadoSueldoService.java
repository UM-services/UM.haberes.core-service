/**
 * 
 */
package um.haberes.core.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.model.view.ImputadoSueldo;
import um.haberes.core.repository.view.JpaImputadoSueldoRepository;

/**
 * @author daniel
 *
 */
@Service
public class ImputadoSueldoService {
	@Autowired
	private JpaImputadoSueldoRepository repository;
	
	public List<ImputadoSueldo> findAllByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}
}
