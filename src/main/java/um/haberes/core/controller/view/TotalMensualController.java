/**
 * 
 */
package um.haberes.core.controller.view;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.server.ResponseStatusException;
import um.haberes.core.exception.view.TotalMensualException;
import um.haberes.core.kotlin.model.view.TotalMensual;
import um.haberes.core.service.view.TotalMensualService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/totalmensual")
@RequiredArgsConstructor
public class TotalMensualController {

	private final TotalMensualService service;

	@GetMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<List<TotalMensual>> findAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
        return ResponseEntity.ok(service.findAllByPeriodo(anho, mes));
	}

	@GetMapping("/unique/{anho}/{mes}/{codigoId}")
	public ResponseEntity<TotalMensual> findByUnique(@PathVariable Integer anho, @PathVariable Integer mes,
			@PathVariable Integer codigoId) {
        try {
            return ResponseEntity.ok(service.findByUnique(anho, mes, codigoId));
        } catch (TotalMensualException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
	}

}
