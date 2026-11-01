package um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;

public interface GetNovedadesByLegajoAndCodigoUseCase {

    List<Novedad> getNovedadesByLegajoAndCodigo(Long legajoId, Integer anho, Integer mes, Integer codigoId);
}
