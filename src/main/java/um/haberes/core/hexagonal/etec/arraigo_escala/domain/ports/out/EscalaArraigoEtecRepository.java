package um.haberes.core.hexagonal.etec.arraigo_escala.domain.ports.out;

import java.util.List;

import um.haberes.core.hexagonal.etec.arraigo_escala.domain.model.EscalaArraigoEtec;

/** Persistencia de la escala global de arraigo ETEC. */
public interface EscalaArraigoEtecRepository {

    /** Tramos ordenados por {@code aniosMinimos} ascendente. */
    List<EscalaArraigoEtec> findAll();

    void deleteAll();

    List<EscalaArraigoEtec> saveAll(List<EscalaArraigoEtec> tramos);
}
