/**
 * 
 */
package um.haberes.core.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.core.kotlin.model.view.LiquidacionPeriodo;
import um.haberes.core.repository.view.ILiquidacionPeriodoRepository;

/**
 * @author daniel
 *
 */
@Service
public class LiquidacionPeriodoService {

	@Autowired
	private ILiquidacionPeriodoRepository repository;

	public List<LiquidacionPeriodo> findAllByLegajoIdForward(Long legajoId, Integer anho, Integer mes) {
		Long periodo = anho * 100L + mes;
		return repository.findAllByLegajoIdAndPeriodoGreaterThanEqual(legajoId, periodo,
				Sort.by("anho").ascending().and(Sort.by("mes").ascending()));
	}

}
