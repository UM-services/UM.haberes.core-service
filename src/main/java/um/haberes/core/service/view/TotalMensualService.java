/**
 * 
 */
package um.haberes.core.service.view;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.view.TotalMensualException;
import um.haberes.core.model.view.TotalMensual;
import um.haberes.core.repository.view.JpaTotalMensualRepository;

/**
 * @author daniel
 *
 */
@Service
@RequiredArgsConstructor
public class TotalMensualService {

	private final JpaTotalMensualRepository repository;

	public List<TotalMensual> findAllByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}

	public TotalMensual findByUnique(Integer anho, Integer mes, Integer codigoId) {
		return repository.findByAnhoAndMesAndCodigoId(anho, mes, codigoId)
				.orElseThrow(() -> new TotalMensualException(anho, mes, codigoId));
	}

}
