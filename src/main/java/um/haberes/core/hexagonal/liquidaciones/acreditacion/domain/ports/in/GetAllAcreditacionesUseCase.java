package um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;

public interface GetAllAcreditacionesUseCase {

    List<Acreditacion> getAllAcreditaciones();
}
