package um.haberes.core.controller.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.haberes.core.service.facade.MakeLiquidacionGeneralService;
import um.haberes.core.service.facade.liquidaciones.LiquidacionProceso;
import um.haberes.core.service.facade.liquidaciones.LiquidacionProcesoService;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/haberes/core/makeLiquidacionGeneral")
public class MakeLiquidacionGeneralController {

    private final MakeLiquidacionGeneralService service;
    private final LiquidacionProcesoService liquidacionProcesoService;

    public MakeLiquidacionGeneralController(MakeLiquidacionGeneralService service, LiquidacionProcesoService liquidacionProcesoService) {
        this.service = service;
        this.liquidacionProcesoService = liquidacionProcesoService;
    }

    @GetMapping("/general/{anho}/{mes}/{force}")
    public ResponseEntity<Map<String, String>> liquidacionGeneral(@PathVariable Integer anho, @PathVariable Integer mes, @PathVariable Boolean force) {
        LiquidacionProceso proceso = service.liquidacionGeneral(anho, mes, force);
        return ResponseEntity.accepted().body(Collections.singletonMap("procesoId", proceso.getId()));
    }

    @GetMapping("/general/progress/{procesoId}")
    public ResponseEntity<LiquidacionProceso> getProgress(@PathVariable String procesoId) {
        return liquidacionProcesoService.getProceso(procesoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
