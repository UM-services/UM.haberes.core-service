package um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.application.exception.CargoClaseImputacionException;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.model.CargoClaseImputacion;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.ports.in.CreateCargoClaseImputacionUseCase;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.ports.in.GetAllCargoClaseImputacionesUseCase;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.ports.in.GetCargoClaseImputacionByIdUseCase;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.ports.in.GetCargoClaseImputacionByUniqueUseCase;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.ports.in.UpdateCargoClaseImputacionUseCase;

@Service
@RequiredArgsConstructor
public class CargoClaseImputacionService {

    private final GetAllCargoClaseImputacionesUseCase getAllCargoClaseImputacionesUseCase;
    private final GetCargoClaseImputacionByIdUseCase getCargoClaseImputacionByIdUseCase;
    private final GetCargoClaseImputacionByUniqueUseCase getCargoClaseImputacionByUniqueUseCase;
    private final CreateCargoClaseImputacionUseCase createCargoClaseImputacionUseCase;
    private final UpdateCargoClaseImputacionUseCase updateCargoClaseImputacionUseCase;

    public List<CargoClaseImputacion> getAllCargoClaseImputaciones() {
        return getAllCargoClaseImputacionesUseCase.getAllCargoClaseImputaciones();
    }

    public CargoClaseImputacion getCargoClaseImputacionById(Long cargoClaseImputacionId) {
        return getCargoClaseImputacionByIdUseCase.getCargoClaseImputacionById(cargoClaseImputacionId)
                .orElseThrow(() -> new CargoClaseImputacionException(cargoClaseImputacionId));
    }

    public CargoClaseImputacion getCargoClaseImputacionByUnique(Integer dependenciaId, Integer facultadId,
            Integer geograficaId, Long cargoClaseId) {
        return getCargoClaseImputacionByUniqueUseCase
                .getCargoClaseImputacionByUnique(dependenciaId, facultadId, geograficaId, cargoClaseId)
                .orElseThrow(() -> new CargoClaseImputacionException(dependenciaId, facultadId, geograficaId,
                        cargoClaseId));
    }

    public CargoClaseImputacion createCargoClaseImputacion(CargoClaseImputacion cargoClaseImputacion) {
        return createCargoClaseImputacionUseCase.createCargoClaseImputacion(cargoClaseImputacion);
    }

    public CargoClaseImputacion updateCargoClaseImputacion(Long cargoClaseImputacionId,
            CargoClaseImputacion cargoClaseImputacion) {
        return updateCargoClaseImputacionUseCase
                .updateCargoClaseImputacion(cargoClaseImputacionId, cargoClaseImputacion)
                .orElseThrow(() -> new CargoClaseImputacionException(cargoClaseImputacionId));
    }
}
