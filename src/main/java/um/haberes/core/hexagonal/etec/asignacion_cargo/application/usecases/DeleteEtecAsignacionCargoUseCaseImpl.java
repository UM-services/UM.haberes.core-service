package um.haberes.core.hexagonal.etec.asignacion_cargo.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.in.DeleteEtecAsignacionCargoUseCase;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.out.EtecAsignacionCargoRepository;
import um.haberes.core.hexagonal.etec.presentismo.application.service.EtecPresentismoService;

@Component
@RequiredArgsConstructor
public class DeleteEtecAsignacionCargoUseCaseImpl implements DeleteEtecAsignacionCargoUseCase {

    private final EtecAsignacionCargoRepository repository;

    private final EtecPresentismoService presentismoService;

    @Transactional
    @Override
    public void deleteEtecAsignacionCargo(Long asignacionCargoId) {
        // Primero las pérdidas de presentismo: la FK no tiene cascada para que cada
        // borrado pase por JPA y quede auditado.
        presentismoService.deleteAllByAsignacionCargoId(asignacionCargoId);
        repository.deleteById(asignacionCargoId);
    }
}
