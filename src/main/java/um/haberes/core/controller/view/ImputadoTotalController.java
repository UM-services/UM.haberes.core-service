/**
 * 
 */
package um.haberes.core.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.server.ResponseStatusException;
import um.haberes.core.exception.view.ImputadoTotalException;
import um.haberes.core.kotlin.model.view.ImputadoTotal;
import um.haberes.core.service.view.ImputadoTotalService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/imputadototal")
@RequiredArgsConstructor
public class ImputadoTotalController {
	
	private final ImputadoTotalService service;
	
	@GetMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<ImputadoTotal> findByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
        try {
            return ResponseEntity.ok(service.findByPeriodo(anho, mes));
        } catch (ImputadoTotalException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
	}
	
}
