/**
 * 
 */
package um.haberes.core.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.core.kotlin.model.ConversionLog;
import um.haberes.core.service.ConversionLogService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/conversionlog")
public class ConversionLogController {
	
	private final ConversionLogService service;

	public ConversionLogController(ConversionLogService service) {
		this.service = service;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<ConversionLog>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<ConversionLog> add(@RequestBody ConversionLog conversion) {
		return new ResponseEntity<ConversionLog>(service.add(conversion), HttpStatus.OK);
	}
	
}
