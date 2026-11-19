package um.haberes.core.hexagonal.etec.hora_escala.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.etec.hora_escala.domain.model.EscalaHoraEtec;

public interface GetEscalaHoraEtecUseCase {

    /** Tramos configurados para el período; lista vacía si el período no tiene control ETEC. */
    List<EscalaHoraEtec> findByPeriodo(Integer anho, Integer mes);
}
