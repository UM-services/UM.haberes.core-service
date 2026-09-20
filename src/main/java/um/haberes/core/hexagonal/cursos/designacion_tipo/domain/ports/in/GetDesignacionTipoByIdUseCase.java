package um.haberes.core.hexagonal.cursos.designacion_tipo.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.model.DesignacionTipo;

public interface GetDesignacionTipoByIdUseCase {

    Optional<DesignacionTipo> getDesignacionTipoById(Integer designacionTipoId);
}
