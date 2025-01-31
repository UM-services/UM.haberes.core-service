package um.haberes.core.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.haberes.core.kotlin.model.view.TotalItem;
import um.haberes.core.service.view.TotalItemService;

import java.util.List;

@RestController
@RequestMapping("/api/haberes/core/totalitem")
public class TotalItemController {
    @Autowired
    private TotalItemService service;

    @GetMapping("/periodo/{anho}/{mes}")
    public ResponseEntity<List<TotalItem>> findAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
        return new ResponseEntity<>(service.findAllByPeriodo(anho, mes), HttpStatus.OK);
    }

    @GetMapping("/unique/{anho}/{mes}/{codigoId}")
    public ResponseEntity<TotalItem> findByUnique(@PathVariable Integer anho, @PathVariable Integer mes,
                                                     @PathVariable Integer codigoId) {
        return new ResponseEntity<>(service.findByUnique(anho, mes, codigoId), HttpStatus.OK);
    }
}
