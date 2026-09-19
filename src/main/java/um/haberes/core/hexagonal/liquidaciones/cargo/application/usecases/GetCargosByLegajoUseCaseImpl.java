package um.haberes.core.hexagonal.liquidaciones.cargo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.in.GetCargosByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.out.CargoRepository;

@Component
@RequiredArgsConstructor
public class GetCargosByLegajoUseCaseImpl implements GetCargosByLegajoUseCase {

    private final CargoRepository cargoRepository;

    @Override
    public List<Cargo> getCargosByLegajo(Long legajoId) {
        return cargoRepository.findAllByLegajoIdOrderByCargoIdDesc(legajoId);
    }
}
