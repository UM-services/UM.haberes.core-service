/**
 * 
 */
package ar.edu.um.haberes.rest.controller.facade;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.um.haberes.rest.service.facade.ToolService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/tool")
@Slf4j
public class ToolController {

	@PostMapping("/mailvalidate")
	public ResponseEntity<Boolean> mailvalidate(@RequestBody List<String> mailes) {
		log.debug(mailes.toString());
		return new ResponseEntity<Boolean>(ToolService.mailvalidate(mailes.get(0)), HttpStatus.OK);
	}
}
