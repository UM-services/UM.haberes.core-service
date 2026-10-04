package um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out;

import java.util.List;

public interface DesignacionRepository {

    List<Integer> findCategoriaIdsAsignadas();
}
