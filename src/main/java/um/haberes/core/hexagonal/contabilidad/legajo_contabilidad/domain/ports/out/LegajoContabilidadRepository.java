package um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.model.LegajoContabilidad;

public interface LegajoContabilidadRepository {

    List<LegajoContabilidad> findAllDiferenciaByPeriodo(Integer anho, Integer mes);

    Optional<LegajoContabilidad> findByUnique(Long legajoId, Integer anho, Integer mes);

    LegajoContabilidad create(LegajoContabilidad legajoContabilidad);

    Optional<LegajoContabilidad> update(Long legajoContabilidadId, LegajoContabilidad legajoContabilidad);

    void deleteById(Long legajoContabilidadId);
}
