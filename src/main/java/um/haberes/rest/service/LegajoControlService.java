/**
 * 
 */
package um.haberes.rest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.LegajoControlException;
import um.haberes.rest.kotlin.model.LegajoControl;
import um.haberes.rest.repository.ILegajoControlRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class LegajoControlService {

	@Autowired
	private ILegajoControlRepository repository;

	public List<LegajoControl> findAllByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}

	public List<LegajoControl> findAllLiquidadoByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMesAndLiquidadoOrderByLegajoId(anho, mes, (byte) 1);
	}

	public List<LegajoControl> findAllDependenciaByPeriodo(Integer anho, Integer mes, Integer dependenciaId,
			String filtro) {
		return repository
				.findAllByAnhoAndMes(anho, mes,
						Sort.by("persona.apellido").ascending().and(Sort.by("persona.nombre").ascending()))
				.stream().filter(legajoControl -> legajoControl.getPersona().getDependenciaId() == dependenciaId
						&& legajoControl.getPersona().getSalida().equals(filtro))
				.collect(Collectors.toList());
	}

	public LegajoControl findByUnique(Long legajoId, Integer anho, Integer mes) {
		return repository.findByLegajoIdAndAnhoAndMes(legajoId, anho, mes)
				.orElseThrow(() -> new LegajoControlException(legajoId, anho, mes));
	}

	public LegajoControl add(LegajoControl legajoControl) {
		repository.save(legajoControl);
		log.debug(String.format("LegajoControl -> %s", legajoControl));
		return legajoControl;
	}

	public LegajoControl update(LegajoControl newLegajoControl, Long legajoControlId) {
		return repository.findByLegajoControlId(legajoControlId).map(legajoControl -> {
			legajoControl = new LegajoControl(newLegajoControl.getLegajoControlId(), newLegajoControl.getLegajoId(),
					newLegajoControl.getAnho(), newLegajoControl.getMes(), newLegajoControl.getLiquidado(),
					newLegajoControl.getFusionado(), newLegajoControl.getBonoEnviado(), newLegajoControl.getPersona());
			repository.save(legajoControl);
			log.debug(String.format("LegajoControl -> %s", legajoControl));
			return legajoControl;
		}).orElseThrow(() -> new LegajoControlException(legajoControlId));
	}

	public LegajoControl save(LegajoControl control) {
		control = repository.save(control);
		return control;
	}

	public List<LegajoControl> saveAll(List<LegajoControl> legajos) {
		legajos = repository.saveAll(legajos);
		return legajos;
	}

}
