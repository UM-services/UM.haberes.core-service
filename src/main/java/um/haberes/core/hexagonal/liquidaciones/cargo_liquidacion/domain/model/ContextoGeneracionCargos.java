package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;
import um.haberes.core.util.Periodo;

/**
 * Contexto de una generación de cargos: el legajo (nulo cuando la generación es del período
 * completo), el período, el modo de liquidación y los datos de referencia compartidos por los
 * generadores.
 */
public record ContextoGeneracionCargos(Long legajoId, Integer anho, Integer mes, int modoLiquidacionId,
                                       BigDecimal valorPuntoEtec, BigDecimal montoFijoCargo,
                                       Map<String, Dependencia> dependenciasPorSede) {

    private static final int MODO_CON_FUSION = 1;

    private static final int MODO_SIN_FUSION = 2;

    /** {@code true} cuando se genera para un legajo; {@code false} cuando es todo el período. */
    public boolean porLegajo() {
        return legajoId != null;
    }

    public boolean conFusion() {
        return modoLiquidacionId == MODO_CON_FUSION;
    }

    public boolean sinFusion() {
        return modoLiquidacionId == MODO_SIN_FUSION;
    }

    public OffsetDateTime fechaDesde() {
        return Periodo.firstDay(anho, mes);
    }

    public OffsetDateTime fechaHasta() {
        return Periodo.lastDay(anho, mes);
    }
}
