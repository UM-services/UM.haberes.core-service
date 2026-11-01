package um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.model.CargoClaseImputacion;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.ports.in.UpdateCargoClaseImputacionUseCase;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.ports.out.CargoClaseImputacionRepository;

@Component
@RequiredArgsConstructor
public class UpdateCargoClaseImputacionUseCaseImpl implements UpdateCargoClaseImputacionUseCase {

    private final CargoClaseImputacionRepository cargoClaseImputacionRepository;

    @Override
    public Optional<CargoClaseImputacion> updateCargoClaseImputacion(Long cargoClaseImputacionId,
            CargoClaseImputacion cargoClaseImputacion) {
        return cargoClaseImputacionRepository.update(cargoClaseImputacionId, cargoClaseImputacion);
    }
}
