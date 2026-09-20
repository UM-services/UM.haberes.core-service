package um.haberes.core.hexagonal.cursos.designacion_tipo.domain.ports.in;

import java.math.BigDecimal;
import java.util.Optional;

import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.model.DesignacionTipo;

public interface FindDesignacionTipoByHorasSemanalesUseCase {

    Optional<DesignacionTipo> findDesignacionTipoByHorasSemanales(BigDecimal horasSemanales);
}
