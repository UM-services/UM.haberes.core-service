/**
 * 
 */
package ar.edu.um.haberes.rest.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.view.TotalMensualNotFoundException;
import ar.edu.um.haberes.rest.model.view.TotalMensual;
import ar.edu.um.haberes.rest.repository.view.ITotalMensualRepository;

/**
 * @author daniel
 *
 */
@Service
public class TotalMensualService {

	@Autowired
	private ITotalMensualRepository repository;

	public List<TotalMensual> findAllByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}

	public TotalMensual findByUnique(Integer anho, Integer mes, Integer codigoId) {
		return repository.findByAnhoAndMesAndCodigoId(anho, mes, codigoId)
				.orElseThrow(() -> new TotalMensualNotFoundException(anho, mes, codigoId));
	}

}
