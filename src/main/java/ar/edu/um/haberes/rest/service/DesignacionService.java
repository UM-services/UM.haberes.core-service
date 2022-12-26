/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.DesignacionNotFoundException;
import ar.edu.um.haberes.rest.model.Designacion;
import ar.edu.um.haberes.rest.repository.IDesignacionRepository;

/**
 * @author daniel
 *
 */
@Service
public class DesignacionService {

	@Autowired
	private IDesignacionRepository repository;

	public List<Designacion> findAllAsignables() {
		return repository.findAllByCategoriaIdNotNull();
	}

	public Designacion findByDesignacionTipoIdAndCargoTipoIdAndAnualAndSemestral(Integer designacionTipoId,
			Integer cargoTipoId, Byte anual, Byte semestral) {
		return repository
				.findByDesignacionTipoIdAndCargoTipoIdAndAnualAndSemestral(designacionTipoId, cargoTipoId, anual,
						semestral)
				.orElseThrow(() -> new DesignacionNotFoundException(designacionTipoId, cargoTipoId, anual, semestral));
	}

}
