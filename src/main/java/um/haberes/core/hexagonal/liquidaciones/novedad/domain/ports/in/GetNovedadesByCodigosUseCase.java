package um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in;

import java.util.Collection;
import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;

public interface GetNovedadesByCodigosUseCase {

    List<Novedad> getNovedadesByCodigos(Collection<Integer> codigoIds, Integer anho, Integer mes);
}
