package um.haberes.core.hexagonal.cursos.cargo_tipo.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.cursos.cargo_tipo.domain.model.CargoTipo;

public interface CargoTipoRepository {

    List<CargoTipo> findAll();

    List<CargoTipo> findAllByCargoTipoIdIn(List<Integer> cargoTipoIds);

    Optional<CargoTipo> findByCargoTipoId(Integer cargoTipoId);
}
