package um.haberes.core.hexagonal.etec.presentismo.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.etec.presentismo.domain.model.EtecPresentismo;

public interface UpdateEtecPresentismoUseCase {

    Optional<EtecPresentismo> updateEtecPresentismo(Long presentismoId, EtecPresentismo presentismo);
}
