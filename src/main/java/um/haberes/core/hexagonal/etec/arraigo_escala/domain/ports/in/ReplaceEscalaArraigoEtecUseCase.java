package um.haberes.core.hexagonal.etec.arraigo_escala.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.etec.arraigo_escala.domain.model.EscalaArraigoEtec;

/** Reemplaza la escala completa (edición desde Administrador). */
public interface ReplaceEscalaArraigoEtecUseCase {

    List<EscalaArraigoEtec> reemplazar(List<EscalaArraigoEtec> tramos);
}
