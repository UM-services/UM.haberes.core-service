/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import um.haberes.core.exception.LiquidacionAdicionalException;
import um.haberes.core.model.LiquidacionAdicionalEntity;
import um.haberes.core.repository.JpaLiquidacionAdicionalRepository;

/**
 * @author daniel
 *
 */
@Service
public class LiquidacionAdicionalService {

	private final JpaLiquidacionAdicionalRepository repository;

	public LiquidacionAdicionalService(JpaLiquidacionAdicionalRepository repository) {
		this.repository = repository;
	}

	public List<LiquidacionAdicionalEntity> findAllByLegajo(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
	}

	public LiquidacionAdicionalEntity findByDependencia(Long legajoId, Integer anho, Integer mes, Integer dependenciaId) {
		return repository.findByLegajoIdAndAnhoAndMesAndDependenciaId(legajoId, anho, mes, dependenciaId).orElseThrow(() -> new LiquidacionAdicionalException(legajoId, anho, mes, dependenciaId));
	}

	@Transactional
	public void deleteAllByLegajo(Long legajoId, Integer anho, Integer mes) {
		repository.deleteAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
	}

	public LiquidacionAdicionalEntity add(LiquidacionAdicionalEntity liquidacionAdicional) {
		return repository.save(liquidacionAdicional);
	}

}
