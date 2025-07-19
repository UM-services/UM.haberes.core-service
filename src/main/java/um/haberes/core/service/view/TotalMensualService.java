/**
 * 
 */
package um.haberes.core.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.view.TotalMensualNotFoundException;
import um.haberes.core.kotlin.model.view.TotalMensual;
import um.haberes.core.repository.view.TotalMensualRepository;

/**
 * @author daniel
 *
 */
@Service
public class TotalMensualService {

	@Autowired
	private TotalMensualRepository repository;

	public List<TotalMensual> findAllByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}

	public TotalMensual findByUnique(Integer anho, Integer mes, Integer codigoId) {
		return repository.findByAnhoAndMesAndCodigoId(anho, mes, codigoId)
				.orElseThrow(() -> new TotalMensualNotFoundException(anho, mes, codigoId));
	}

}
