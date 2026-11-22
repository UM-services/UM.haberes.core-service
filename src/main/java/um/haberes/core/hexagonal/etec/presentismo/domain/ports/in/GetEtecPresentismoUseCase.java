package um.haberes.core.hexagonal.etec.presentismo.domain.ports.in;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import um.haberes.core.hexagonal.etec.presentismo.domain.model.EtecPresentismo;

public interface GetEtecPresentismoUseCase {

    Optional<EtecPresentismo> getById(Long presentismoId);

    Optional<EtecPresentismo> getByAsignacionCargoIdAndPeriodo(Long asignacionCargoId, Integer anho, Integer mes);

    List<EtecPresentismo> getByLegajoIdAndPeriodo(Long legajoId, Integer anho, Integer mes);

    List<EtecPresentismo> getByPeriodo(Integer anho, Integer mes);

    Set<Integer> getCategoriasSinPresentismo(Long legajoId, Integer anho, Integer mes);
}
