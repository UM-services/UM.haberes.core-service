/**
 * 
 */
package um.haberes.rest.controller.facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.rest.service.facade.BonoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/bono")
public class BonoController {

	@Autowired
	private BonoService service;

	@GetMapping("/generatePdf/{legajoId}/{anho}/{mes}/{legajoIdSolicitud}/{ipAddress}")
	public ResponseEntity<Resource> generatePdf(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Long legajoIdSolicitud, @PathVariable String ipAddress)
			throws FileNotFoundException {
		String filename = service.generatePdf(legajoId, anho, mes, legajoIdSolicitud, ipAddress);
		File file = new File(filename);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bono.pdf");
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

	@GetMapping("/generatePdfDependencia/{anho}/{mes}/{dependenciaId}/{salida}/{legajoIdSolicitud}/{ipAddress}")
	public ResponseEntity<Resource> generatePdfDependencia(@PathVariable Integer anho, @PathVariable Integer mes,
			@PathVariable Integer dependenciaId, @PathVariable String salida, @PathVariable Long legajoIdSolicitud,
			@PathVariable String ipAddress) throws FileNotFoundException {
		String filename = service.generatePdfDependencia(anho, mes, dependenciaId, salida, legajoIdSolicitud,
				ipAddress);
		File file = new File(filename);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bonos.pdf");
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

	@GetMapping("/sendBono/{legajoId}/{anho}/{mes}/{legajoIdSolicitud}/{ipAddress}")
	public String sendBono(@PathVariable Long legajoId, @PathVariable Integer anho, @PathVariable Integer mes,
			@PathVariable Long legajoIdSolicitud, @PathVariable String ipAddress) throws MessagingException {
		return service.sendBono(legajoId, anho, mes, legajoIdSolicitud, ipAddress);
	}

}
