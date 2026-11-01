package um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;

public interface CreateCargoUseCase {

    Cargo createCargo(Cargo cargo);
}
