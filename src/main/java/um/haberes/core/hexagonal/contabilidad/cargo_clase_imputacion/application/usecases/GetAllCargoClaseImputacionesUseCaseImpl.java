package um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.model.CargoClaseImputacion;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.ports.in.GetAllCargoClaseImputacionesUseCase;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.ports.out.CargoClaseImputacionRepository;

@Component
@RequiredArgsConstructor
public class GetAllCargoClaseImputacionesUseCaseImpl implements GetAllCargoClaseImputacionesUseCase {

    private final CargoClaseImputacionRepository cargoClaseImputacionRepository;

    @Override
    public List<CargoClaseImputacion> getAllCargoClaseImputaciones() {
        return cargoClaseImputacionRepository.findAll();
    }
}
