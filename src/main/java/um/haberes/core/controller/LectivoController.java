/**
 * 
 */
package um.haberes.core.controller;

import java.util.List;

import um.haberes.core.model.LectivoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.core.service.LectivoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/lectivo")
public class LectivoController {

	@Autowired
	private LectivoService service;

	@GetMapping("/")
	public ResponseEntity<List<LectivoEntity>> findAll() {
		return new ResponseEntity<List<LectivoEntity>>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/reverse")
	public ResponseEntity<List<LectivoEntity>> findAllReverse() {
		return new ResponseEntity<List<LectivoEntity>>(service.findAllReverse(), HttpStatus.OK);
	}

	@GetMapping("/{lectivoId}")
	public ResponseEntity<LectivoEntity> findByLectivoId(@PathVariable Integer lectivoId) {
		return new ResponseEntity<LectivoEntity>(service.findByLectivoId(lectivoId), HttpStatus.OK);
	}

	@PutMapping("/saveall")
	public ResponseEntity<List<LectivoEntity>> saveAll(@RequestBody List<LectivoEntity> lectivos) {
		return new ResponseEntity<List<LectivoEntity>>(service.saveAll(lectivos), HttpStatus.OK);
	}

}
