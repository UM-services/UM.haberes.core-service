/**
 * 
 */
package um.haberes.core.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.model.view.ImputadoAporte;
import um.haberes.core.repository.view.JpaImputadoAporteRepository;

/**
 * @author daniel
 *
 */
@Service
public class ImputadoAporteService {
		
	@Autowired
	private JpaImputadoAporteRepository repository;
	
	public List<ImputadoAporte> findAllByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}
}
