/**
 * 
 */
package um.haberes.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import um.haberes.core.kotlin.model.Build;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.BuildException;
import um.haberes.core.repository.BuildRepository;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class BuildService {

	private final BuildRepository repository;

	public BuildService(BuildRepository repository) {
		this.repository = repository;
	}

	public Build findLast() {
		return repository.findTopByOrderByBuildDesc().orElseThrow(() -> new BuildException());
	}

	public Build add(Build build) {
		var newBuild = repository.save(build);
        try {
            log.debug("Build -> " + JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(newBuild));
        } catch (JsonProcessingException e) {
            log.debug("Build -> null");
        }
        return newBuild;
	}
	
	
}
