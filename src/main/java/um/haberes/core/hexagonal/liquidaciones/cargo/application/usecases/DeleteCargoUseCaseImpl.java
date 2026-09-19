package um.haberes.core.hexagonal.liquidaciones.cargo.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.in.DeleteCargoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.out.CargoRepository;

@Component
@RequiredArgsConstructor
public class DeleteCargoUseCaseImpl implements DeleteCargoUseCase {

    private final CargoRepository cargoRepository;

    @Transactional
    @Override
    public void deleteCargo(Long cargoId) {
        cargoRepository.deleteByCargoId(cargoId);
    }
}
