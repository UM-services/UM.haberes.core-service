/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.CodigoImputacionNotFoundException;
import ar.edu.um.haberes.rest.model.CodigoImputacion;
import ar.edu.um.haberes.rest.repository.ICodigoImputacionRepository;

/**
 * @author daniel
 *
 */
@Service
public class CodigoImputacionService {

	@Autowired
	private ICodigoImputacionRepository repository;

	public List<CodigoImputacion> findAll() {
		return repository.findAll();
	}

	public CodigoImputacion findByCodigoimputacionId(Long codigoImputacionId) {
		return repository.findByCodigoImputacionId(codigoImputacionId)
				.orElseThrow(() -> new CodigoImputacionNotFoundException(codigoImputacionId));
	}

	public CodigoImputacion findByUnique(Integer dependenciaId, Integer facultadId, Integer geograficaId,
			Integer codigoId) {
		return repository
				.findByDependenciaIdAndFacultadIdAndGeograficaIdAndCodigoId(dependenciaId, facultadId, geograficaId,
						codigoId)
				.orElseThrow(
						() -> new CodigoImputacionNotFoundException(dependenciaId, facultadId, geograficaId, codigoId));
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
		}).orElseThrow(() -> new CodigoImputacionNotFoundException(codigoImputacionId));
	}

}
