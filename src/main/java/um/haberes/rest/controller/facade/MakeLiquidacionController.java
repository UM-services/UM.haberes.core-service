/**
 *
 */
package um.haberes.rest.controller.facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.rest.kotlin.model.Item;
import um.haberes.rest.service.facade.MakeLiquidacionService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/makeLiquidacion")
public class MakeLiquidacionController {

    private final MakeLiquidacionService service;

    @Autowired
    public MakeLiquidacionController(MakeLiquidacionService service) {
        this.service = service;
    }

    @GetMapping("/legajo/{legajoId}/{anho}/{mes}/{force}")
    public ResponseEntity<Void> liquidacionByLegajoId(@PathVariable Long legajoId, @PathVariable Integer anho,
                                                      @PathVariable Integer mes, @PathVariable Boolean force) {
        service.liquidacionByLegajoId(legajoId, anho, mes, force);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/legajo/{legajoId}/{anho}/{mes}/{force}")
    public ResponseEntity<Void> deleteLiquidacionByLegajoId(@PathVariable Long legajoId, @PathVariable Integer anho,
                                                            @PathVariable Integer mes, @PathVariable Boolean force) {
        service.deleteLiquidacionByLegajoId(legajoId, anho, mes, force);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/basicoAndAntiguedad/{legajoId}/{anho}/{mes}")
    public ResponseEntity<List<Item>> basicoAndAntiguedad(@PathVariable Long legajoId, @PathVariable Integer anho,
                                                          @PathVariable Integer mes) {
        return new ResponseEntity<>(service.basicoAndAntiguedad(legajoId, anho, mes), HttpStatus.OK);
    }

    @GetMapping("/generateCargosDocentes/{legajoId}/{anho}/{mes}")
    public ResponseEntity<Void> generateCargosDocentes(@PathVariable Long legajoId, @PathVariable Integer anho,
                                                       @PathVariable Integer mes) {
        service.generateCargosDocentes(legajoId, anho, mes);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/generateCargosNoDocentes/{legajoId}/{anho}/{mes}")
    public ResponseEntity<Void> generateCargosNoDocentes(@PathVariable Long legajoId, @PathVariable Integer anho,
                                                         @PathVariable Integer mes) {
        service.generateCargosNoDocentes(legajoId, anho, mes);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/deleteNovedadDuplicada/{anho}/{mes}")
    public ResponseEntity<Void> deleteNovedadDuplicada(@PathVariable Integer anho, @PathVariable Integer mes) {
        service.deleteNovedadDuplicada(anho, mes);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/desmarcaPeriodo/{anho}/{mes}")
    public ResponseEntity<Void> desmarcaPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
        service.desmarcaPeriodo(anho, mes);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/generateSIJP/{anho}/{mes}")
    public ResponseEntity<Resource> generateSIJP(@PathVariable Integer anho, @PathVariable Integer mes)
            throws IOException {
        String filename = service.generateSIJP(anho, mes);
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sijp.txt");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

}
