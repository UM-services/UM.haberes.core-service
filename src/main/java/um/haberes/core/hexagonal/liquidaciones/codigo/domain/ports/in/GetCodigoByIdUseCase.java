package um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;

public interface GetCodigoByIdUseCase {

    Optional<Codigo> getCodigoById(Integer codigoId);
}
