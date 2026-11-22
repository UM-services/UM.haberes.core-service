package um.haberes.core.hexagonal.etec.presentismo_horas.domain.ports.in;

import um.haberes.core.hexagonal.etec.presentismo_horas.domain.model.EtecPresentismoHoras;

public interface CreateEtecPresentismoHorasUseCase {

    EtecPresentismoHoras createEtecPresentismoHoras(EtecPresentismoHoras presentismo);
}
