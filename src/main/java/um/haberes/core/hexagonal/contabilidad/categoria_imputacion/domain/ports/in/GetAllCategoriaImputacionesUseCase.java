package um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.model.CategoriaImputacion;

public interface GetAllCategoriaImputacionesUseCase {

    List<CategoriaImputacion> getAllCategoriaImputaciones();
}
