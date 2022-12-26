/**
 * 
 */
package ar.edu.um.haberes.rest.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.model.view.ImputadoAporte;
import ar.edu.um.haberes.rest.repository.view.IImputadoAporteRepository;

/**
 * @author daniel
 *
 */
@Service
public class ImputadoAporteService {
		
	@Autowired
	private IImputadoAporteRepository repository;
	
	public List<ImputadoAporte> findAllByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}
}
