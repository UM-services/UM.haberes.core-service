/**
 * 
 */
package um.haberes.core.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.server.ResponseStatusException;
import um.haberes.core.exception.CargoTipoException;
import um.haberes.core.kotlin.model.CargoTipo;
import um.haberes.core.service.CargoTipoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/cargotipo")
@RequiredArgsConstructor
public class CargoTipoController {

	private final CargoTipoService service;

	@GetMapping("/")
	public ResponseEntity<List<CargoTipo>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("/{cargoTipoId}")
	public ResponseEntity<CargoTipo> findByCargoTipoId(@PathVariable Integer cargoTipoId) {
        try {
            return ResponseEntity.ok(service.findByCargoTipoId(cargoTipoId));
        } catch (CargoTipoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
	}

}
