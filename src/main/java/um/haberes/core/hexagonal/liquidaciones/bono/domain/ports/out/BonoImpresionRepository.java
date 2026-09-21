package um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.BonoImpresion;

public interface BonoImpresionRepository {

    BonoImpresion save(BonoImpresion bonoImpresion);

    List<BonoImpresion> findAllByLegajoIdAndAnhoAndMesOrderByFechaDesc(Long legajoId, Integer anho, Integer mes);
}
