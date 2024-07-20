/**
 *
 */
package um.haberes.rest.controller.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import um.haberes.rest.exception.common.ImportNewsException;
import um.haberes.rest.exception.common.TituloNotFoundException;
import um.haberes.rest.service.facade.NovedadFileService;
import um.haberes.rest.util.transfer.FileInfo;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/novedadfile")
public class NovedadFileController {

    private final NovedadFileService service;

    @Autowired
    public NovedadFileController(NovedadFileService service) {
        this.service = service;
    }

    @PostMapping("/upload/{anho}/{mes}")
    public ResponseEntity<String> upload(@RequestBody FileInfo fileInfo, @PathVariable Integer anho,
                                         @PathVariable Integer mes) {
        try {
            return new ResponseEntity<>(service.upload(fileInfo, anho, mes), HttpStatus.OK);
        } catch (TituloNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/import/{anho}/{mes}")
    public ResponseEntity<Void> importNews(@PathVariable Integer anho, @PathVariable Integer mes) {
        service.importNews(anho, mes);
        try {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ImportNewsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping("/transfer/{anho}/{mes}")
    public ResponseEntity<Void> transfer(@PathVariable Integer anho, @PathVariable Integer mes) {
        service.transfer(anho, mes);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
