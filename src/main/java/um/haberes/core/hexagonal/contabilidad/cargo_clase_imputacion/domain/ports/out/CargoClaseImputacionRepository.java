package um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.model.CargoClaseImputacion;

public interface CargoClaseImputacionRepository {

    CargoClaseImputacion create(CargoClaseImputacion cargoClaseImputacion);

    Optional<CargoClaseImputacion> findById(Long cargoClaseImputacionId);

    Optional<CargoClaseImputacion> findByUnique(Integer dependenciaId, Integer facultadId, Integer geograficaId,
            Long cargoClaseId);

    List<CargoClaseImputacion> findAll();

    Optional<CargoClaseImputacion> update(Long cargoClaseImputacionId, CargoClaseImputacion cargoClaseImputacion);
}
