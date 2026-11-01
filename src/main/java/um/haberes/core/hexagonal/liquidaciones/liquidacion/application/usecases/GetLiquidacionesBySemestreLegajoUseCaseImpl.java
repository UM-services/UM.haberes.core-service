package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesBySemestreLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetLiquidacionesBySemestreLegajoUseCaseImpl implements GetLiquidacionesBySemestreLegajoUseCase {

    private final LiquidacionRepository liquidacionRepository;

    @Override
    public List<Liquidacion> getLiquidacionesBySemestreLegajo(Integer anho, Integer semestre, Long legajoId, Integer limit) {
        Integer mesDesde = (semestre - 1) * 6 + 1;
        Integer mesHasta = semestre * 6;
        return liquidacionRepository.findAllByAnhoAndMesBetweenAndLegajoId(anho, mesDesde, mesHasta, legajoId, limit);
    }
}
