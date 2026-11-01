package um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.ports.in;

import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.model.CategoriaImputacion;

public interface CreateCategoriaImputacionUseCase {

    CategoriaImputacion createCategoriaImputacion(CategoriaImputacion categoriaImputacion);
}
