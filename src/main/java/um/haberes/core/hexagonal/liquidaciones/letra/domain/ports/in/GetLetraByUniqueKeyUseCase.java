package um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.letra.domain.model.Letra;

public interface GetLetraByUniqueKeyUseCase {

    Optional<Letra> getLetraByUniqueKey(Long legajoId, Integer anho, Integer mes);
}
