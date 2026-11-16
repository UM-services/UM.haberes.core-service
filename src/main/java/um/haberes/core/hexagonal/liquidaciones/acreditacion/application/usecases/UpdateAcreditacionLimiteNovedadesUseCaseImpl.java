package um.haberes.core.hexagonal.liquidaciones.acreditacion.application.usecases;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.in.UpdateAcreditacionLimiteNovedadesUseCase;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.out.AcreditacionRepository;

@Component
@RequiredArgsConstructor
public class UpdateAcreditacionLimiteNovedadesUseCaseImpl implements UpdateAcreditacionLimiteNovedadesUseCase {

    private final AcreditacionRepository acreditacionRepository;

    @Override
    public Optional<Acreditacion> updateLimiteNovedades(Long acreditacionId, OffsetDateTime limiteNovedades) {
        return acreditacionRepository.updateLimiteNovedades(acreditacionId, limiteNovedades);
    }
}
