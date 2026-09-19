package um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.model.CategoriaImputacion;

public interface UpdateCategoriaImputacionUseCase {

    Optional<CategoriaImputacion> updateCategoriaImputacion(Long categoriaImputacionId,
            CategoriaImputacion categoriaImputacion);
}
