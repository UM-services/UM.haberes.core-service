/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.CodigoImputacionException;
import um.haberes.core.kotlin.model.CodigoImputacion;
import um.haberes.core.repository.CodigoImputacionRepository;

/**
 * @author daniel
 *
 */
@Service
public class CodigoImputacionService {

	@Autowired
	private CodigoImputacionRepository repository;

	public List<CodigoImputacion> findAll() {
		return repository.findAll();
	}

	public CodigoImputacion findByCodigoimputacionId(Long codigoImputacionId) {
		return repository.findByCodigoImputacionId(codigoImputacionId)
				.orElseThrow(() -> new CodigoImputacionException(codigoImputacionId));
	}

	public CodigoImputacion findByUnique(Integer dependenciaId, Integer facultadId, Integer geograficaId,
			Integer codigoId) {
		return repository
				.findByDependenciaIdAndFacultadIdAndGeograficaIdAndCodigoId(dependenciaId, facultadId, geograficaId,
						codigoId)
				.orElseThrow(
						() -> new CodigoImputacionException(dependenciaId, facultadId, geograficaId, codigoId));
	}

	public CodigoImputacion add(CodigoImputacion codigoimputacion) {
		repository.save(codigoimputacion);
		return codigoimputacion;
	}

	public CodigoImputacion update(CodigoImputacion newCodigoImputacion, Long codigoImputacionId) {
		return repository.findByCodigoImputacionId(codigoImputacionId).map(codigoimputacion -> {
			codigoimputacion = new CodigoImputacion(codigoImputacionId, newCodigoImputacion.getDependenciaId(),
					newCodigoImputacion.getFacultadId(), newCodigoImputacion.getGeograficaId(),
					newCodigoImputacion.getCodigoId(), newCodigoImputacion.getCuentaSueldosDocente(),
					newCodigoImputacion.getCuentaAportesDocente(), newCodigoImputacion.getCuentaSueldosNoDocente(),
					newCodigoImputacion.getCuentaAportesNoDocente());
			repository.save(codigoimputacion);
			return codigoimputacion;
		}).orElseThrow(() -> new CodigoImputacionException(codigoImputacionId));
	}

}
