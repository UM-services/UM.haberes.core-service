package um.haberes.core.hexagonal.crossslice.control;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.exception.ControlException;
import um.haberes.core.hexagonal.crossslice.control.model.ControlView;
import um.haberes.core.model.ControlEntity;
import um.haberes.core.service.ControlService;

/** Adaptador cross-slice del control de período. */
@Component
@RequiredArgsConstructor
public class ControlCrossSliceAdapter implements ControlCrossSlice {

    private final ControlService controlService;

    @Override
    public Optional<ControlView> porPeriodo(Integer anho, Integer mes) {
        try {
            ControlEntity control = controlService.findByPeriodo(anho, mes);
            return Optional.of(new ControlView(control.getControlId(), control.getAnho(), control.getMes(),
                    control.getModoLiquidacionId()));
        } catch (ControlException e) {
            return Optional.empty();
        }
    }
}
