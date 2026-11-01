/**
 * 
 */
package um.haberes.core.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.core.model.NivelEntity;
import um.haberes.core.service.NivelService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/nivel")
@RequiredArgsConstructor
public class NivelController {
	
	private final NivelService service;
	
	@GetMapping("/")
	public ResponseEntity<List<NivelEntity>> findAll() {
        return ResponseEntity.ok(service.findAll());
	}
	
}
