package um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;

public interface GetCargosNoDocentesByLegajoUseCase {

    List<Cargo> getCargosNoDocentesByLegajo(Long legajoId, Integer anho, Integer mes);
}
