package um.haberes.core.hexagonal.etec.presentismo.domain.ports.in;

import um.haberes.core.hexagonal.etec.presentismo.domain.model.EtecPresentismo;

public interface CreateEtecPresentismoUseCase {

    EtecPresentismo createEtecPresentismo(EtecPresentismo presentismo);
}
