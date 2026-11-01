package um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out;

import java.util.List;

public interface CodigoGrupoRepository {

    List<Integer> findCodigoIdsByRemunerativo(Byte remunerativo);
}
