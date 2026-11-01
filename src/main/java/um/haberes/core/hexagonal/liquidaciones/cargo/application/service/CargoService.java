package um.haberes.core.hexagonal.liquidaciones.cargo.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo.application.exception.CargoException;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.in.CreateCargoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.in.DeleteCargoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.in.GetCargoByIdUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.in.GetCargosByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.in.GetCargosDocentesByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.in.GetCargosNoDocentesByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.in.UpdateCargoUseCase;

@Service
@RequiredArgsConstructor
public class CargoService {

    private final GetCargosByLegajoUseCase getCargosByLegajoUseCase;
    private final GetCargosDocentesByLegajoUseCase getCargosDocentesByLegajoUseCase;
    private final GetCargosNoDocentesByLegajoUseCase getCargosNoDocentesByLegajoUseCase;
    private final GetCargoByIdUseCase getCargoByIdUseCase;
    private final CreateCargoUseCase createCargoUseCase;
    private final UpdateCargoUseCase updateCargoUseCase;
    private final DeleteCargoUseCase deleteCargoUseCase;

    public List<Cargo> findAllByLegajoId(Long legajoId) {
        return getCargosByLegajoUseCase.getCargosByLegajo(legajoId);
    }

    public List<Cargo> findAllNoDocenteByPeriodo(Long legajoId, Integer anho, Integer mes) {
        return getCargosNoDocentesByLegajoUseCase.getCargosNoDocentesByLegajo(legajoId, anho, mes);
    }

    public List<Cargo> findAllDocenteByPeriodo(Long legajoId, Integer anho, Integer mes) {
        return getCargosDocentesByLegajoUseCase.getCargosDocentesByLegajo(legajoId, anho, mes);
    }

    public Cargo findByCargoId(Long cargoId) {
        return getCargoByIdUseCase.getCargoById(cargoId)
                .orElseThrow(() -> new CargoException(cargoId));
    }

    public Cargo add(Cargo cargo) {
        return createCargoUseCase.createCargo(cargo);
    }

    public Cargo update(Cargo cargo, Long cargoId) {
        return updateCargoUseCase.updateCargo(cargoId, cargo)
                .orElseThrow(() -> new CargoException(cargoId));
    }

    public void delete(Long cargoId) {
        deleteCargoUseCase.deleteCargo(cargoId);
    }
}
