/**
 * 
 */
package um.haberes.core.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.core.kotlin.model.NovedadUpload;
import um.haberes.core.service.NovedadUploadService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/novedadupload")
@RequiredArgsConstructor
public class NovedadUploadController {

	private final NovedadUploadService service;

	@GetMapping("/pendiente/{anho}/{mes}/{pendiente}")
	public ResponseEntity<List<NovedadUpload>> findAllByPendiente(@PathVariable Integer anho, @PathVariable Integer mes,
																  @PathVariable Byte pendiente) {
        return ResponseEntity.ok(service.findAllByPendiente(anho, mes, pendiente));
	}

}
