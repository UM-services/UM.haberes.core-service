package um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.ports.out;

import java.util.List;

import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.model.LegajoCargoClaseImputacion;

public interface LegajoCargoClaseImputacionRepository {

    LegajoCargoClaseImputacion create(LegajoCargoClaseImputacion legajoCargoClaseImputacion);

    List<LegajoCargoClaseImputacion> findByLegajo(Long legajoId, Integer anho, Integer mes);

    void deleteAllByLegajo(Long legajoId, Integer anho, Integer mes);

    void deleteAllByPeriodo(Integer anho, Integer mes);
}
