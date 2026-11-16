package um.haberes.core.hexagonal.liquidaciones.individual.application.service;

import java.util.Optional;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.CargoLiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.cargos_clase.CargoClaseCrossSlice;
import um.haberes.core.hexagonal.crossslice.control.ControlCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.LiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;
import um.haberes.core.hexagonal.crossslice.novedad.NovedadCrossSlice;
import um.haberes.core.hexagonal.crossslice.novedad.model.NovedadView;
import um.haberes.core.hexagonal.crossslice.persona.PersonaCrossSlice;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.AccionesLiquidacionIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.EstadoProcesoIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResultadoProcesoIndividual;
import um.haberes.core.hexagonal.liquidaciones.reglas.Inelegibilidad;
import um.haberes.core.hexagonal.liquidaciones.reglas.MotivoInelegibilidad;
import um.haberes.core.hexagonal.liquidaciones.reglas.ReglasElegibilidadLiquidacion;

/**
 * Reglas de negocio de liquidación individual.
 *
 * <p>Delega la elegibilidad en {@link ReglasElegibilidadLiquidacion}, compartida con la
 * liquidación general, y traduce los motivos neutrales al vocabulario del slice individual.
 * No modifica el motor: lo valida antes y lo relee después.</p>
 */
@Component
public class LiquidacionIndividualReglas {

    public static final String MOTIVO_BLOQUEO = ReglasElegibilidadLiquidacion.MOTIVO_BLOQUEO;

    private final ReglasElegibilidadLiquidacion reglas;

    private final NovedadCrossSlice novedadCrossSlice;

    public LiquidacionIndividualReglas(LiquidacionCrossSlice liquidacionCrossSlice,
                                       ControlCrossSlice controlCrossSlice,
                                       PersonaCrossSlice personaCrossSlice,
                                       CargoLiquidacionCrossSlice cargoLiquidacionCrossSlice,
                                       CargoClaseCrossSlice cargoClaseCrossSlice,
                                       NovedadCrossSlice novedadCrossSlice) {
        this.reglas = new ReglasElegibilidadLiquidacion(liquidacionCrossSlice, controlCrossSlice,
                personaCrossSlice, cargoLiquidacionCrossSlice, cargoClaseCrossSlice, novedadCrossSlice);
        this.novedadCrossSlice = novedadCrossSlice;
    }

    public boolean estaBloqueada(Optional<LiquidacionView> liquidacion) {
        return reglas.estaBloqueada(liquidacion);
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
        return reglas.bloqueo(liquidacion, forzar).map(this::toResultado);
    }

    /** RN-03: elegibilidad de la persona, con la misma decisión que el motor legado. */
    public Optional<ResultadoProcesoIndividual> validarPersona(Long legajoId, Integer anho, Integer mes) {
        return reglas.validarPersona(legajoId, anho, mes).map(this::toResultado);
    }

    /** RN-04: sólo se liquida si hay cargos activos, cargos con clase o novedades especiales. */
    public boolean periodoLiquidable(Long legajoId, Integer anho, Integer mes,
                                     java.util.List<NovedadView> novedades) {
        return reglas.periodoLiquidable(legajoId, anho, mes, novedades);
    }

    /** RN-02: sin control del período no se puede liquidar ni generar cargos. */
    public boolean hayControl(Integer anho, Integer mes) {
        return reglas.hayControl(anho, mes);
    }

    public Optional<NovedadView> buscarNovedad(Long legajoId, Integer anho, Integer mes, Integer codigoId) {
        return novedadCrossSlice.porClaveSinDependencia(legajoId, anho, mes, codigoId);
    }

    private ResultadoProcesoIndividual toResultado(Inelegibilidad inelegibilidad) {
        return ResultadoProcesoIndividual.de(estado(inelegibilidad.motivo()), inelegibilidad.mensaje());
    }

    private EstadoProcesoIndividual estado(MotivoInelegibilidad motivo) {
        return switch (motivo) {
            case BLOQUEADA -> EstadoProcesoIndividual.BLOQUEADA;
            case PERSONA_INEXISTENTE -> EstadoProcesoIndividual.PERSONA_INEXISTENTE;
            case SIN_LIQUIDACION_ANTERIOR -> EstadoProcesoIndividual.SIN_LIQUIDACION_ANTERIOR;
            case PERSONA_NO_LIQUIDABLE -> EstadoProcesoIndividual.PERSONA_NO_LIQUIDABLE;
        };
    }
}
