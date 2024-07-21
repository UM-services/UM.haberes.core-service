/**
 * 
 */
package um.haberes.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.rest.kotlin.model.Letra;
import um.haberes.rest.service.LetraService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/letra")
public class LetraController {
	
	private final LetraService service;

	public LetraController(LetraService service) {
		this.service = service;
	}
	
	@GetMapping("/periodo/{anho}/{mes}/{limit}")
	public ResponseEntity<List<Letra>> findAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes, @PathVariable Integer limit) {
		if (limit == 0)
			limit = 30000;
		return new ResponseEntity<List<Letra>>(service.findAllByPeriodo(anho, mes, limit), HttpStatus.OK);
	}

	@GetMapping("/unique/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Letra> findByUnique(@PathVariable Long legajoId, @PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<Letra>(service.findByUnique(legajoId, anho, mes), HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<Letra> add(@RequestBody Letra letra) {
		return new ResponseEntity<Letra>(service.add(letra), HttpStatus.OK);
	}
	
	@PutMapping("/{letraId}")
	public ResponseEntity<Letra> update(@RequestBody Letra letra, @PathVariable Long letraId) {
		return new ResponseEntity<Letra>(service.update(letra, letraId), HttpStatus.OK);
	}
	
	@PutMapping("/")
	public ResponseEntity<List<Letra>> saveAll(@RequestBody List<Letra> letras) {
		return new ResponseEntity<List<Letra>>(service.saveAll(letras), HttpStatus.OK);
	}
	
	@DeleteMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<Void> deleteByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		service.deleteByPeriodo(anho, mes);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
