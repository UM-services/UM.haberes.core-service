package um.haberes.core.hexagonal.crossslice.legajo_control;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.crossslice.legajo_control.model.LegajoControlView;
import um.haberes.core.model.LegajoControlEntity;
import um.haberes.core.service.LegajoControlService;

/** Adaptador cross-slice del control de legajos. */
@Component
@RequiredArgsConstructor
public class LegajoControlCrossSliceAdapter implements LegajoControlCrossSlice {

    private static final byte LIQUIDADO = 0;

    private final LegajoControlService legajoControlService;

    @Override
    public List<LegajoControlView> porPeriodo(Integer anho, Integer mes) {
        return legajoControlService.findAllByPeriodo(anho, mes).stream()
                .map(this::toView)
                .toList();
    }

    @Override
    public void desmarcarPeriodo(Integer anho, Integer mes) {
        List<LegajoControlEntity> legajos = legajoControlService.findAllByPeriodo(anho, mes);
        legajos.forEach(legajo -> legajo.setLiquidado(LIQUIDADO));
        legajoControlService.saveAll(legajos);
    }

    private LegajoControlView toView(LegajoControlEntity legajoControl) {
        return new LegajoControlView(legajoControl.getLegajoControlId(), legajoControl.getLegajoId(),
                legajoControl.getAnho(), legajoControl.getMes(), legajoControl.getLiquidado(),
                legajoControl.getFusionado(), legajoControl.getBonoEnviado());
    }
}
