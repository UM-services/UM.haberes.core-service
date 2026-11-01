package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesByAnhoAndMesBetweenUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetLiquidacionesByAnhoAndMesBetweenUseCaseImpl implements GetLiquidacionesByAnhoAndMesBetweenUseCase {

    private final LiquidacionRepository liquidacionRepository;

    @Override
    public List<Liquidacion> getLiquidacionesByAnhoAndMesBetween(Integer anho, Integer mesDesde, Integer mesHasta) {
        return liquidacionRepository.findAllByAnhoAndMesBetween(anho, mesDesde, mesHasta);
    }
}
