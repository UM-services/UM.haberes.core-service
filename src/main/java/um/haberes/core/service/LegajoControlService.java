/**
 * 
 */
package um.haberes.core.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.LegajoControlException;
import um.haberes.core.model.LegajoControlEntity;
import um.haberes.core.repository.JpaLegajoControlRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class LegajoControlService {

	@Autowired
	private JpaLegajoControlRepository repository;

	public List<LegajoControlEntity> findAllByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}

	public List<LegajoControlEntity> findAllLiquidadoByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMesAndLiquidadoOrderByLegajoId(anho, mes, (byte) 1);
	}

	public List<LegajoControlEntity> findAllDependenciaByPeriodo(Integer anho, Integer mes, Integer dependenciaId,
			String filtro) {
		return repository
				.findAllByAnhoAndMes(anho, mes,
						Sort.by("persona.apellido").ascending().and(Sort.by("persona.nombre").ascending()))
				.stream().filter(legajoControl -> legajoControl.getPersona().getDependenciaId() == dependenciaId
						&& legajoControl.getPersona().getSalida().equals(filtro))
				.collect(Collectors.toList());
	}

	public LegajoControlEntity findByUnique(Long legajoId, Integer anho, Integer mes) {
		return repository.findByLegajoIdAndAnhoAndMes(legajoId, anho, mes)
				.orElseThrow(() -> new LegajoControlException(legajoId, anho, mes));
	}

	public LegajoControlEntity add(LegajoControlEntity legajoControl) {
		repository.save(legajoControl);
		log.debug(String.format("LegajoControlEntity -> %s", legajoControl));
		return legajoControl;
	}

	public LegajoControlEntity update(LegajoControlEntity newLegajoControl, Long legajoControlId) {
		return repository.findByLegajoControlId(legajoControlId).map(legajoControl -> {
			legajoControl = new LegajoControlEntity(newLegajoControl.getLegajoControlId(), newLegajoControl.getLegajoId(),
					newLegajoControl.getAnho(), newLegajoControl.getMes(), newLegajoControl.getLiquidado(),
					newLegajoControl.getFusionado(), newLegajoControl.getBonoEnviado(), newLegajoControl.getPersona());
			repository.save(legajoControl);
			log.debug(String.format("LegajoControlEntity -> %s", legajoControl));
			return legajoControl;
		}).orElseThrow(() -> new LegajoControlException(legajoControlId));
	}

	public LegajoControlEntity save(LegajoControlEntity control) {
		control = repository.save(control);
		return control;
	}

	public List<LegajoControlEntity> saveAll(List<LegajoControlEntity> legajos) {
		legajos = repository.saveAll(legajos);
		return legajos;
	}

}
