package um.haberes.core.hexagonal.etec.control_etec.domain.ports.in;

import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;

public interface SaveControlEtecUseCase {

    /**
     * Alta o actualización por período: si el período ya tiene control ETEC se actualiza el
     * registro existente y si no se crea uno nuevo.
     */
    ControlEtec save(Integer anho, Integer mes, ControlEtec datos);
}
