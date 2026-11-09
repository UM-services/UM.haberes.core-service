package um.haberes.core.hexagonal.crossslice.control;

import java.util.Optional;

import um.haberes.core.hexagonal.crossslice.control.model.ControlView;

/** Contrato de cross-slice para el control de un período. */
public interface ControlCrossSlice {

    Optional<ControlView> porPeriodo(Integer anho, Integer mes);
}
