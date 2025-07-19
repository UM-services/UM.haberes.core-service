/**
 * 
 */
package um.haberes.core.service;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.DesignacionTipoException;
import um.haberes.core.kotlin.model.DesignacionTipo;
import um.haberes.core.repository.DesignacionTipoRepository;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class DesignacionTipoService {

	private final DesignacionTipoRepository repository;

	public DesignacionTipoService(DesignacionTipoRepository repository) {
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
