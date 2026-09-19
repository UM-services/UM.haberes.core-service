package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionByPeriodoAnteriorUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetLiquidacionByPeriodoAnteriorUseCaseImpl implements GetLiquidacionByPeriodoAnteriorUseCase {

    private final LiquidacionRepository liquidacionRepository;

    @Override
    public Optional<Liquidacion> getLiquidacionByPeriodoAnterior(Long legajoId, Integer anho, Integer mes) {
        return liquidacionRepository.findByLegajoIdAndAnhoAndMes(legajoId, prevAnho(anho, mes), prevMes(anho, mes));
    }

    public static Integer prevAnho(Integer anho, Integer mes) {
        return mes == 1 ? anho - 1 : anho;
    }

    public static Integer prevMes(Integer anho, Integer mes) {
        return mes == 1 ? 12 : mes - 1;
    }
}
