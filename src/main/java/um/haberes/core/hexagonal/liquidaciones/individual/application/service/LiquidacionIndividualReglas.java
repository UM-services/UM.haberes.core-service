package um.haberes.core.hexagonal.liquidaciones.individual.application.service;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.CargoLiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.cargos_clase.CargoClaseCrossSlice;
import um.haberes.core.hexagonal.crossslice.control.ControlCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.LiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;
import um.haberes.core.hexagonal.crossslice.novedad.NovedadCrossSlice;
import um.haberes.core.hexagonal.crossslice.novedad.model.NovedadView;
import um.haberes.core.hexagonal.crossslice.persona.PersonaCrossSlice;
import um.haberes.core.hexagonal.crossslice.persona.model.PersonaView;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.AccionesLiquidacionIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.CodigoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.EstadoProcesoIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResultadoProcesoIndividual;

/**
 * Reglas de negocio de liquidación individual.
 *
 * <p>Reimplementa en el slice las reglas que el formulario legacy tenía repartidas en sus event
 * handlers y en los {@code return} silenciosos del motor. No modifica el motor: lo valida antes y
 * lo relee después.
 */
@Component
@RequiredArgsConstructor
public class LiquidacionIndividualReglas {

    private static final byte BLOQUEADO = 1;
    private static final int ESTADO_BAJA = 9;
    private static final int ESTADO_ACTIVO = 1;
    private static final String LIQUIDA_SI = "S";
    private static final String LIQUIDA_NO = "N";
    private static final String MOTIVO_BLOQUEO =
            "La liquidación del período está bloqueada. Active «Forzar» para modificarla.";

    private final LiquidacionCrossSlice liquidacionCrossSlice;

    private final ControlCrossSlice controlCrossSlice;

    private final PersonaCrossSlice personaCrossSlice;

    private final CargoLiquidacionCrossSlice cargoLiquidacionCrossSlice;

    private final CargoClaseCrossSlice cargoClaseCrossSlice;

    private final NovedadCrossSlice novedadCrossSlice;

    public boolean estaBloqueada(Optional<LiquidacionView> liquidacion) {
        return liquidacion.map(LiquidacionView::bloqueado)
                .map(bloqueado -> bloqueado != null && bloqueado == BLOQUEADO)
                .orElse(false);
    }

    public AccionesLiquidacionIndividual acciones(Optional<LiquidacionView> liquidacion, boolean hayControl) {
        boolean bloqueado = estaBloqueada(liquidacion);
        return new AccionesLiquidacionIndividual(
                hayControl && !bloqueado,
                hayControl,
                liquidacion.isPresent(),
                true,
                bloqueado,
                bloqueado ? MOTIVO_BLOQUEO : null);
    }

    /** RN-01: la liquidación bloqueada sólo admite cambios con {@code forzar}. */
    public Optional<ResultadoProcesoIndividual> bloquearSiCorresponde(Optional<LiquidacionView> liquidacion,
                                                                      boolean forzar) {
        if (!forzar && estaBloqueada(liquidacion)) {
            return Optional.of(ResultadoProcesoIndividual.de(EstadoProcesoIndividual.BLOQUEADA,
                    MOTIVO_BLOQUEO));
        }
        return Optional.empty();
    }

    /**
     * RN-03: elegibilidad de la persona, con la misma decisión que el motor legado.
     *
     * <p>La corrección de {@code liquida} a {@code "N"} también se replica: es un dato que el
     * proceso dejaba corregido al detectar el caso.
     */
    public Optional<ResultadoProcesoIndividual> validarPersona(Long legajoId, Integer anho, Integer mes) {
        Optional<PersonaView> persona = personaCrossSlice.porLegajo(legajoId);
        if (persona.isEmpty()) {
            return Optional.of(ResultadoProcesoIndividual.de(EstadoProcesoIndividual.PERSONA_INEXISTENTE,
                    "El legajo no existe."));
        }
        PersonaView personaView = persona.get();
        if (esBajaNoLiquida(personaView)) {
            Optional<LiquidacionView> anterior = liquidacionCrossSlice.porPeriodoAnterior(legajoId, anho, mes);
            if (anterior.map(this::esLiquidadaActiva).orElse(false)) {
                return Optional.of(ResultadoProcesoIndividual.de(
                        EstadoProcesoIndividual.SIN_LIQUIDACION_ANTERIOR,
                        "El legajo no registra liquidación anterior válida."));
            }
        }
        if (!esActivoLiquida(personaView)) {
            Optional<LiquidacionView> anterior = liquidacionCrossSlice.porPeriodoAnterior(legajoId, anho, mes);
            if (anterior.map(this::esBajaLiquidada).orElse(false)) {
                personaCrossSlice.marcarNoLiquida(legajoId);
                return Optional.of(ResultadoProcesoIndividual.de(
                        EstadoProcesoIndividual.PERSONA_NO_LIQUIDABLE,
                        "El legajo no está en estado liquidable."));
            }
        }
        return Optional.empty();
    }

    /** RN-04: sólo se liquida si hay cargos activos, cargos con clase o novedades especiales. */
    public boolean periodoLiquidable(Long legajoId, Integer anho, Integer mes,
                                     java.util.List<NovedadView> novedades) {
        boolean tieneNovedadEspecial = novedades != null && novedades.stream()
                .map(NovedadView::codigoId)
                .anyMatch(codigoId -> CodigoLiquidacion.AGUINALDO.coincideCon(codigoId)
                        || CodigoLiquidacion.AGUINALDO_ETEC.coincideCon(codigoId)
                        || CodigoLiquidacion.VACACIONES.coincideCon(codigoId));
        if (tieneNovedadEspecial) {
            return true;
        }
        return cargoLiquidacionCrossSlice.tieneCargosActivos(legajoId, anho, mes)
                || cargoClaseCrossSlice.tieneCargos(legajoId, anho, mes);
    }

    /** RN-02: sin control del período no se puede liquidar ni generar cargos. */
    public boolean hayControl(Integer anho, Integer mes) {
        return controlCrossSlice.porPeriodo(anho, mes).isPresent();
    }

    public Optional<NovedadView> buscarNovedad(Long legajoId, Integer anho, Integer mes, Integer codigoId) {
        return novedadCrossSlice.porClaveSinDependencia(legajoId, anho, mes, codigoId);
    }

    private boolean esBajaNoLiquida(PersonaView persona) {
        return persona.estado() != null && persona.estado() == ESTADO_BAJA
                && LIQUIDA_NO.equals(persona.liquida());
    }

    private boolean esActivoLiquida(PersonaView persona) {
        return persona.estado() != null && persona.estado() == ESTADO_ACTIVO
                && LIQUIDA_SI.equals(persona.liquida());
    }

    private boolean esLiquidadaActiva(LiquidacionView liquidacion) {
        return liquidacion.estado() != null && liquidacion.estado() == ESTADO_ACTIVO
                && LIQUIDA_SI.equals(liquidacion.liquida());
    }

    private boolean esBajaLiquidada(LiquidacionView liquidacion) {
        return liquidacion.estado() != null && liquidacion.estado() == ESTADO_BAJA
                && LIQUIDA_SI.equals(liquidacion.liquida());
    }
}
