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
import org.springframework.web.server.ResponseStatusException;

import um.haberes.rest.exception.CategoriaException;
import um.haberes.rest.exception.common.TituloNotFoundException;
import um.haberes.rest.kotlin.model.Categoria;
import um.haberes.rest.kotlin.model.view.CategoriaSearch;
import um.haberes.rest.service.CategoriaService;
import um.haberes.rest.util.transfer.FileInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/categoria")
@Slf4j
public class CategoriaController {

	@Autowired
	private CategoriaService service;

	@GetMapping("/")
	public ResponseEntity<List<Categoria>> findAll() {
		return new ResponseEntity<List<Categoria>>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/nogrado")
	public ResponseEntity<List<Categoria>> findAllNoGrado() {
		return new ResponseEntity<List<Categoria>>(service.findAllNoDocentes(), HttpStatus.OK);
	}

	@GetMapping("/search/{chain}")
	public ResponseEntity<List<CategoriaSearch>> findAllSearch(@PathVariable String chain) {
		return new ResponseEntity<List<CategoriaSearch>>(service.findAllSearch(chain), HttpStatus.OK);
	}

	@GetMapping("/nodocente/{anho}/{mes}")
	public ResponseEntity<List<Categoria>> findAllNoDocente(@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<Categoria>>(service.findAllNoDocenteByPeriodo(anho, mes), HttpStatus.OK);
	}

	@GetMapping("/nodocentelegajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<Categoria>> findAllNoDocentesByLegajoId(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<Categoria>>(service.findAllNoDocenteByLegajoId(legajoId, anho, mes),
				HttpStatus.OK);
	}

	@GetMapping("/{categoriaId}")
	public ResponseEntity<Categoria> findByCategoriaId(@PathVariable Integer categoriaId) {
		try {
			return new ResponseEntity<Categoria>(service.findByCategoriaId(categoriaId), HttpStatus.OK);
		} catch (CategoriaException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/last")
	public @ResponseBody ResponseEntity<Categoria> findLast() {
		try {
			return new ResponseEntity<Categoria>(service.findLast(), HttpStatus.OK);
		} catch (CategoriaException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping("/{categoriaId}")
	public ResponseEntity<Void> delete(@PathVariable Integer categoriaId) {
		service.delete(categoriaId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/{anho}/{mes}")
	public ResponseEntity<Categoria> add(@RequestBody Categoria categoria, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<Categoria>(service.add(categoria, anho, mes), HttpStatus.OK);
	}

	@PutMapping("/{categoriaId}/{anho}/{mes}")
	public ResponseEntity<Categoria> update(@RequestBody Categoria categoria, @PathVariable Integer categoriaId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		log.debug("Categoria -> {}", categoria);	
		return new ResponseEntity<Categoria>(service.update(categoria, categoriaId, anho, mes), HttpStatus.OK);
	}

	@PutMapping("/all/{anho}/{mes}")
	public ResponseEntity<List<Categoria>> saveAll(@RequestBody List<Categoria> categorias, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<List<Categoria>>(service.saveAll(categorias, anho, mes), HttpStatus.OK);
	}

	@PostMapping("/upload/{anho}/{mes}")
	public ResponseEntity<Void> upload(@RequestBody FileInfo fileInfo, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		try {
			service.upload(fileInfo, anho, mes);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (TituloNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
