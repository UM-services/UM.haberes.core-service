package um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.ports.out;

import java.util.List;

import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.model.LegajoCategoriaImputacion;

public interface LegajoCategoriaImputacionRepository {

    LegajoCategoriaImputacion create(LegajoCategoriaImputacion legajoCategoriaImputacion);

    List<LegajoCategoriaImputacion> findByLegajo(Long legajoId, Integer anho, Integer mes);

    void deleteAllByLegajo(Long legajoId, Integer anho, Integer mes);

    void deleteAllByPeriodo(Integer anho, Integer mes);
}
