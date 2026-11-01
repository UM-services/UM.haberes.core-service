package um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.ports.in;

import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.model.CargoClaseImputacion;

public interface CreateCargoClaseImputacionUseCase {

    CargoClaseImputacion createCargoClaseImputacion(CargoClaseImputacion cargoClaseImputacion);
}
