/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.AcreditacionException;
import um.haberes.core.kotlin.model.Acreditacion;
import um.haberes.core.repository.AcreditacionRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class AcreditacionService {
	@Autowired
	private AcreditacionRepository repository;

	public List<Acreditacion> findAll() {
		return repository.findAll();
	}

	public Acreditacion findByAcreditacionId(Long acreditacionId) {
		return repository.findByAcreditacionId(acreditacionId).orElseThrow(() -> new AcreditacionException(acreditacionId));
	}
	
	public Acreditacion findByPeriodo(Integer anho, Integer mes) {
		return repository.findByAnhoAndMes(anho, mes).orElseThrow(() -> new AcreditacionException(anho, mes));
	}

	public void delete(Long acreditacionId) {
		repository.deleteById(acreditacionId);
	}

	public Acreditacion add(Acreditacion acreditacion) {
		repository.save(acreditacion);
		log.debug(String.format("Acreditacion -> ", acreditacion));
		return acreditacion;
	}

	public Acreditacion update(Acreditacion newAcreditacion, Long acreditacionId) {
		return repository.findByAcreditacionId(acreditacionId).map(acreditacion -> {
			acreditacion.setAnho(newAcreditacion.getAnho());
			acreditacion.setMes(newAcreditacion.getMes());
			acreditacion.setAcreditado(newAcreditacion.getAcreditado());
			acreditacion.setLimiteNovedades(newAcreditacion.getLimiteNovedades());
			acreditacion.setFechaContable(newAcreditacion.getFechaContable());
			acreditacion.setOrdenContable(newAcreditacion.getOrdenContable());
			acreditacion.setSueldosOriginal(newAcreditacion.getSueldosOriginal());
			acreditacion.setSueldosAjustados(newAcreditacion.getSueldosAjustados());
			acreditacion.setContribucionesPatronales(newAcreditacion.getContribucionesPatronales());
			repository.save(acreditacion);
			log.debug(String.format("Acreditacion -> ", acreditacion));
			return acreditacion;
		}).orElseThrow(() -> new AcreditacionException(acreditacionId));
	}
}
