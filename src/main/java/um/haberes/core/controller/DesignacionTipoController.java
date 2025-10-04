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
import um.haberes.core.exception.DesignacionTipoException;
import um.haberes.core.kotlin.model.DesignacionTipo;
import um.haberes.core.service.DesignacionTipoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/designaciontipo")
@RequiredArgsConstructor
public class DesignacionTipoController {

	private final DesignacionTipoService service;

	@GetMapping("/")
	public ResponseEntity<List<DesignacionTipo>> findAll() {
        return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("/{designacionTipoId}")
	public ResponseEntity<DesignacionTipo> findByDesignacionTipoId(@PathVariable Integer designacionTipoId) {
        try {
            return ResponseEntity.ok(service.findByDesignacionTipoId(designacionTipoId));
        } catch (DesignacionTipoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
	}
}
