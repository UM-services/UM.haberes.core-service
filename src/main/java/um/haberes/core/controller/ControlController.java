/**
 * 
 */
package um.haberes.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import um.haberes.core.exception.ControlException;
import um.haberes.core.exception.ControlOrigenException;
import um.haberes.core.exception.ControlYaExisteException;
import um.haberes.core.model.ControlEntity;
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
	public ResponseEntity<ControlEntity> findByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		try {
			return new ResponseEntity<>(service.findByPeriodo(anho, mes), HttpStatus.OK);
		} catch (ControlException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<ControlEntity> add(@RequestBody ControlEntity control) {
		return new ResponseEntity<>(service.add(control), HttpStatus.OK);
	}

	@PutMapping("/{controlId}")
	public ResponseEntity<ControlEntity> update(@RequestBody ControlEntity control, @PathVariable Long controlId) {
		return new ResponseEntity<>(service.update(control, controlId), HttpStatus.OK);
	}

	/**
	 * Igual que {@code /periodo/{anho}/{mes}} pero devuelve 200 con cuerpo vacío
	 * cuando el período todavía no tiene datos (el frontend lo interpreta como null).
	 */
	@GetMapping("/periodo/{anho}/{mes}/opcional")
	public ResponseEntity<ControlEntity> findByPeriodoOpcional(@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(service.findByPeriodoOpcional(anho, mes).orElse(null), HttpStatus.OK);
	}

	/**
	 * Crea el período indicado a partir del registro del mes anterior.
	 */
	@PostMapping("/copiar/{anho}/{mes}")
	public ResponseEntity<ControlEntity> copiarPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(service.copiarPeriodo(anho, mes), HttpStatus.OK);
	}

	/**
	 * Los conflictos de la copia viajan como ProblemDetail para que el frontend
	 * muestre el mensaje de negocio (`detail`) y no el error genérico de Spring.
	 */
	@ExceptionHandler(ControlYaExisteException.class)
	public ResponseEntity<ProblemDetail> handleControlYaExiste(ControlYaExisteException e) {
		return new ResponseEntity<>(ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage()),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ControlOrigenException.class)
	public ResponseEntity<ProblemDetail> handleControlOrigen(ControlOrigenException e) {
		return new ResponseEntity<>(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage()),
				HttpStatus.NOT_FOUND);
	}

}
