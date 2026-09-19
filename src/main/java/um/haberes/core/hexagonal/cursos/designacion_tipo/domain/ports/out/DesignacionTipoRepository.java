package um.haberes.core.hexagonal.cursos.designacion_tipo.domain.ports.out;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.model.DesignacionTipo;

public interface DesignacionTipoRepository {

    List<DesignacionTipo> findAll();

    Optional<DesignacionTipo> findByDesignacionTipoId(Integer designacionTipoId);

    Optional<DesignacionTipo> findFirstByHorasSemanalesGreaterThanEqual(BigDecimal horasSemanales);
}
