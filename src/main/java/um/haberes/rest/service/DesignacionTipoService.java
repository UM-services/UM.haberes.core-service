/**
 * 
 */
package um.haberes.rest.service;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.DesignacionTipoException;
import um.haberes.rest.kotlin.model.DesignacionTipo;
import um.haberes.rest.repository.IDesignacionTipoRepository;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class DesignacionTipoService {

	private final IDesignacionTipoRepository repository;

	public DesignacionTipoService(IDesignacionTipoRepository repository) {
		this.repository = repository;
	}

	public List<DesignacionTipo> findAll() {
		return repository.findAll();
	}

	public DesignacionTipo findByHorasSemanales(BigDecimal horasSemanales) {
		return repository.findFirstByHorasSemanalesGreaterThanEqual(horasSemanales)
				.orElseThrow(() -> new DesignacionTipoException(horasSemanales));
	}

	public DesignacionTipo findByDesignacionTipoId(Integer designacionTipoId) {
		var designacionTipo = repository.findByDesignacionTipoId(designacionTipoId)
				.orElseThrow(() -> new DesignacionTipoException(designacionTipoId));
        try {
            log.debug("DesignacionTipo: {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(designacionTipo));
        } catch (JsonProcessingException e) {
            log.debug("DesignacionTipo: null");
        }
        return designacionTipo;
	}

}
