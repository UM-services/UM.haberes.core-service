package um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.model.LegajoContabilidad;

public interface GetLegajoContabilidadByUniqueUseCase {

    Optional<LegajoContabilidad> getLegajoContabilidadByUnique(Long legajoId, Integer anho, Integer mes);
}
