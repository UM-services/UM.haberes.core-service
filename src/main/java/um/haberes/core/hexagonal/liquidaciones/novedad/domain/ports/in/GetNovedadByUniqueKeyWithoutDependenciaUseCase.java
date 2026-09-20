package um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;

public interface GetNovedadByUniqueKeyWithoutDependenciaUseCase {

    Optional<Novedad> getNovedadByUniqueKeyWithoutDependencia(Long legajoId, Integer anho, Integer mes,
            Integer codigoId);
}
