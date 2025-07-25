/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.LegajoContabilidadException;
import um.haberes.core.kotlin.model.LegajoContabilidad;
import um.haberes.core.repository.LegajoContabilidadRepository;

/**
 * @author daniel
 *
 */
@Service
public class LegajoContabilidadService {
	@Autowired
	private LegajoContabilidadRepository repository;

	public List<LegajoContabilidad> findAllDiferenciaByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMesAndDiferencia(anho, mes, (byte) 1);
	}

	public LegajoContabilidad findByUnique(Long legajoId, Integer anho, Integer mes) {
		return repository.findByLegajoIdAndAnhoAndMes(legajoId, anho, mes)
				.orElseThrow(() -> new LegajoContabilidadException(legajoId, anho, mes));
	}

	public void delete(Long legajocontabilidadID) {
		repository.deleteById(legajocontabilidadID);
	}

	public LegajoContabilidad add(LegajoContabilidad legajocontabilidad) {
		repository.save(legajocontabilidad);
		return legajocontabilidad;
	}

	public LegajoContabilidad update(LegajoContabilidad newLegajoContabilidad, Long legajoContabilidadId) {
		return repository.findById(legajoContabilidadId).map(legajocontabilidad -> {
			legajocontabilidad.setLegajoId(newLegajoContabilidad.getLegajoId());
			legajocontabilidad.setAnho(newLegajoContabilidad.getAnho());
			legajocontabilidad.setMes(newLegajoContabilidad.getMes());
			legajocontabilidad.setDiferencia(newLegajoContabilidad.getDiferencia());
			legajocontabilidad.setRemunerativo(newLegajoContabilidad.getRemunerativo());
			legajocontabilidad.setNoRemunerativo(newLegajoContabilidad.getNoRemunerativo());
			return repository.save(legajocontabilidad);
		}).orElseThrow(() -> new LegajoContabilidadException(legajoContabilidadId));
	}

	public LegajoContabilidad save(LegajoContabilidad legajoContabilidad) {
		return add(legajoContabilidad);
	}

}
