/**
 * 
 */
package um.haberes.core.controller;

import um.haberes.core.kotlin.model.Build;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.core.service.BuildService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/build")
public class BuildController {
	
	private final BuildService service;

	public BuildController(BuildService service) {
		this.service = service;
	}
	
	@GetMapping("/last")
	public ResponseEntity<Build> findLast() {
		return new ResponseEntity<>(service.findLast(), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<Build> add() {
		return new ResponseEntity<>(service.add(new Build()), HttpStatus.OK);
	}
	
}
