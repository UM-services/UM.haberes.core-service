package um.haberes.core.hexagonal.liquidaciones.general.domain.model;

import java.time.OffsetDateTime;

/** Estado de un proceso masivo de liquidación general. */
public record ProcesoGeneral(String procesoId, TipoProcesoGeneral tipo, Integer anho, Integer mes,
                             EstadoProcesoGeneral estado, int total, int procesados, int exitosos,
                             int omitidos, int fallidos, boolean forzar, Long iniciadoPor,
                             OffsetDateTime iniciadoEn, OffsetDateTime finalizadoEn, String mensaje) {

    /** Avance del proceso en porcentaje (0 a 100). */
    public int progreso() {
        if (total <= 0) {
            return 0;
        }
        return Math.min(100, (int) Math.round(procesados * 100.0 / total));
    }
}
