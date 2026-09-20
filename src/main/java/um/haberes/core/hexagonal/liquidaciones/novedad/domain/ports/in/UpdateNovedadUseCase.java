package um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;

public interface UpdateNovedadUseCase {

    Optional<Novedad> updateNovedad(Long novedadId, Novedad novedad);
}
