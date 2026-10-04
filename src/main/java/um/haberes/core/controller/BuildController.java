/**
 * 
 */
package um.haberes.core.controller;

import um.haberes.core.model.BuildEntity;
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
	public ResponseEntity<BuildEntity> findLast() {
		return new ResponseEntity<>(service.findLast(), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<BuildEntity> add() {
		return new ResponseEntity<>(service.add(new BuildEntity()), HttpStatus.OK);
	}
	
}
