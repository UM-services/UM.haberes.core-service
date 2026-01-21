/**
 *
 */
package um.haberes.core.controller.facade;

import java.io.IOException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.core.kotlin.model.Item;
import um.haberes.core.service.facade.MakeLiquidacionService;
import um.haberes.core.util.Tool;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/makeLiquidacion")
@RequiredArgsConstructor
public class MakeLiquidacionController {

    private final MakeLiquidacionService service;

    @GetMapping("/legajo/{legajoId}/{anho}/{mes}/{force}")
    public ResponseEntity<Void> liquidacionByLegajoId(@PathVariable Long legajoId, @PathVariable Integer anho,
                                                      @PathVariable Integer mes, @PathVariable Boolean force) {
        service.liquidacionByLegajoId(legajoId, anho, mes, force);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/legajo/{legajoId}/{anho}/{mes}/{force}")
    public ResponseEntity<Void> deleteLiquidacionByLegajoId(@PathVariable Long legajoId, @PathVariable Integer anho,
                                                            @PathVariable Integer mes, @PathVariable Boolean force) {
        service.deleteLiquidacionByLegajoId(legajoId, anho, mes, force);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/basicoAndAntiguedad/{legajoId}/{anho}/{mes}")
    public ResponseEntity<List<Item>> basicoAndAntiguedad(@PathVariable Long legajoId, @PathVariable Integer anho,
                                                          @PathVariable Integer mes) {
        return ResponseEntity.ok(service.basicoAndAntiguedad(legajoId, anho, mes));
    }

    @GetMapping("/generateCargosDocentes/{legajoId}/{anho}/{mes}")
    public ResponseEntity<Void> generateCargosDocentes(@PathVariable Long legajoId, @PathVariable Integer anho,
                                                       @PathVariable Integer mes) {
        service.generateCargosDocentes(legajoId, anho, mes);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/generateCargosNoDocentes/{legajoId}/{anho}/{mes}")
    public ResponseEntity<Void> generateCargosNoDocentes(@PathVariable Long legajoId, @PathVariable Integer anho,
                                                         @PathVariable Integer mes) {
        service.generateCargosNoDocentes(legajoId, anho, mes);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/deleteNovedadDuplicada/{anho}/{mes}")
    public ResponseEntity<Void> deleteNovedadDuplicada(@PathVariable Integer anho, @PathVariable Integer mes) {
        service.deleteNovedadDuplicada(anho, mes);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/desmarcaPeriodo/{anho}/{mes}")
    public ResponseEntity<Void> desmarcaPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
        service.desmarcaPeriodo(anho, mes);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/generateSIJP/{anho}/{mes}")
    public ResponseEntity<Resource> generateSIJP(@PathVariable Integer anho, @PathVariable Integer mes)
            throws IOException {
        return Tool.generateFile(service.generateSIJP(anho, mes), "sijp.txt");
    }

}
