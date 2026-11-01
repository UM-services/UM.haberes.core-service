/**
 * 
 */
package um.haberes.core.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.core.model.SituacionEntity;
import um.haberes.core.service.SituacionService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/situacion")
@RequiredArgsConstructor
public class SituacionController {
	
	private final SituacionService service;

	@GetMapping("/")
	public ResponseEntity<List<SituacionEntity>> findAll() {
        return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("/{situacionId}")
	public ResponseEntity<SituacionEntity> findBySituacionID(@PathVariable Integer situacionId) {
        return ResponseEntity.ok(service.findBySituacionId(situacionId));
	}
}
