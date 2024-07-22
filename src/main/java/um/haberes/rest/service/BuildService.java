/**
 * 
 */
package um.haberes.rest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import um.haberes.rest.kotlin.model.Build;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.BuildException;
import um.haberes.rest.repository.IBuildRepository;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class BuildService {

	private final IBuildRepository repository;

	public BuildService(IBuildRepository repository) {
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
