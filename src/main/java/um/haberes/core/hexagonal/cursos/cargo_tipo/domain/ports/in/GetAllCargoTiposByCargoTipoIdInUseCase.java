package um.haberes.core.hexagonal.cursos.cargo_tipo.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.cursos.cargo_tipo.domain.model.CargoTipo;

public interface GetAllCargoTiposByCargoTipoIdInUseCase {

    List<CargoTipo> getAllCargoTiposByCargoTipoIdIn(List<Integer> cargoTipoIds);
}
