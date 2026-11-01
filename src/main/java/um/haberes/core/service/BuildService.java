/**
 * 
 */
package um.haberes.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import um.haberes.core.model.BuildEntity;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.BuildException;
import um.haberes.core.repository.JpaBuildRepository;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class BuildService {

	private final JpaBuildRepository repository;

	public BuildService(JpaBuildRepository repository) {
		this.repository = repository;
	}

	public BuildEntity findLast() {
		return repository.findTopByOrderByBuildDesc().orElseThrow(() -> new BuildException());
	}

	public BuildEntity add(BuildEntity build) {
		var newBuild = repository.save(build);
        try {
            log.debug("BuildEntity -> " + JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(newBuild));
        } catch (JsonProcessingException e) {
            log.debug("BuildEntity -> null");
        }
        return newBuild;
	}
	
	
}
