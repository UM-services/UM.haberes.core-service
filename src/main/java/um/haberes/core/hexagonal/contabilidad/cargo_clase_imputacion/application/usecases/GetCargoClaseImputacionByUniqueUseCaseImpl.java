package um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.model.CargoClaseImputacion;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.ports.in.GetCargoClaseImputacionByUniqueUseCase;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.ports.out.CargoClaseImputacionRepository;

@Component
@RequiredArgsConstructor
public class GetCargoClaseImputacionByUniqueUseCaseImpl implements GetCargoClaseImputacionByUniqueUseCase {

    private final CargoClaseImputacionRepository cargoClaseImputacionRepository;

    @Override
    public Optional<CargoClaseImputacion> getCargoClaseImputacionByUnique(Integer dependenciaId, Integer facultadId,
            Integer geograficaId, Long cargoClaseId) {
        return cargoClaseImputacionRepository.findByUnique(dependenciaId, facultadId, geograficaId, cargoClaseId);
    }
}
