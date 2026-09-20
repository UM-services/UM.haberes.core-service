package um.haberes.core.hexagonal.cursos.cargo_tipo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.cargo_tipo.domain.model.CargoTipo;
import um.haberes.core.hexagonal.cursos.cargo_tipo.domain.ports.in.GetAllCargoTiposByCargoTipoIdInUseCase;
import um.haberes.core.hexagonal.cursos.cargo_tipo.domain.ports.out.CargoTipoRepository;

@Component
@RequiredArgsConstructor
public class GetAllCargoTiposByCargoTipoIdInUseCaseImpl implements GetAllCargoTiposByCargoTipoIdInUseCase {

    private final CargoTipoRepository cargoTipoRepository;

    @Override
    public List<CargoTipo> getAllCargoTiposByCargoTipoIdIn(List<Integer> cargoTipoIds) {
        return cargoTipoRepository.findAllByCargoTipoIdIn(cargoTipoIds);
    }
}
