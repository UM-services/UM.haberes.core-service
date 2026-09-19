package um.haberes.core.hexagonal.cursos.cargo_tipo.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.cursos.cargo_tipo.domain.model.CargoTipo;

public interface GetCargoTipoByIdUseCase {

    Optional<CargoTipo> getCargoTipoById(Integer cargoTipoId);
}
