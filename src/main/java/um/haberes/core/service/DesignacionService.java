/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.DesignacionException;
import um.haberes.core.model.DesignacionEntity;
import um.haberes.core.repository.JpaDesignacionRepository;

/**
 * @author daniel
 *
 */
@Service
public class DesignacionService {

	@Autowired
	private JpaDesignacionRepository repository;

	public List<DesignacionEntity> findAllAsignables() {
		return repository.findAllByCategoriaIdNotNull();
	}

	public DesignacionEntity findByDesignacionTipoIdAndCargoTipoIdAndAnualAndSemestral(Integer designacionTipoId,
			Integer cargoTipoId, Byte anual, Byte semestral) {
		return repository
				.findByDesignacionTipoIdAndCargoTipoIdAndAnualAndSemestral(designacionTipoId, cargoTipoId, anual,
						semestral)
				.orElseThrow(() -> new DesignacionException(designacionTipoId, cargoTipoId, anual, semestral));
	}

}
