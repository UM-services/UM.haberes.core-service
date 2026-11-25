/**
 *
 */
package um.haberes.core.controller.facade;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import um.haberes.core.exception.common.ImportNewsException;
import um.haberes.core.exception.common.TituloNotFoundException;
import um.haberes.core.service.facade.NovedadFileService;
import um.haberes.core.util.transfer.FileInfo;
import um.haberes.core.util.transfer.NovedadesTransferencia;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/novedadfile")
public class NovedadFileController {

    private final NovedadFileService service;

    public NovedadFileController(NovedadFileService service) {
        this.service = service;
    }

    @PostMapping("/upload/{anho}/{mes}/{stepByStep}")
    public ResponseEntity<String> upload(@RequestBody FileInfo fileInfo, @PathVariable Integer anho,
                                         @PathVariable Integer mes, @PathVariable Boolean stepByStep) {
        try {
            return new ResponseEntity<>(service.upload(fileInfo, anho, mes, stepByStep), HttpStatus.OK);
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

    @PostMapping("/transfer/{anho}/{mes}")
    public ResponseEntity<NovedadesTransferencia> transfer(@PathVariable Integer anho, @PathVariable Integer mes) {
        return new ResponseEntity<>(service.transfer(anho, mes), HttpStatus.OK);
    }

    /**
     * Compatibilidad con el cliente legacy (`clsCtlLiquidacion.transfer` del proyecto VB6, que
     * todavía invoca por GET y no revisa el status). El cliente web usa el POST. Retirar cuando
     * se deje de usar el cliente VB6.
     */
    @Deprecated
    @GetMapping("/transfer/{anho}/{mes}")
    public ResponseEntity<Void> transferLegacy(@PathVariable Integer anho, @PathVariable Integer mes) {
        service.transfer(anho, mes);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
