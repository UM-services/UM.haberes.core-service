package um.haberes.core.hexagonal.cursos.designacion_tipo.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.model.DesignacionTipo;

public interface GetAllDesignacionTiposUseCase {

    List<DesignacionTipo> getAllDesignacionTipos();
}
