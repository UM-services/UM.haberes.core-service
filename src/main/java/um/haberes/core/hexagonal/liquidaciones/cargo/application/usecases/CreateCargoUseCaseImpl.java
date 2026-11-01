package um.haberes.core.hexagonal.liquidaciones.cargo.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.in.CreateCargoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.out.CargoRepository;

@Component
@RequiredArgsConstructor
public class CreateCargoUseCaseImpl implements CreateCargoUseCase {

    private final CargoRepository cargoRepository;

    @Override
    public Cargo createCargo(Cargo cargo) {
        return cargoRepository.save(cargo);
    }
}
