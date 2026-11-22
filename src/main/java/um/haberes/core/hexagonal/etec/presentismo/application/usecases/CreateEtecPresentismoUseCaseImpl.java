package um.haberes.core.hexagonal.etec.presentismo.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.presentismo.application.service.EtecPresentismoValidator;
import um.haberes.core.hexagonal.etec.presentismo.domain.model.EtecPresentismo;
import um.haberes.core.hexagonal.etec.presentismo.domain.ports.in.CreateEtecPresentismoUseCase;
import um.haberes.core.hexagonal.etec.presentismo.domain.ports.out.EtecPresentismoRepository;

@Component
@RequiredArgsConstructor
public class CreateEtecPresentismoUseCaseImpl implements CreateEtecPresentismoUseCase {

    private final EtecPresentismoRepository repository;

    private final EtecPresentismoValidator validator;

    @Transactional
    @Override
    public EtecPresentismo createEtecPresentismo(EtecPresentismo presentismo) {
        validator.validarAlta(presentismo);
        presentismo.setMotivo(presentismo.getMotivo().trim());
        return repository.save(presentismo);
    }
}
