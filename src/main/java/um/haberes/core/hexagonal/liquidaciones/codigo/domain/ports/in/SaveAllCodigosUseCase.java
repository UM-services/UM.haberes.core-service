package um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;

public interface SaveAllCodigosUseCase {

    List<Codigo> saveAllCodigos(List<Codigo> codigos);
}
