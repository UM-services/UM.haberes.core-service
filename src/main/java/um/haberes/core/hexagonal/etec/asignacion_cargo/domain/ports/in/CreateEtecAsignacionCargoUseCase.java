package um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.in;

import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.model.EtecAsignacionCargo;

public interface CreateEtecAsignacionCargoUseCase {

    EtecAsignacionCargo createEtecAsignacionCargo(EtecAsignacionCargo asignacion);
}
