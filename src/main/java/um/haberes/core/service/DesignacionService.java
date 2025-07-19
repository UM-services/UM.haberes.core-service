/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.DesignacionException;
import um.haberes.core.kotlin.model.Designacion;
import um.haberes.core.repository.DesignacionRepository;

/**
 * @author daniel
 *
 */
@Service
public class DesignacionService {

	@Autowired
	private DesignacionRepository repository;

	public List<Designacion> findAllAsignables() {
		return repository.findAllByCategoriaIdNotNull();
	}

	public Designacion findByDesignacionTipoIdAndCargoTipoIdAndAnualAndSemestral(Integer designacionTipoId,
			Integer cargoTipoId, Byte anual, Byte semestral) {
		return repository
				.findByDesignacionTipoIdAndCargoTipoIdAndAnualAndSemestral(designacionTipoId, cargoTipoId, anual,
						semestral)
				.orElseThrow(() -> new DesignacionException(designacionTipoId, cargoTipoId, anual, semestral));
	}

}
