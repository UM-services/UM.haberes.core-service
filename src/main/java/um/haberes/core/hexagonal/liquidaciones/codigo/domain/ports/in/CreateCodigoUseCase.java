package um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;

public interface CreateCodigoUseCase {

    Codigo createCodigo(Codigo codigo);
}
