package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesByDependenciaUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetLiquidacionesByDependenciaUseCaseImpl implements GetLiquidacionesByDependenciaUseCase {

    private final LiquidacionRepository liquidacionRepository;

    @Override
    public List<Liquidacion> getLiquidacionesByDependencia(Integer dependenciaId, Integer anho, Integer mes, String salida) {
        return liquidacionRepository.findAllByDependenciaIdAndAnhoAndMesAndSalida(dependenciaId, anho, mes, salida);
    }
}
