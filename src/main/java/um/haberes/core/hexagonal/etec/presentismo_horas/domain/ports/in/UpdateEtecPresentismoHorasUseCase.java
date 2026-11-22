package um.haberes.core.hexagonal.etec.presentismo_horas.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.etec.presentismo_horas.domain.model.EtecPresentismoHoras;

public interface UpdateEtecPresentismoHorasUseCase {

    Optional<EtecPresentismoHoras> updateEtecPresentismoHoras(
            Long presentismoHorasId, EtecPresentismoHoras presentismo);
}
