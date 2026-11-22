package um.haberes.core.hexagonal.etec.presentismo_horas.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.etec.presentismo_horas.domain.model.EtecPresentismoHoras;

public interface EtecPresentismoHorasRepository {

    EtecPresentismoHoras save(EtecPresentismoHoras presentismo);

    Optional<EtecPresentismoHoras> findById(Long presentismoHorasId);

    Optional<EtecPresentismoHoras> findByLegajoIdAndPeriodo(Long legajoId, Integer anho, Integer mes);

    List<EtecPresentismoHoras> findAllByPeriodo(Integer anho, Integer mes);

    boolean existsByLegajoIdAndPeriodo(Long legajoId, Integer anho, Integer mes);

    void deleteById(Long presentismoHorasId);
}
