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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.rest.kotlin.model.Codigo;
import um.haberes.rest.model.view.CodigoSearch;
import um.haberes.rest.service.CodigoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/codigo")
public class CodigoController {
	@Autowired
	private CodigoService service;

	@GetMapping("/")
	public ResponseEntity<List<Codigo>> findAll() {
		return new ResponseEntity<List<Codigo>>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<List<Codigo>> findAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<Codigo>>(service.findAllByPeriodo(anho, mes), HttpStatus.OK);
	}
	
	@GetMapping("/search/{chain}")
	public ResponseEntity<List<CodigoSearch>> findAllSearch(@PathVariable String chain) {
		return new ResponseEntity<List<CodigoSearch>>(service.findAllSearch(chain), HttpStatus.OK);
	}

	@GetMapping("/{codigoId}")
	public ResponseEntity<Codigo> findByCodigoId(@PathVariable Integer codigoId) {
		return new ResponseEntity<Codigo>(service.findByCodigoId(codigoId), HttpStatus.OK);
	}

	@GetMapping("/last")
	public @ResponseBody ResponseEntity<Codigo> findLast() {
		return new ResponseEntity<Codigo>(service.findLast(), HttpStatus.OK);
	}
	
	/*
	 * delete
	 *
	 */	
	@DeleteMapping("/{codigoId}")
	public ResponseEntity<Void> delete(@PathVariable Integer codigoId) {
		service.delete(codigoId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	/*
	 * add
	 *
	 */
	@PostMapping("/")
	public ResponseEntity<Codigo> add(@RequestBody Codigo codigo) {
		return new ResponseEntity<Codigo>(service.add(codigo), HttpStatus.OK);
	}
	
	/*
	 * update
	 *
	 */
	@PutMapping("/{codigoId}")
	public ResponseEntity<Codigo> update(@RequestBody Codigo codigo, @PathVariable Integer codigoId) {
		return new ResponseEntity<Codigo>(service.update(codigo, codigoId), HttpStatus.OK);
	}
	
	@PutMapping("/")
	public ResponseEntity<List<Codigo>> saveAll(@RequestBody List<Codigo> codigos) {
		return new ResponseEntity<List<Codigo>>(service.saveAll(codigos), HttpStatus.OK);
	}
}
