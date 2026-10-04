package um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.model.CargoClaseImputacion;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.ports.in.GetCargoClaseImputacionByIdUseCase;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.ports.out.CargoClaseImputacionRepository;

@Component
@RequiredArgsConstructor
public class GetCargoClaseImputacionByIdUseCaseImpl implements GetCargoClaseImputacionByIdUseCase {

    private final CargoClaseImputacionRepository cargoClaseImputacionRepository;

    @Override
    public Optional<CargoClaseImputacion> getCargoClaseImputacionById(Long cargoClaseImputacionId) {
        return cargoClaseImputacionRepository.findById(cargoClaseImputacionId);
    }
}
