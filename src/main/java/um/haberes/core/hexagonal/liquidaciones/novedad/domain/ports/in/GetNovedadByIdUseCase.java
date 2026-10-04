package um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;

public interface GetNovedadByIdUseCase {

    Optional<Novedad> getNovedadById(Long novedadId);
}
