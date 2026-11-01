package um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.LiquidacionVersion;

public interface LiquidacionVersionRepository {

    LiquidacionVersion save(LiquidacionVersion liquidacionVersion);

    List<LiquidacionVersion> saveAll(List<LiquidacionVersion> liquidacionVersions);
}
