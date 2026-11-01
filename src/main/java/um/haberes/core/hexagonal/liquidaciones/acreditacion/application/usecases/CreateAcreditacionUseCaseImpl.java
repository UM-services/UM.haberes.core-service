package um.haberes.core.hexagonal.liquidaciones.acreditacion.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.in.CreateAcreditacionUseCase;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.out.AcreditacionRepository;

@Component
@RequiredArgsConstructor
public class CreateAcreditacionUseCaseImpl implements CreateAcreditacionUseCase {

    private final AcreditacionRepository acreditacionRepository;

    @Override
    public Acreditacion createAcreditacion(Acreditacion acreditacion) {
        return acreditacionRepository.create(acreditacion);
    }
}
