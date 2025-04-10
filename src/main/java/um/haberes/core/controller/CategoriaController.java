/**
 * 
 */
package um.haberes.core.controller;

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

import um.haberes.core.exception.CategoriaException;
import um.haberes.core.exception.common.TituloNotFoundException;
import um.haberes.core.kotlin.model.Categoria;
import um.haberes.core.kotlin.model.view.CategoriaSearch;
import um.haberes.core.service.CategoriaService;
import um.haberes.core.util.transfer.FileInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/categoria")
@Slf4j
public class CategoriaController {

	private final CategoriaService service;

	public CategoriaController(CategoriaService service) {
		this.service = service;
	}

	@GetMapping("/")
	public ResponseEntity<List<Categoria>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/nogrado")
	public ResponseEntity<List<Categoria>> findAllNoGrado() {
		return new ResponseEntity<>(service.findAllNoDocentes(), HttpStatus.OK);
	}

	@GetMapping("/search/{chain}")
	public ResponseEntity<List<CategoriaSearch>> findAllSearch(@PathVariable String chain) {
		return new ResponseEntity<>(service.findAllSearch(chain), HttpStatus.OK);
	}

	@GetMapping("/nodocente/{anho}/{mes}")
	public ResponseEntity<List<Categoria>> findAllNoDocente(@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(service.findAllNoDocenteByPeriodo(anho, mes), HttpStatus.OK);
	}

	@GetMapping("/nodocentelegajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<Categoria>> findAllNoDocentesByLegajoId(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(service.findAllNoDocenteByLegajoId(legajoId, anho, mes),
				HttpStatus.OK);
	}

	@GetMapping("/{categoriaId}")
	public ResponseEntity<Categoria> findByCategoriaId(@PathVariable Integer categoriaId) {
		try {
			return new ResponseEntity<>(service.findByCategoriaId(categoriaId), HttpStatus.OK);
		} catch (CategoriaException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/last")
	public @ResponseBody ResponseEntity<Categoria> findLast() {
		try {
			return new ResponseEntity<>(service.findLast(), HttpStatus.OK);
		} catch (CategoriaException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping("/{categoriaId}")
	public ResponseEntity<Void> delete(@PathVariable Integer categoriaId) {
		service.delete(categoriaId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/{anho}/{mes}")
	public ResponseEntity<Categoria> add(@RequestBody Categoria categoria, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<>(service.add(categoria, anho, mes), HttpStatus.OK);
	}

	@PutMapping("/{categoriaId}/{anho}/{mes}")
	public ResponseEntity<Categoria> update(@RequestBody Categoria categoria, @PathVariable Integer categoriaId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		log.debug("Categoria -> {}", categoria);	
		return new ResponseEntity<>(service.update(categoria, categoriaId, anho, mes), HttpStatus.OK);
	}

	@PutMapping("/all/{anho}/{mes}")
	public ResponseEntity<List<Categoria>> saveAll(@RequestBody List<Categoria> categorias, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<>(service.saveAll(categorias, anho, mes), HttpStatus.OK);
	}

	@PostMapping("/upload/{anho}/{mes}")
	public ResponseEntity<Void> upload(@RequestBody FileInfo fileInfo, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		try {
			service.upload(fileInfo, anho, mes);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (TituloNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
