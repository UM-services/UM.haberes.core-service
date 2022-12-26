/**
 * 
 */
package ar.edu.um.haberes.rest.controller;

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
import org.springframework.web.server.ResponseStatusException;

import ar.edu.um.haberes.rest.exception.ItemNotFoundException;
import ar.edu.um.haberes.rest.model.Item;
import ar.edu.um.haberes.rest.service.ItemService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService service;

	@GetMapping("/codigo/{codigoId}/{anho}/{mes}")
	public ResponseEntity<List<Item>> findAllByCodigoId(@PathVariable Integer codigoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<List<Item>>(service.findAllByCodigo(codigoId, anho, mes), HttpStatus.OK);
	}

	@GetMapping("/legajo/{legajoId}/{anho}/{mes}/{codigoIdDesde}/{codigoIdHasta}")
	public ResponseEntity<List<Item>> findAllByLegajoId(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Integer codigoIdDesde, @PathVariable Integer codigoIdHasta) {
		return new ResponseEntity<List<Item>>(
				service.findAllByLegajo(legajoId, anho, mes, codigoIdDesde, codigoIdHasta), HttpStatus.OK);
	}

	@GetMapping("/periodo/{anho}/{mes}/{limit}")
	public ResponseEntity<List<Item>> findAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes,
			@PathVariable Integer limit) {
		if (limit == 0)
			limit = 99999;
		return new ResponseEntity<List<Item>>(service.findAllByPeriodo(anho, mes, limit), HttpStatus.OK);
	}

	@GetMapping("/periodolegajo/{anho}/{mes}/{legajoId}/{limit}")
	public ResponseEntity<List<Item>> findAllByPeriodoAndLegajo(@PathVariable Integer anho, @PathVariable Integer mes,
			@PathVariable Long legajoId, @PathVariable Integer limit) {
		if (limit == 0)
			limit = 99999;
		return new ResponseEntity<List<Item>>(service.findAllByPeriodoAndLegajo(anho, mes, legajoId, limit),
				HttpStatus.OK);
	}

	@GetMapping("/unique/{legajoId}/{anho}/{mes}/{codigoId}")
	public ResponseEntity<Item> findByUnique(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Integer codigoId) {
		try {
			return new ResponseEntity<Item>(service.findByUnique(legajoId, anho, mes, codigoId), HttpStatus.OK);
		} catch (ItemNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<Item> add(@RequestBody Item item) {
		return new ResponseEntity<Item>(service.add(item), HttpStatus.OK);
	}

	@PutMapping("/{itemId}")
	public ResponseEntity<Item> update(@RequestBody Item item, @PathVariable Long itemId) {
		return new ResponseEntity<Item>(service.update(item, itemId), HttpStatus.OK);
	}

	@PutMapping("/")
	public ResponseEntity<List<Item>> saveAll(@RequestBody List<Item> items) {
		return new ResponseEntity<List<Item>>(service.saveAll(items), HttpStatus.OK);
	}

	@DeleteMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<Void> deleteByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		service.deleteByPeriodo(anho, mes);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/onlyETEC/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Boolean> onlyETEC(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<Boolean>(service.onlyETEC(legajoId, anho, mes), HttpStatus.OK);
	}

}
