package um.haberes.core.hexagonal.etec.control_etec.domain.ports.in;

import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;

public interface CopiarControlEtecUseCase {

    /**
     * Crea el control ETEC del período indicado copiando todos los parámetros del mes anterior.
     */
    ControlEtec copiar(Integer anho, Integer mes);
}
