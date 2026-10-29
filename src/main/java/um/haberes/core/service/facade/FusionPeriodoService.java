package um.haberes.core.service.facade;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.LegajoControlException;
import um.haberes.core.model.LegajoControlEntity;
import um.haberes.core.service.LegajoControlService;

/**
 * Procesos masivos por período del circuito de designaciones.
 * <p>
 * Cada legajo se procesa en su propia transacción (se invoca el servicio de
 * designaciones a través del proxy de Spring), y el cliente pide el período en
 * lotes: así una corrida larga no queda atada a un único request ni a una única
 * transacción gigante. Los fallos por legajo se informan sin cortar el lote.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FusionPeriodoService {

    private final DesignacionToolService designacionToolService;
    private final LegajoControlService legajoControlService;

    /**
     * Proceso completo de fusión de un lote: reset de legajo_control, redesignar,
     * fusionar y recalcular desarraigo (la secuencia que hacía el cliente legacy).
     */
    public ResumenLote procesarFusion(List<Long> legajos, Integer anho, Integer mes) {
        boolean aplicaExcepcion = (long) anho * 100L + mes < 201904;
        List<Fallo> fallidos = new ArrayList<>();
        int procesados = 0;

        for (Long legajoId : legajos) {
            try {
                marcarNoFusionado(legajoId, anho, mes);
                designacionToolService.redesignarGradoByLegajo(legajoId, anho, mes, aplicaExcepcion);
                designacionToolService.fusionarGradoByLegajo(legajoId, anho, mes, null, aplicaExcepcion);
                designacionToolService.desarraigoGradoByLegajo(legajoId, anho, mes);
                procesados++;
            } catch (Exception e) {
                log.warn("Fusion del legajo {} periodo {}/{} fallida: {}", legajoId, mes, anho, e.getMessage());
                fallidos.add(new Fallo(legajoId, e.getMessage()));
            }
        }
        log.debug("Fusion de lote {}/{} -> procesados={} fallidos={}", mes, anho, procesados, fallidos.size());
        return new ResumenLote(procesados, fallidos);
    }

    /** Recalcula el desarraigo de un lote de legajos. */
    public ResumenLote procesarDesarraigo(List<Long> legajos, Integer anho, Integer mes) {
        List<Fallo> fallidos = new ArrayList<>();
        int procesados = 0;

        for (Long legajoId : legajos) {
            try {
                designacionToolService.desarraigoGradoByLegajo(legajoId, anho, mes);
                procesados++;
            } catch (Exception e) {
                log.warn("Desarraigo del legajo {} periodo {}/{} fallido: {}", legajoId, mes, anho, e.getMessage());
                fallidos.add(new Fallo(legajoId, e.getMessage()));
            }
        }
        return new ResumenLote(procesados, fallidos);
    }

    /**
     * Duplica los cargos del mes anterior sobre el período indicado.
     * El legajo se toma del período anterior, como en el proceso legacy.
     */
    public ResumenLote procesarDuplicado(List<Long> legajos, Integer anho, Integer mes) {
        List<Fallo> fallidos = new ArrayList<>();
        int procesados = 0;

        for (Long legajoId : legajos) {
            try {
                designacionToolService.duplicarByLegajo(legajoId, anho, mes);
                procesados++;
            } catch (Exception e) {
                log.warn("Duplicado del legajo {} periodo {}/{} fallido: {}", legajoId, mes, anho, e.getMessage());
                fallidos.add(new Fallo(legajoId, e.getMessage()));
            }
        }
        return new ResumenLote(procesados, fallidos);
    }

    /** Elimina las fusiones huérfanas del período (legajos sin cursos cargo). */
    @Transactional
    public void depurarZombies(Integer anho, Integer mes) {
        designacionToolService.deleteZombies(anho, mes);
    }

    private void marcarNoFusionado(Long legajoId, Integer anho, Integer mes) {
        LegajoControlEntity legajoControl;
        try {
            legajoControl = legajoControlService.findByUnique(legajoId, anho, mes);
        } catch (LegajoControlException e) {
            legajoControl = new LegajoControlEntity();
            legajoControl.setLegajoId(legajoId);
            legajoControl.setAnho(anho);
            legajoControl.setMes(mes);
        }
        legajoControl.setFusionado((byte) 0);
        legajoControlService.save(legajoControl);
    }

    public record ResumenLote(int procesados, List<Fallo> fallidos) {
    }

    public record Fallo(Long legajoId, String error) {
    }

}
