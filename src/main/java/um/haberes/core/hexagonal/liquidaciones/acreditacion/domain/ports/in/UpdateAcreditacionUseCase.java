package um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;

public interface UpdateAcreditacionUseCase {

    Optional<Acreditacion> updateAcreditacion(Long acreditacionId, Acreditacion acreditacion);
}
