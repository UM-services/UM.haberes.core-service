package um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.model.CodigoImputacion;

public interface GetAllCodigoImputacionesUseCase {

    List<CodigoImputacion> getAllCodigoImputaciones();
}
