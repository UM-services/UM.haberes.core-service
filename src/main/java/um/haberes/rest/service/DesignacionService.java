/**
 * 
 */
package um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.DesignacionException;
import um.haberes.rest.model.Designacion;
import um.haberes.rest.repository.IDesignacionRepository;

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
				.orElseThrow(() -> new DesignacionException(designacionTipoId, cargoTipoId, anual, semestral));
	}

}
