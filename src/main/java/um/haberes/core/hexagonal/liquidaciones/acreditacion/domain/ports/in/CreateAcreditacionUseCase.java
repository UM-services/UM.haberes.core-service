package um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;

public interface CreateAcreditacionUseCase {

    Acreditacion createAcreditacion(Acreditacion acreditacion);
}
