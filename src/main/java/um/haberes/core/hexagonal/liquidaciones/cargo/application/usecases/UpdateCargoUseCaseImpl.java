package um.haberes.core.hexagonal.liquidaciones.cargo.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.in.UpdateCargoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.out.CargoRepository;

@Component
@RequiredArgsConstructor
public class UpdateCargoUseCaseImpl implements UpdateCargoUseCase {

    private final CargoRepository cargoRepository;

    @Transactional
    @Override
    public Optional<Cargo> updateCargo(Long cargoId, Cargo cargo) {
        return cargoRepository.findByCargoId(cargoId).map(existing -> {
            Cargo.CargoBuilder builder = Cargo.builder()
                    .cargoId(cargoId)
                    .legajoId(cargo.getLegajoId())
                    .fechaAlta(cargo.getFechaAlta())
                    .fechaBaja(cargo.getFechaBaja())
                    .dependenciaId(cargo.getDependenciaId())
                    .categoriaId(cargo.getCategoriaId())
                    .jornada(cargo.getJornada())
                    .presentismo(cargo.getPresentismo());
            if (cargo.getHorasJornada() != null) {
                builder.horasJornada(cargo.getHorasJornada());
            }
            return cargoRepository.save(builder.build());
        });
    }
}
