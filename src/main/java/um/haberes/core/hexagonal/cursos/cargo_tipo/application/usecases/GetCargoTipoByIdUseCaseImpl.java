package um.haberes.core.hexagonal.cursos.cargo_tipo.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.cargo_tipo.domain.model.CargoTipo;
import um.haberes.core.hexagonal.cursos.cargo_tipo.domain.ports.in.GetCargoTipoByIdUseCase;
import um.haberes.core.hexagonal.cursos.cargo_tipo.domain.ports.out.CargoTipoRepository;

@Component
@RequiredArgsConstructor
public class GetCargoTipoByIdUseCaseImpl implements GetCargoTipoByIdUseCase {

    private final CargoTipoRepository cargoTipoRepository;

    @Override
    public Optional<CargoTipo> getCargoTipoById(Integer cargoTipoId) {
        return cargoTipoRepository.findByCargoTipoId(cargoTipoId);
    }
}
