package um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.model.EtecAsignacionCargo;

public interface UpdateEtecAsignacionCargoUseCase {

    Optional<EtecAsignacionCargo> updateEtecAsignacionCargo(Long asignacionCargoId, EtecAsignacionCargo asignacion);
}
