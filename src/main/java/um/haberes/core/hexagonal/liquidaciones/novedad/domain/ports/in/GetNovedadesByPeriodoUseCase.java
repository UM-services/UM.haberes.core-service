package um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;

public interface GetNovedadesByPeriodoUseCase {

    List<Novedad> getNovedadesByPeriodo(Integer anho, Integer mes);
}
