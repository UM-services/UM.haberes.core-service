package um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;

public interface CodigoRepository {

    List<Codigo> findAll();

    /**
     * Versión del catálogo para usar como clave de caché. Cambia ante altas, bajas o
     * modificaciones, incluso si el cambio se hizo por SQL directo.
     */
    String version();

    List<Codigo> findAllByCodigoIdIn(List<Integer> codigoIds);

    List<Codigo> findAllByTransferible(Byte transferible);

    Optional<Codigo> findByCodigoId(Integer codigoId);

    Optional<Codigo> findLast();

    Codigo save(Codigo codigo);

    List<Codigo> saveAll(List<Codigo> codigos);

    void deleteByCodigoId(Integer codigoId);
}
