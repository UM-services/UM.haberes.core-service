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

import um.haberes.core.model.ConversionLogEntity;
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
	public ResponseEntity<List<ConversionLogEntity>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<ConversionLogEntity> add(@RequestBody ConversionLogEntity conversion) {
		return new ResponseEntity<ConversionLogEntity>(service.add(conversion), HttpStatus.OK);
	}
	
}
