package um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;

public interface GetCargosByLegajoUseCase {

    List<Cargo> getCargosByLegajo(Long legajoId);
}
