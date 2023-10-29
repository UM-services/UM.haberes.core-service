/**
 * 
 */
package um.haberes.rest.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.kotlin.model.view.NovedadAcumulado;
import um.haberes.rest.repository.view.INovedadAcumuladoRepository;

/**
 * @author daniel
 *
 */
@Service
public class NovedadAcumuladoService {

	@Autowired
	private INovedadAcumuladoRepository repository;

	public List<NovedadAcumulado> findAllByCodigo(Integer codigoId, Integer anho, Integer mes) {
		return repository.findAllByCodigoIdAndAnhoAndMes(codigoId, anho, mes);
	}

}
