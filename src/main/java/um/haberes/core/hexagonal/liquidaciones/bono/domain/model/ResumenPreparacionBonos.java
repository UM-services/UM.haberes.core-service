package um.haberes.core.hexagonal.liquidaciones.bono.domain.model;

import java.util.List;

/**
 * Resultado de preparar (actividad) y auditar la impresión de todos los bonos de una
 * dependencia y período. Un legajo que falla no corta el lote: se informa en {@code fallidos}.
 */
public record ResumenPreparacionBonos(Integer dependenciaId, Integer anho, Integer mes, String salida,
                                      int total, int preparados, List<Fallido> fallidos) {

    public ResumenPreparacionBonos {
        fallidos = fallidos == null ? List.of() : List.copyOf(fallidos);
    }

    public record Fallido(Long legajoId, String motivo) {
    }
}
