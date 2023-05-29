/**
 * 
 */
package um.haberes.rest.service;

import um.haberes.rest.kotlin.model.Build;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.BuildNotFoundException;
import um.haberes.rest.repository.IBuildRepository;

/**
 * @author daniel
 *
 */
@Service
public class BuildService {
	@Autowired
	private IBuildRepository repository;

	public Build findLast() {
		return repository.findTopByOrderByBuildDesc().orElseThrow(() -> new BuildNotFoundException());
	}

	public Build add(Build build) {
		repository.save(build);
		return build;
	}
	
	
}
