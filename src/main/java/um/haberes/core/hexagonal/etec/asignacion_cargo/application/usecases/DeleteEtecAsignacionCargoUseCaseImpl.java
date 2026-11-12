package um.haberes.core.hexagonal.etec.asignacion_cargo.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.in.DeleteEtecAsignacionCargoUseCase;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.out.EtecAsignacionCargoRepository;

@Component
@RequiredArgsConstructor
public class DeleteEtecAsignacionCargoUseCaseImpl implements DeleteEtecAsignacionCargoUseCase {

    private final EtecAsignacionCargoRepository repository;

    @Transactional
    @Override
    public void deleteEtecAsignacionCargo(Long asignacionCargoId) {
        repository.deleteById(asignacionCargoId);
    }
}
