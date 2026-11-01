package um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;

public interface GetNovedadByUniqueKeyUseCase {

    Optional<Novedad> getNovedadByUniqueKey(Long legajoId, Integer anho, Integer mes, Integer codigoId,
            Integer dependenciaId);
}
