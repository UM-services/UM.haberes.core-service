/**
 * 
 */
package ar.edu.um.haberes.rest.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.model.view.ImputadoSueldo;
import ar.edu.um.haberes.rest.repository.view.IImputadoSueldoRepository;

/**
 * @author daniel
 *
 */
@Service
public class ImputadoSueldoService {
	@Autowired
	private IImputadoSueldoRepository repository;
	
	public List<ImputadoSueldo> findAllByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}
}
