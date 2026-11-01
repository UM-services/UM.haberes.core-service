package um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.model.CargoClaseImputacion;

public interface GetAllCargoClaseImputacionesUseCase {

    List<CargoClaseImputacion> getAllCargoClaseImputaciones();
}
