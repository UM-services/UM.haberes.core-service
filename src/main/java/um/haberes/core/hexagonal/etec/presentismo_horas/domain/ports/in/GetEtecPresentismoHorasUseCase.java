package um.haberes.core.hexagonal.etec.presentismo_horas.domain.ports.in;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.etec.presentismo_horas.domain.model.EtecPresentismoHoras;

public interface GetEtecPresentismoHorasUseCase {

    Optional<EtecPresentismoHoras> getById(Long presentismoHorasId);

    Optional<EtecPresentismoHoras> getByLegajoIdAndPeriodo(Long legajoId, Integer anho, Integer mes);

    List<EtecPresentismoHoras> getByPeriodo(Integer anho, Integer mes);

    boolean tieneCargosHoras(Long legajoId, Integer anho, Integer mes);
}
