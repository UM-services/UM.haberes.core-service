package um.haberes.core.hexagonal.etec.presentismo.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.etec.presentismo.domain.model.EtecPresentismo;

public interface EtecPresentismoRepository {

    EtecPresentismo save(EtecPresentismo presentismo);

    Optional<EtecPresentismo> findById(Long presentismoId);

    Optional<EtecPresentismo> findByAsignacionCargoIdAndPeriodo(Long asignacionCargoId, Integer anho, Integer mes);

    List<EtecPresentismo> findAllByLegajoIdAndPeriodo(Long legajoId, Integer anho, Integer mes);

    List<EtecPresentismo> findAllByPeriodo(Integer anho, Integer mes);

    boolean existsByAsignacionCargoIdAndPeriodo(Long asignacionCargoId, Integer anho, Integer mes);

    void deleteById(Long presentismoId);

    void deleteAllByAsignacionCargoId(Long asignacionCargoId);
}
