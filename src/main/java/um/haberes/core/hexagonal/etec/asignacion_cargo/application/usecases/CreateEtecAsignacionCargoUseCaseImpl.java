package um.haberes.core.hexagonal.etec.asignacion_cargo.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.model.EtecAsignacionCargo;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.in.CreateEtecAsignacionCargoUseCase;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.out.EtecAsignacionCargoRepository;

@Component
@RequiredArgsConstructor
public class CreateEtecAsignacionCargoUseCaseImpl implements CreateEtecAsignacionCargoUseCase {

    private final EtecAsignacionCargoRepository repository;

    @Override
    public EtecAsignacionCargo createEtecAsignacionCargo(EtecAsignacionCargo asignacion) {
        return repository.save(asignacion);
    }
}
