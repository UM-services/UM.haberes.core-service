/**
 * 
 */
package um.haberes.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import um.haberes.core.exception.ControlException;
import um.haberes.core.kotlin.model.Control;
import um.haberes.core.service.ControlService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/control")
public class ControlController {

	private final ControlService service;

	@Autowired
	public ControlController(ControlService service) {
		this.service = service;
	}

	@GetMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<Control> findByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		try {
			return new ResponseEntity<>(service.findByPeriodo(anho, mes), HttpStatus.OK);
		} catch (ControlException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<Control> add(@RequestBody Control control) {
		return new ResponseEntity<>(service.add(control), HttpStatus.OK);
	}

	@PutMapping("/{controlId}")
	public ResponseEntity<Control> update(@RequestBody Control control, @PathVariable Long controlId) {
		return new ResponseEntity<>(service.update(control, controlId), HttpStatus.OK);
	}

}
