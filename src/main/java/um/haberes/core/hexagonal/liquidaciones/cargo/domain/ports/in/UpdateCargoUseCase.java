package um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;

public interface UpdateCargoUseCase {

    Optional<Cargo> updateCargo(Long cargoId, Cargo cargo);
}
