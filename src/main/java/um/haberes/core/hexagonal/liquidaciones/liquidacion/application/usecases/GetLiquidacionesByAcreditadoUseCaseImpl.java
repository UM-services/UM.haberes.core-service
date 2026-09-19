package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesByAcreditadoUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetLiquidacionesByAcreditadoUseCaseImpl implements GetLiquidacionesByAcreditadoUseCase {

    private final LiquidacionRepository liquidacionRepository;

    @Override
    public List<Liquidacion> getLiquidacionesByAcreditado(Integer anho, Integer mes) {
        return liquidacionRepository.findAllByAnhoAndMesAndFechaAcreditacionNotNull(anho, mes);
    }
}
