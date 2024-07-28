/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import um.haberes.core.exception.LiquidacionAdicionalException;
import um.haberes.core.kotlin.model.LiquidacionAdicional;
import um.haberes.core.repository.ILiquidacionAdicionalRepository;

/**
 * @author daniel
 *
 */
@Service
public class LiquidacionAdicionalService {

	private final ILiquidacionAdicionalRepository repository;

	public LiquidacionAdicionalService(ILiquidacionAdicionalRepository repository) {
		this.repository = repository;
	}

	public List<LiquidacionAdicional> findAllByLegajo(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
	}

	public LiquidacionAdicional findByDependencia(Long legajoId, Integer anho, Integer mes, Integer dependenciaId) {
		return repository.findByLegajoIdAndAnhoAndMesAndDependenciaId(legajoId, anho, mes, dependenciaId).orElseThrow(() -> new LiquidacionAdicionalException(legajoId, anho, mes, dependenciaId));
	}

	@Transactional
	public void deleteAllByLegajo(Long legajoId, Integer anho, Integer mes) {
		repository.deleteAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
	}

	public LiquidacionAdicional add(LiquidacionAdicional liquidacionAdicional) {
		return repository.save(liquidacionAdicional);
	}

}
