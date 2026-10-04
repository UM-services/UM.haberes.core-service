package um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;

public interface GetCargosDocentesByLegajoUseCase {

    List<Cargo> getCargosDocentesByLegajo(Long legajoId, Integer anho, Integer mes);
}
