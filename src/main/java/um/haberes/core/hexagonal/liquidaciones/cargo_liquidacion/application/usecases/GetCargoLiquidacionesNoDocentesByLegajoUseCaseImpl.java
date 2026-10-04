package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargoLiquidacionesNoDocentesByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetCargoLiquidacionesNoDocentesByLegajoUseCaseImpl implements GetCargoLiquidacionesNoDocentesByLegajoUseCase {

    private static final Byte NO_DOCENTE = 1;

    private final CargoLiquidacionRepository cargoLiquidacionRepository;

    @Override
    public List<CargoLiquidacion> getCargosNoDocentesByLegajo(Long legajoId, Integer anho, Integer mes) {
        return cargoLiquidacionRepository.findAllByLegajoIdAndAnhoAndMesAndCategoriaNoDocente(legajoId, anho, mes,
                NO_DOCENTE);
    }
}
