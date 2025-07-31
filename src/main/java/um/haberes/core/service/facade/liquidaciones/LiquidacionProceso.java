package um.haberes.core.service.facade.liquidaciones;

import lombok.Data;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class LiquidacionProceso {
    private final String id = UUID.randomUUID().toString();
    private final int total;
    private AtomicInteger procesados = new AtomicInteger(0);
    private Status status = Status.RUNNING;

    public enum Status {
        RUNNING,
        COMPLETED,
        FAILED
    }

    public LiquidacionProceso(int total) {
        this.total = total;
    }

    public void legajoProcesado() {
        procesados.incrementAndGet();
    }

    public double getProgreso() {
        return total > 0 ? (double) procesados.get() / total * 100 : 0;
    }

}
