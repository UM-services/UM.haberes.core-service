package um.haberes.core.hexagonal.liquidaciones.cargo.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.in.GetCargoByIdUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.out.CargoRepository;

@Component
@RequiredArgsConstructor
public class GetCargoByIdUseCaseImpl implements GetCargoByIdUseCase {

    private final CargoRepository cargoRepository;

    @Override
    public Optional<Cargo> getCargoById(Long cargoId) {
        return cargoRepository.findByCargoId(cargoId);
    }
}
