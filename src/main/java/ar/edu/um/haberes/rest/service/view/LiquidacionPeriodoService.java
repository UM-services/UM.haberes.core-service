/**
 * 
 */
package ar.edu.um.haberes.rest.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.model.view.LiquidacionPeriodo;
import ar.edu.um.haberes.rest.repository.view.ILiquidacionPeriodoRepository;

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
