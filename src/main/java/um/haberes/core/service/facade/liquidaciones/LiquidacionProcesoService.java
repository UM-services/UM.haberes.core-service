package um.haberes.core.service.facade.liquidaciones;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LiquidacionProcesoService {

    private final Map<String, LiquidacionProceso> procesos = new ConcurrentHashMap<>();

    public LiquidacionProceso crearProceso(int total) {
        LiquidacionProceso proceso = new LiquidacionProceso(total);
        procesos.put(proceso.getId(), proceso);
        return proceso;
    }

    public Optional<LiquidacionProceso> getProceso(String id) {
        return Optional.ofNullable(procesos.get(id));
    }

}
