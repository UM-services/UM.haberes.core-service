/**
 * 
 */
package um.haberes.core.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.kotlin.model.view.ImputadoAporte;
import um.haberes.core.repository.view.ImputadoAporteRepository;

/**
 * @author daniel
 *
 */
@Service
public class ImputadoAporteService {
		
	@Autowired
	private ImputadoAporteRepository repository;
	
	public List<ImputadoAporte> findAllByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}
}
