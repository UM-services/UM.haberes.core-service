package um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.model.CategoriaImputacion;

public interface GetCategoriaImputacionByIdUseCase {

    Optional<CategoriaImputacion> getCategoriaImputacionById(Long categoriaImputacionId);
}
