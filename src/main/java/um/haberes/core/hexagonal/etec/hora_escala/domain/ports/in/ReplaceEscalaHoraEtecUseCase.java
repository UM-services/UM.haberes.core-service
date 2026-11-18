package um.haberes.core.hexagonal.etec.hora_escala.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.etec.hora_escala.domain.model.EscalaHoraEtec;

/** Reemplaza la escala de horas de clase ETEC de un período. */
public interface ReplaceEscalaHoraEtecUseCase {

    List<EscalaHoraEtec> reemplazar(Integer anho, Integer mes, List<EscalaHoraEtec> tramos);
}
