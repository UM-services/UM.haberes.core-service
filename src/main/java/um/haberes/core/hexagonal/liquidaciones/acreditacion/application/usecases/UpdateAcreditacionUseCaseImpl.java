package um.haberes.core.hexagonal.liquidaciones.acreditacion.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.in.UpdateAcreditacionUseCase;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.out.AcreditacionRepository;

@Component
@RequiredArgsConstructor
public class UpdateAcreditacionUseCaseImpl implements UpdateAcreditacionUseCase {

    private final AcreditacionRepository acreditacionRepository;

    @Override
    public Optional<Acreditacion> updateAcreditacion(Long acreditacionId, Acreditacion acreditacion) {
        return acreditacionRepository.update(acreditacionId, acreditacion);
    }
}
