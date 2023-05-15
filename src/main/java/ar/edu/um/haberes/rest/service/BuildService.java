/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import ar.edu.um.haberes.rest.kotlin.model.Build;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.BuildNotFoundException;
import ar.edu.um.haberes.rest.repository.IBuildRepository;

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
