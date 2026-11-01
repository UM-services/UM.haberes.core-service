package um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out;

import java.math.BigDecimal;
import java.util.List;

public interface CargoLiquidacionQueryPort {

    List<BigDecimal> findBasicoCargosDocentesByLegajoAndPeriodo(Long legajoId, Integer anho, Integer mes);

    List<BigDecimal> findBasicoCargosNoDocentesByLegajoAndPeriodo(Long legajoId, Integer anho, Integer mes);
}
