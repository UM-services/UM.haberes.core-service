/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.FacultadException;
import um.haberes.core.kotlin.model.Facultad;
import um.haberes.core.repository.IFacultadRepository;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class FacultadService {

	private final IFacultadRepository repository;

	public FacultadService(IFacultadRepository repository) {
		this.repository = repository;
	}

	public List<Facultad> findAll() {
		return repository.findAll();
	}

	public List<Facultad> findAllFacultades() {
		List<Integer> codigos = List.of(1, 2, 3, 4, 5, 14, 15);
		return repository.findAllByFacultadIdIn(codigos);
	}

	public List<Facultad> findAllByFacultadIdIn(List<Integer> ids) {
		return repository.findAllByFacultadIdIn(ids);
	}

	public Facultad findByFacultadId(Integer facultadId) {
		var facultad = repository.findByFacultadId(facultadId).orElseThrow(() -> new FacultadException(facultadId));
        try {
            log.debug("Facultad: {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(facultad));
        } catch (JsonProcessingException e) {
            log.debug("Facultad: error {}", e.getMessage());
        }
        return facultad;
	}

}
