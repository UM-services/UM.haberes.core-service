/**
 * 
 */
package um.haberes.core.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.kotlin.model.view.NovedadAcumulado;
import um.haberes.core.repository.view.NovedadAcumuladoRepository;

/**
 * @author daniel
 *
 */
@Service
public class NovedadAcumuladoService {

	@Autowired
	private NovedadAcumuladoRepository repository;

	public List<NovedadAcumulado> findAllByCodigo(Integer codigoId, Integer anho, Integer mes) {
		return repository.findAllByCodigoIdAndAnhoAndMes(codigoId, anho, mes);
	}

}
