package um.haberes.core.hexagonal.etec.arraigo_escala.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.etec.arraigo_escala.domain.model.EscalaArraigoEtec;

public interface GetEscalaArraigoEtecUseCase {

    List<EscalaArraigoEtec> findAll();
}
