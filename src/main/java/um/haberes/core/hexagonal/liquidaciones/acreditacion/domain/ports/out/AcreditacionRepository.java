package um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;

public interface AcreditacionRepository {

    List<Acreditacion> findAll();

    Optional<Acreditacion> findByAcreditacionId(Long acreditacionId);

    Optional<Acreditacion> findByAnhoAndMes(Integer anho, Integer mes);

    Acreditacion create(Acreditacion acreditacion);

    Optional<Acreditacion> update(Long acreditacionId, Acreditacion acreditacion);

    void deleteById(Long acreditacionId);
}
