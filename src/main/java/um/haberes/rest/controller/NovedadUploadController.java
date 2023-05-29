/**
 * 
 */
package um.haberes.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.rest.model.NovedadUpload;
import um.haberes.rest.service.NovedadUploadService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/novedadupload")
public class NovedadUploadController {

	@Autowired
	private NovedadUploadService service;

	@GetMapping("/pendiente/{anho}/{mes}/{pendiente}")
	public ResponseEntity<List<NovedadUpload>> findAllByPendiente(@PathVariable Integer anho, @PathVariable Integer mes,
			@PathVariable Byte pendiente) {
		return new ResponseEntity<List<NovedadUpload>>(service.findAllByPendiente(anho, mes, pendiente), HttpStatus.OK);
	}
}
