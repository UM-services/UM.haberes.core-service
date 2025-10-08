/**
 * 
 */
package um.haberes.core.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
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

import um.haberes.core.exception.LegajoBancoException;
import um.haberes.core.kotlin.model.LegajoBanco;
import um.haberes.core.service.LegajoBancoService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/legajobanco")
@Slf4j
@RequiredArgsConstructor
public class LegajoBancoController {

	private final LegajoBancoService service;

	@GetMapping("/{legajoBancoId}")
	public ResponseEntity<LegajoBanco> findByLegajoBancoId(@PathVariable Long legajoBancoId) {
		try {
            return ResponseEntity.ok(service.findByLegajoBancoId(legajoBancoId));
		} catch (LegajoBancoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/unique/{legajoId}/{anho}/{mes}/{cbu}")
	public ResponseEntity<LegajoBanco> findByUnique(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable String cbu) {
		try {
            return ResponseEntity.ok(service.findByUnique(legajoId, anho, mes, cbu));
		} catch (LegajoBancoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/lastlegajo/{legajoId}")
	public ResponseEntity<LegajoBanco> findLastByLegajoId(@PathVariable Long legajoId) {
		try {
            return ResponseEntity.ok(service.findLastByLegajoId(legajoId));
		} catch (LegajoBancoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/legajo/{legajoId}")
	public ResponseEntity<List<LegajoBanco>> findAllByLegajoId(@PathVariable Long legajoId) {
        return ResponseEntity.ok(service.findAllByLegajoId(legajoId));
	}

	@GetMapping("/legajoperiodo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<LegajoBanco>> findAllByLegajoPeriodo(
            @PathVariable Long legajoId,
			@PathVariable Integer anho,
            @PathVariable Integer mes) {
        return ResponseEntity.ok(service.findAllByLegajoPeriodo(legajoId, anho, mes));
	}

	@GetMapping("/cbuprincipal/{legajoId}/{anho}/{mes}")
	public ResponseEntity<LegajoBanco> findLegajoCbuPrincipal(@PathVariable Long legajoId, @PathVariable Integer anho, @PathVariable Integer mes) {
		try {
            return ResponseEntity.ok(service.findLegajoCbuPrincipal(legajoId, anho, mes));
		} catch (LegajoBancoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<List<LegajoBanco>> findAllPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
        return ResponseEntity.ok(service.findAllPeriodo(anho, mes));
	}

	@GetMapping("/periodosantander/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<LegajoBanco>> findAllPeriodoSantander(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
        return ResponseEntity.ok(service.findAllPeriodoSantander(legajoId, anho, mes));
	}

	@GetMapping("/periodootrosbancos/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<LegajoBanco>> findAllPeriodoOtrosBancos(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
        return ResponseEntity.ok(service.findAllPeriodoOtrosBancos(legajoId, anho, mes));
	}

	@PostMapping("/")
	public ResponseEntity<LegajoBanco> add(@RequestBody LegajoBanco legajobanco) {
        return ResponseEntity.ok(service.add(legajobanco));
	}

	@PutMapping("/{legajobancoId}")
	public ResponseEntity<LegajoBanco> update(@RequestBody LegajoBanco legajobanco,
                                              @PathVariable Long legajobancoId) {
        return ResponseEntity.ok(service.update(legajobanco, legajobancoId));
	}

	@GetMapping("/santander/{salida}/{anho}/{mes}/{dependenciaId}")
	public ResponseEntity<List<LegajoBanco>> findAllSantander(@PathVariable String salida,
                                                              @PathVariable Integer anho,
                                                              @PathVariable Integer mes,
                                                              @PathVariable Integer dependenciaId) {
        return ResponseEntity.ok(service.findAllSantander(salida, anho, mes, dependenciaId));
	}

    @GetMapping("/santander/{salida}/{anho}/{mes}/{dependenciaId}/codigo/{codigoId}")
    public ResponseEntity<List<LegajoBanco>> findAllSantanderConCodigo(@PathVariable String salida,
                                                              @PathVariable Integer anho,
                                                              @PathVariable Integer mes,
                                                              @PathVariable Integer dependenciaId,
                                                              @PathVariable Integer codigoId) {
        return ResponseEntity.ok(service.findAllSantanderConCodigo(salida, anho, mes, dependenciaId, codigoId));
    }

    @GetMapping("/otrosbancos/{salida}/{anho}/{mes}/{dependenciaId}")
	public ResponseEntity<List<LegajoBanco>> findAllOtrosBancos(@PathVariable String salida,
                                                                @PathVariable Integer anho,
                                                                @PathVariable Integer mes,
                                                                @PathVariable Integer dependenciaId) {
        return ResponseEntity.ok(service.findAllOtrosBancos(salida, anho, mes, dependenciaId));
	}

    @GetMapping("/otrosbancos/{salida}/{anho}/{mes}/{dependenciaId}/codigo/{codigoId}")
    public ResponseEntity<List<LegajoBanco>> findAllOtrosBancosConCodigo(@PathVariable String salida,
                                                                        @PathVariable Integer anho,
                                                                        @PathVariable Integer mes,
                                                                        @PathVariable Integer dependenciaId,
                                                                        @PathVariable Integer codigoId) {
        return ResponseEntity.ok(service.findAllOtrosBancosConCodigo(salida, anho, mes, dependenciaId, codigoId));
    }

	@DeleteMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<Void> deleteAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		service.deleteAllByPeriodo(anho, mes);
        return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{legajobancoId}")
	public ResponseEntity<Void> delete(@PathVariable Long legajobancoId) {
		service.deleteByLegajobancoId(legajobancoId);
        return ResponseEntity.noContent().build();
	}

}
