package um.haberes.core.hexagonal.etec.asignacion_cargo.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.model.EtecAsignacionCargo;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.in.UpdateEtecAsignacionCargoUseCase;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.out.EtecAsignacionCargoRepository;

@Component
@RequiredArgsConstructor
public class UpdateEtecAsignacionCargoUseCaseImpl implements UpdateEtecAsignacionCargoUseCase {

    private final EtecAsignacionCargoRepository repository;

    @Transactional
    @Override
    public Optional<EtecAsignacionCargo> updateEtecAsignacionCargo(Long asignacionCargoId,
            EtecAsignacionCargo asignacion) {
        return repository.findById(asignacionCargoId).map(existing -> repository.save(
                EtecAsignacionCargo.builder()
                        .asignacionCargoId(asignacionCargoId)
                        .legajoId(asignacion.getLegajoId())
                        .categoriaId(asignacion.getCategoriaId())
                        .dependenciaId(asignacion.getDependenciaId())
                        .fechaAlta(asignacion.getFechaAlta())
                        .fechaBaja(asignacion.getFechaBaja())
                        .observaciones(asignacion.getObservaciones())
                        .build()));
    }
}
