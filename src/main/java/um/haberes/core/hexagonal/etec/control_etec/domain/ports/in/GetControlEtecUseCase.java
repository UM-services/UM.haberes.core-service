package um.haberes.core.hexagonal.etec.control_etec.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;

public interface GetControlEtecUseCase {

    Optional<ControlEtec> findByPeriodo(Integer anho, Integer mes);
}
