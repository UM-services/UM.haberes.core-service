package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in;

import java.util.List;

public interface DeleteCargosNotInByPeriodoUseCase {

    void deleteCargosNotInByPeriodo(List<Long> legajoIds, Integer anho, Integer mes);
}
