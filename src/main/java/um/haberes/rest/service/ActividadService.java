/**
 * 
 */
package um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.ActividadException;
import um.haberes.rest.kotlin.model.Actividad;
import um.haberes.rest.kotlin.view.ActividadPeriodo;
import um.haberes.rest.repository.IActividadRepository;
import um.haberes.rest.repository.view.IActividadPeriodoRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class ActividadService {

	@Autowired
	private IActividadRepository repository;

	@Autowired
	private IActividadPeriodoRepository actividadperiodorepository;

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
