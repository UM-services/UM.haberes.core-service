package um.haberes.core.hexagonal.etec.control_etec.domain.ports.out;

import java.util.Optional;

import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;

public interface ControlEtecRepository {

    Optional<ControlEtec> findByPeriodo(Integer anho, Integer mes);

    boolean existsByPeriodo(Integer anho, Integer mes);

    ControlEtec save(ControlEtec controlEtec);
}
