/**
 * 
 */
package um.haberes.core.controller.facade;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.core.service.facade.DesignacionToolService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/designaciontool")
public class DesignacionToolController {

	private final DesignacionToolService service;

	@Autowired
	public DesignacionToolController(DesignacionToolService service) {
		this.service = service;
	}

	@GetMapping("/convertirbylegajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Void> convertirGradoByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		boolean aplicaExcepcion = (long) anho * 100L + mes < 201904;
        service.convertirGradoByLegajo(legajoId, anho, mes, aplicaExcepcion);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/redesignarbylegajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Void> redesignarGradoByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		boolean aplicaExcepcion = (long) anho * 100L + mes < 201904;
        service.redesignarGradoByLegajo(legajoId, anho, mes, aplicaExcepcion);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/fusionarbylegajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Void> fusionarGradoByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		boolean aplicaExcepcion = (long) anho * 100L + mes < 201904;
        service.fusionarGradoByLegajo(legajoId, anho, mes, null, aplicaExcepcion);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Re-fusión completa del legajo en una sola operación de servidor.
	 * Reemplaza la secuencia del cliente legacy: resetear legajo_control.fusionado,
	 * fusionar los cursos y recalcular el desarraigo.
	 */
	@PostMapping("/fusionar/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Void> fusionarCompletoByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @RequestBody(required = false) FusionarRequest request) {
		boolean aplicaExcepcion = (long) anho * 100L + mes < 201904;
		boolean forzar = request != null && request.forzar();
		service.fusionarCompletoByLegajo(legajoId, anho, mes, forzar, aplicaExcepcion);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	public record FusionarRequest(boolean forzar) {
	}

	@GetMapping("/desarraigobylegajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Void> desarraigoGradobylegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		service.desarraigoGradoByLegajo(legajoId, anho, mes);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/duplicarbylegajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Void> duplicarByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		service.duplicarByLegajo(legajoId, anho, mes);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/deletezombies/{anho}/{mes}")
	public ResponseEntity<Void> deleteZombies(@PathVariable Integer anho, @PathVariable Integer mes) {
		service.deleteZombies(anho, mes);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	/**
	 * Depura las designaciones fusionadas de un legajo que ya no tiene cursos cargo.
	 */
	@PostMapping("/depurafusion/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Void> depurarFusionByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		service.depurarFusionByLegajo(legajoId, anho, mes);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Marca como no fusionados los legajos con cargos en el curso (cambió el curso).
	 */
	@PostMapping("/invalidafusion/curso/{cursoId}/{anho}/{mes}")
	public ResponseEntity<Void> invalidarFusionByCurso(@PathVariable Long cursoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		service.invalidarFusionByCurso(cursoId, anho, mes);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/indiceantiguedad/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<BigDecimal>> indiceAntiguedad(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<>(service.indiceAntiguedad(legajoId, anho, mes), HttpStatus.OK);
	}
}
