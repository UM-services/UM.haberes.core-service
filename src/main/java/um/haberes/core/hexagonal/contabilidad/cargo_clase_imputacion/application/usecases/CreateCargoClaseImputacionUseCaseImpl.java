package um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.model.CargoClaseImputacion;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.ports.in.CreateCargoClaseImputacionUseCase;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.ports.out.CargoClaseImputacionRepository;

@Component
@RequiredArgsConstructor
public class CreateCargoClaseImputacionUseCaseImpl implements CreateCargoClaseImputacionUseCase {

    private final CargoClaseImputacionRepository cargoClaseImputacionRepository;

    @Override
    public CargoClaseImputacion createCargoClaseImputacion(CargoClaseImputacion cargoClaseImputacion) {
        return cargoClaseImputacionRepository.create(cargoClaseImputacion);
    }
}
