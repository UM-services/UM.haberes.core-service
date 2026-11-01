package um.haberes.core.hexagonal.liquidaciones.acreditacion.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.in.GetAcreditacionByIdUseCase;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.out.AcreditacionRepository;

@Component
@RequiredArgsConstructor
public class GetAcreditacionByIdUseCaseImpl implements GetAcreditacionByIdUseCase {

    private final AcreditacionRepository acreditacionRepository;

    @Override
    public Optional<Acreditacion> getAcreditacionById(Long acreditacionId) {
        return acreditacionRepository.findByAcreditacionId(acreditacionId);
    }
}
