package um.haberes.core.service.facade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import um.haberes.core.kotlin.model.Persona;
import um.haberes.core.service.PersonaService;
import um.haberes.core.service.facade.liquidaciones.LiquidacionProceso;
import um.haberes.core.service.facade.liquidaciones.LiquidacionProcesoService;
import um.haberes.core.service.facade.liquidaciones.LiquidacionProcessor;

import java.util.List;

@Service
@Slf4j
public class MakeLiquidacionGeneralService {

    private final PersonaService personaService;
    private final LiquidacionProcesoService liquidacionProcesoService;
    private final LiquidacionProcessor liquidacionProcessor;


    public MakeLiquidacionGeneralService(PersonaService personaService,
                                         LiquidacionProcesoService liquidacionProcesoService,
                                         LiquidacionProcessor liquidacionProcessor) {
        this.personaService = personaService;
        this.liquidacionProcesoService = liquidacionProcesoService;
        this.liquidacionProcessor = liquidacionProcessor;
    }

    public LiquidacionProceso liquidacionGeneral(Integer anho, Integer mes, Boolean force) {
        List<Persona> personas = personaService.findAllLiquidables();
        LiquidacionProceso proceso = liquidacionProcesoService.crearProceso(personas.size());
        liquidacionProcessor.procesarLiquidaciones(personas, anho, mes, force, proceso);
        return proceso;
    }

}
