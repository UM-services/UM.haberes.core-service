package um.haberes.core.service.facade.liquidaciones;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.service.facade.MakeLiquidacionService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class LiquidacionProcessor {

    private final MakeLiquidacionService makeLiquidacionService;

    @Async
    public void procesarLiquidaciones(List<Persona> personas, Integer anho, Integer mes, Boolean force, LiquidacionProceso proceso) {
        try {
            List<CompletableFuture<Void>> futures = personas.stream()
                    .map(persona -> CompletableFuture.runAsync(() -> {
                        try {
                            makeLiquidacionService.liquidacionByLegajoId(persona.getLegajoId(), anho, mes, force);
                        } catch (Exception e) {
                            log.error("Error liquidando legajo {}", persona.getLegajoId(), e);
                        } finally {
                            proceso.legajoProcesado();
                        }
                    }))
                    .toList();

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            proceso.setStatus(LiquidacionProceso.Status.COMPLETED);
        } catch (Exception e) {
            proceso.setStatus(LiquidacionProceso.Status.FAILED);
            log.error("Error en la liquidacion general", e);
        }
    }
}
