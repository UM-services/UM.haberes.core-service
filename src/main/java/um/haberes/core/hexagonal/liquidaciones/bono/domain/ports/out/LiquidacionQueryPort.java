package um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out;

import java.util.List;

public interface LiquidacionQueryPort {

    boolean existsByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    List<Long> findLegajoIdsByDependencia(Integer dependenciaId, Integer anho, Integer mes, String salida);
}
