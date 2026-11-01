package um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out;

import java.util.Optional;

public interface PersonaQueryPort {

    Optional<Integer> findDependenciaIdByLegajoId(Long legajoId);
}
