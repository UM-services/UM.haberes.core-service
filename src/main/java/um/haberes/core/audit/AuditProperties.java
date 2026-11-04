package um.haberes.core.audit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ConfigurationProperties(prefix = "haberes.audit")
public class AuditProperties {

    public enum Mode {
        /** No captura nada. */
        OFF,
        /** Captura y mide, pero no escribe en la base ni envía. */
        LOG,
        /** Escribe en audit_outbox y envía al servicio de auditoría. */
        SEND
    }

    private Mode mode = Mode.OFF;

    private boolean requestEvents = true;

    private boolean changeEvents = true;

    private String url = "http://localhost:8210";

    private String apiKey = "";

    private int batchSize = 200;

    private long senderDelayMs = 2000;

    private long senderInitialDelayMs = 5000;

    private int maxAttempts = 10;

    private int maxPayloadChars = 32768;

    /** Días que se conservan las filas ya entregadas ({@code SENT}) antes de purgarlas. */
    private int sentRetentionDays = 7;

    /**
     * Días que se conservan las filas {@code FAILED} para poder inspeccionarlas. Sin esto
     * una entrega rechazada quedaba en la tabla para siempre.
     */
    private int failedRetentionDays = 30;

    /** Horas a partir de las cuales se avisa que hay {@code PENDING} sin entregar. */
    private int pendingWarnHours = 24;

    /** Cada cuánto corre la limpieza (por defecto, cada hora). */
    private long cleanupDelayMs = 3_600_000;

    /** Espera antes de la primera limpieza después de arrancar. */
    private long cleanupInitialDelayMs = 60_000;

    private List<String> maskFields = new ArrayList<>(List.of("password", "cbu", "cuenta", "documento"));

    private Set<String> excludedEntities = new HashSet<>(Set.of("BuildEntity", "ConversionLogEntity"));

    /**
     * Kill switch del resumen masivo: con {@code false} los scopes son inertes y todo se
     * audita fila por fila (comportamiento previo), sin necesidad de deploy.
     */
    private boolean bulkEnabled = true;

    /**
     * Entidades elegibles para resumirse dentro de un {@link AuditBulkScope}. Fuera del
     * scope siguen emitiendo su evento {@code CHANGE} normal: la configuración no silencia
     * nada por sí sola.
     */
    private Set<String> summaryEntities = new HashSet<>(Set.of(
            "ItemEntity", "ItemVersionEntity",
            "CargoLiquidacionEntity", "CargoLiquidacionVersionEntity"));
}
