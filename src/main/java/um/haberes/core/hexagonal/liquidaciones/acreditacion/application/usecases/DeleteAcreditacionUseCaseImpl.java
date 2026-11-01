package um.haberes.core.hexagonal.liquidaciones.acreditacion.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.in.DeleteAcreditacionUseCase;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.out.AcreditacionRepository;

@Component
@RequiredArgsConstructor
public class DeleteAcreditacionUseCaseImpl implements DeleteAcreditacionUseCase {

    private final AcreditacionRepository acreditacionRepository;

    @Override
    public void deleteAcreditacion(Long acreditacionId) {
        acreditacionRepository.deleteById(acreditacionId);
    }
}
