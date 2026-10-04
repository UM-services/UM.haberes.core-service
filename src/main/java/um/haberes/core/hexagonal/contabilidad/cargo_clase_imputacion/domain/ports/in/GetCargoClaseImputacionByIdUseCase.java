package um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.model.CargoClaseImputacion;

public interface GetCargoClaseImputacionByIdUseCase {

    Optional<CargoClaseImputacion> getCargoClaseImputacionById(Long cargoClaseImputacionId);
}
