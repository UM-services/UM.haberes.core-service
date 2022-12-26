/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.DesignacionTipoNotFoundException;
import ar.edu.um.haberes.rest.model.DesignacionTipo;
import ar.edu.um.haberes.rest.repository.IDesignacionTipoRepository;

/**
 * @author daniel
 *
 */
@Service
public class DesignacionTipoService {

	@Autowired
	private IDesignacionTipoRepository repository;

	public List<DesignacionTipo> findAll() {
		return repository.findAll();
	}

	public DesignacionTipo findByHorasSemanales(BigDecimal horasSemanales) {
		return repository.findFirstByHorasSemanalesGreaterThanEqual(horasSemanales)
				.orElseThrow(() -> new DesignacionTipoNotFoundException(horasSemanales));
	}

	public DesignacionTipo findByDesignacionTipoId(Integer designacionTipoId) {
		return repository.findByDesignacionTipoId(designacionTipoId)
				.orElseThrow(() -> new DesignacionTipoNotFoundException(designacionTipoId));
	}

}
