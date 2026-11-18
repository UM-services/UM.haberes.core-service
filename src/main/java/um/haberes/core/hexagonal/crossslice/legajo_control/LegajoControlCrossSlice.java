package um.haberes.core.hexagonal.crossslice.legajo_control;

import java.util.List;

import um.haberes.core.hexagonal.crossslice.legajo_control.model.LegajoControlView;

/**
 * Contrato de cross-slice para el estado de control de los legajos en un período.
 *
 * <p>Lo usa la liquidación general para no depender del paquete legacy
 * {@code um.haberes.core.service.LegajoControlService}.</p>
 */
public interface LegajoControlCrossSlice {

    List<LegajoControlView> porPeriodo(Integer anho, Integer mes);

    /** Marca todos los legajos del período como no liquidados ({@code liquidado = 0}). */
    void desmarcarPeriodo(Integer anho, Integer mes);
}
