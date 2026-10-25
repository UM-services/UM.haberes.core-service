package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.dto;

import java.util.List;

import lombok.Builder;

/**
 * Resumen de la preparación masiva de bonos de una dependencia: total de legajos del período,
 * cuántos quedaron preparados y auditados, y el detalle de los que fallaron.
 */
@Builder
public record BonoDependenciaPrintPrepareResponse(Integer dependenciaId, Integer anho, Integer mes,
                                                  String salida, int total, int preparados,
                                                  List<Fallido> fallidos) {

    public BonoDependenciaPrintPrepareResponse {
        fallidos = fallidos == null ? List.of() : List.copyOf(fallidos);
    }

    public record Fallido(Long legajoId, String motivo) {
    }
}
