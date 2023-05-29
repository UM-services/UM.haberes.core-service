/**
 * 
 */
package um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.LegajoContabilidadNotFoundException;
import um.haberes.rest.model.LegajoContabilidad;
import um.haberes.rest.repository.ILegajoContabilidadRepository;

/**
 * @author daniel
 *
 */
@Service
public class LegajoContabilidadService {
	@Autowired
	private ILegajoContabilidadRepository repository;

	public List<LegajoContabilidad> findAllDiferenciaByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMesAndDiferencia(anho, mes, (byte) 1);
	}

	public LegajoContabilidad findByUnique(Long legajoId, Integer anho, Integer mes) {
		return repository.findByLegajoIdAndAnhoAndMes(legajoId, anho, mes)
				.orElseThrow(() -> new LegajoContabilidadNotFoundException(legajoId, anho, mes));
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
		}).orElseThrow(() -> new LegajoContabilidadNotFoundException(legajoContabilidadId));
	}

	public LegajoContabilidad save(LegajoContabilidad legajoContabilidad) {
		return add(legajoContabilidad);
	}

}
