package um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.in;

import java.time.OffsetDateTime;
import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;

public interface UpdateAcreditacionLimiteNovedadesUseCase {

    Optional<Acreditacion> updateLimiteNovedades(Long acreditacionId, OffsetDateTime limiteNovedades);
}
