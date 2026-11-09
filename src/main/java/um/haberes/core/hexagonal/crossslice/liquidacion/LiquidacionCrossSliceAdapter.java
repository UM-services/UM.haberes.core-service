package um.haberes.core.hexagonal.crossslice.liquidacion;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.application.exception.LiquidacionException;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.application.service.LiquidacionService;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;

/** Adaptador cross-slice de liquidación. */
@Component
@RequiredArgsConstructor
public class LiquidacionCrossSliceAdapter implements LiquidacionCrossSlice {

    private final LiquidacionService liquidacionService;

    @Override
    public Optional<LiquidacionView> porClaveUnica(Long legajoId, Integer anho, Integer mes) {
        try {
            return Optional.of(toView(liquidacionService.getLiquidacionByUniqueKey(legajoId, anho, mes)));
        } catch (LiquidacionException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<LiquidacionView> porPeriodoAnterior(Long legajoId, Integer anho, Integer mes) {
        try {
            return Optional.of(toView(liquidacionService.getLiquidacionByPeriodoAnterior(legajoId, anho, mes)));
        } catch (LiquidacionException e) {
            return Optional.empty();
        }
    }

    private LiquidacionView toView(Liquidacion liquidacion) {
        if (liquidacion == null) {
            return null;
        }
        return new LiquidacionView(liquidacion.getLiquidacionId(), liquidacion.getAnho(), liquidacion.getMes(),
                liquidacion.getBloqueado(), liquidacion.getEstado(), liquidacion.getLiquida(),
                liquidacion.getTotalRemunerativo(), liquidacion.getTotalNoRemunerativo(),
                liquidacion.getTotalDeduccion(), liquidacion.getTotalNeto(),
                liquidacion.getFechaLiquidacion(), liquidacion.getFechaAcreditacion(),
                liquidacion.getDependenciaId(), liquidacion.getSalida());
    }
}
