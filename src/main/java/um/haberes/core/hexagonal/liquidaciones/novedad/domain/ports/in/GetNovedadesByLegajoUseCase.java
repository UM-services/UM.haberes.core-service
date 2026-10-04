package um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;

public interface GetNovedadesByLegajoUseCase {

    List<Novedad> getNovedadesByLegajo(Long legajoId, Integer anho, Integer mes);
}
