package um.haberes.core.hexagonal.liquidaciones.general.infrastructure.web.dto;

import java.math.BigDecimal;
import java.util.List;

/** Padrón agregado de liquidación general del período. */
public record PadronLiquidacionGeneralResponse(Integer anho, Integer mes, Acciones acciones,
                                               Resumen resumen, List<Fila> filas) {

    public record Acciones(boolean hayControl, boolean puedeGenerarCargos, boolean puedeLiquidar,
                           boolean puedeEliminar) {
    }

    public record Resumen(int total, int docentes, int noDocentes, int conNovedades, int liquidados,
                          int bloqueados, int netoNegativo, int netoCero, BigDecimal totalNeto) {
    }

    public record Fila(int orden, Long legajoId, String apellido, String nombre, Integer estado,
                       String liquida, Integer dependenciaId, String salida, boolean esDocente,
                       boolean esNoDocente, boolean tieneNovedades, boolean liquidado, boolean bloqueado,
                       BigDecimal totalRemunerativo, BigDecimal totalNoRemunerativo,
                       BigDecimal totalDeduccion, BigDecimal totalNeto) {
    }
}
