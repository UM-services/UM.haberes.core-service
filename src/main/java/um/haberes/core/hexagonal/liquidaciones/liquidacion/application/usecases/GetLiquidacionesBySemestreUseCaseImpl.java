package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesBySemestreUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetLiquidacionesBySemestreUseCaseImpl implements GetLiquidacionesBySemestreUseCase {

    private final LiquidacionRepository liquidacionRepository;

    @Override
    public List<Liquidacion> getLiquidacionesBySemestre(Integer anho, Integer semestre, Integer limit) {
        Integer mesDesde = (semestre - 1) * 6 + 1;
        Integer mesHasta = semestre * 6;
        return liquidacionRepository.findAllByAnhoAndMesBetweenOrderByLegajoId(anho, mesDesde, mesHasta, limit);
    }
}
