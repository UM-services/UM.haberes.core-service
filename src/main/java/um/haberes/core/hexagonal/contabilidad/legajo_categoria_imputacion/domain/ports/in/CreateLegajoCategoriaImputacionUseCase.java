package um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.ports.in;

import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.model.LegajoCategoriaImputacion;

public interface CreateLegajoCategoriaImputacionUseCase {

    LegajoCategoriaImputacion createLegajoCategoriaImputacion(
            LegajoCategoriaImputacion legajoCategoriaImputacion);
}
