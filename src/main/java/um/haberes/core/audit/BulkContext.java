package um.haberes.core.audit;

import java.util.UUID;

/**
 * Contexto de una operación masiva auditada como un solo evento {@code SUMMARY/BULK}.
 *
 * <p>{@code label} describe la operación de negocio ("recalculo-legajo",
 * "borrado-periodo-item"), {@code anho}/{@code mes}/{@code legajoId} acotan el alcance y
 * {@code bulkId} agrupa varios eventos cuando la operación se parte en chunks.</p>
 */
public record BulkContext(String label, Integer anho, Integer mes, Long legajoId,
                          String bulkId, int chunkIndex, int chunkSize) {

    /** Operación completa en una sola transacción (por ejemplo, un legajo). */
    public static BulkContext of(String label, Integer anho, Integer mes, Long legajoId) {
        return new BulkContext(label, anho, mes, legajoId, UUID.randomUUID().toString(), 0, 0);
    }

    /** Un chunk de una operación mayor; todos comparten {@code bulkId}. */
    public static BulkContext chunk(String label, Integer anho, Integer mes, String bulkId,
                                    int chunkIndex, int chunkSize) {
        return new BulkContext(label, anho, mes, null, bulkId, chunkIndex, chunkSize);
    }
}
