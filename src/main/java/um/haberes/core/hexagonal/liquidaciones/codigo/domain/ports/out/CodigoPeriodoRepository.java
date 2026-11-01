package um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.out;

import java.util.List;

public interface CodigoPeriodoRepository {

    List<Integer> findCodigoIdsByPeriodo(Integer anho, Integer mes);
}
