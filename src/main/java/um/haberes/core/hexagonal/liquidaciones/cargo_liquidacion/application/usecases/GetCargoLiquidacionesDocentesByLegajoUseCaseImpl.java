package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargoLiquidacionesDocentesByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetCargoLiquidacionesDocentesByLegajoUseCaseImpl implements GetCargoLiquidacionesDocentesByLegajoUseCase {

    private static final Byte DOCENTE = 1;

    private final CargoLiquidacionRepository cargoLiquidacionRepository;

    @Override
    public List<CargoLiquidacion> getCargosDocentesByLegajo(Long legajoId, Integer anho, Integer mes) {
        return cargoLiquidacionRepository.findAllByLegajoIdAndAnhoAndMesAndCategoriaDocente(legajoId, anho, mes,
                DOCENTE);
    }
}
