package um.haberes.core.hexagonal.etec.hora_escala.domain.ports.out;

import java.util.List;

import um.haberes.core.hexagonal.etec.hora_escala.domain.model.EscalaHoraEtec;

/** Persistencia de la escala de horas de clase ETEC de cada período. */
public interface EscalaHoraEtecRepository {

    /** Tramos del período ordenados por {@code horasDesde} ascendente. */
    List<EscalaHoraEtec> findAllByControlEtecId(Long controlEtecId);

    void deleteAllByControlEtecId(Long controlEtecId);

    List<EscalaHoraEtec> saveAll(List<EscalaHoraEtec> tramos);
}
