/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.ActividadException;
import um.haberes.core.kotlin.model.Actividad;
import um.haberes.core.kotlin.model.view.ActividadPeriodo;
import um.haberes.core.repository.ActividadRepository;
import um.haberes.core.repository.view.ActividadPeriodoRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class ActividadService {

	@Autowired
	private ActividadRepository repository;

	@Autowired
	private ActividadPeriodoRepository actividadperiodorepository;

	public List<Actividad> findAllByLegajoId(Long legajoId) {
		return repository.findAllByLegajoId(legajoId, Sort.by("anho", "mes").ascending());
	}

	public List<ActividadPeriodo> findAllByPeriodo(Long legajoId, Integer anho, Integer mes) {
		return actividadperiodorepository.findAllByLegajoIdAndPeriodoGreaterThanEqual(legajoId, (long) anho * 100 + mes,
				Sort.by("anho", "mes").ascending());
	}

	public Actividad findByUnique(Long legajoId, Integer anho, Integer mes) {
		return repository.findByLegajoIdAndAnhoAndMes(legajoId, anho, mes)
				.orElseThrow(() -> new ActividadException(legajoId, anho, mes));
	}

	public Actividad add(Actividad actividad) {
		actividad = repository.save(actividad);
		log.debug("Actividad -> {}", actividad);
		return actividad;
	}

	public Actividad update(Actividad newActividad, Long actividadId) {
		return repository.findById(actividadId).map(actividad -> {
			actividad.setDocente(newActividad.getDocente());
			actividad.setOtras(newActividad.getOtras());
			actividad.setClases(newActividad.getClases());
			actividad.setDependenciaId(newActividad.getDependenciaId());
			actividad = repository.save(actividad);
			log.debug("Actividad -> {}", actividad);
			return actividad;
		}).orElseThrow(() -> new ActividadException(actividadId));
	}
}
