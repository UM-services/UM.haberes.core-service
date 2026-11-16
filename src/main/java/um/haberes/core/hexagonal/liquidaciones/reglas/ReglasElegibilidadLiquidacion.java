package um.haberes.core.hexagonal.liquidaciones.reglas;

import java.util.List;
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
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.CodigoLiquidacion;

/**
 * Reglas de elegibilidad compartidas por la liquidación individual y la general.
 *
 * <p>Devuelve motivos neutrales ({@link MotivoInelegibilidad}) para que cada slice los traduzca a
 * su propio vocabulario sin acoplarse al otro. No modifica el motor de liquidación: lo valida
 * antes y lo relee después.</p>
 */
@Component
@RequiredArgsConstructor
public class ReglasElegibilidadLiquidacion {

    public static final String MOTIVO_BLOQUEO =
            "La liquidación del período está bloqueada. Active «Forzar» para modificarla.";

    private static final byte BLOQUEADO = 1;
    private static final int ESTADO_BAJA = 9;
    private static final int ESTADO_ACTIVO = 1;
    private static final String LIQUIDA_SI = "S";
    private static final String LIQUIDA_NO = "N";

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

    /** RN-01: la liquidación bloqueada sólo admite cambios con {@code forzar}. */
    public Optional<Inelegibilidad> bloqueo(Optional<LiquidacionView> liquidacion, boolean forzar) {
        if (!forzar && estaBloqueada(liquidacion)) {
            return Optional.of(Inelegibilidad.de(MotivoInelegibilidad.BLOQUEADA, MOTIVO_BLOQUEO));
        }
        return Optional.empty();
    }

    /**
     * RN-03: elegibilidad de la persona, con la misma decisión que el motor legado.
     *
     * <p>La corrección de {@code liquida} a {@code "N"} también se replica: es un dato que el
     * proceso dejaba corregido al detectar el caso.</p>
     */
    public Optional<Inelegibilidad> validarPersona(Long legajoId, Integer anho, Integer mes) {
        Optional<PersonaView> persona = personaCrossSlice.porLegajo(legajoId);
        if (persona.isEmpty()) {
            return Optional.of(Inelegibilidad.de(MotivoInelegibilidad.PERSONA_INEXISTENTE,
                    "El legajo no existe."));
        }
        PersonaView personaView = persona.get();
        if (esBajaNoLiquida(personaView)) {
            Optional<LiquidacionView> anterior = liquidacionCrossSlice.porPeriodoAnterior(legajoId, anho, mes);
            if (anterior.map(this::esLiquidadaActiva).orElse(false)) {
                return Optional.of(Inelegibilidad.de(MotivoInelegibilidad.SIN_LIQUIDACION_ANTERIOR,
                        "El legajo no registra liquidación anterior válida."));
            }
        }
        if (!esActivoLiquida(personaView)) {
            Optional<LiquidacionView> anterior = liquidacionCrossSlice.porPeriodoAnterior(legajoId, anho, mes);
            if (anterior.map(this::esBajaLiquidada).orElse(false)) {
                personaCrossSlice.marcarNoLiquida(legajoId);
                return Optional.of(Inelegibilidad.de(MotivoInelegibilidad.PERSONA_NO_LIQUIDABLE,
                        "El legajo no está en estado liquidable."));
            }
        }
        return Optional.empty();
    }

    /** RN-04: sólo se liquida si hay cargos activos, cargos con clase o novedades especiales. */
    public boolean periodoLiquidable(Long legajoId, Integer anho, Integer mes, List<NovedadView> novedades) {
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

    public List<NovedadView> novedades(Long legajoId, Integer anho, Integer mes) {
        return novedadCrossSlice.porLegajo(legajoId, anho, mes);
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
