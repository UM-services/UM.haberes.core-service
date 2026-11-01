package um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;

public interface CreateNovedadUseCase {

    Novedad createNovedad(Novedad novedad);
}
