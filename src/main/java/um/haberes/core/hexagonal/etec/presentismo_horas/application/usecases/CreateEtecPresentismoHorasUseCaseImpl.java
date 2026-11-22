package um.haberes.core.hexagonal.etec.presentismo_horas.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.presentismo_horas.application.service.EtecPresentismoHorasValidator;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.model.EtecPresentismoHoras;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.ports.in.CreateEtecPresentismoHorasUseCase;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.ports.out.EtecPresentismoHorasRepository;

@Component
@RequiredArgsConstructor
public class CreateEtecPresentismoHorasUseCaseImpl implements CreateEtecPresentismoHorasUseCase {

    private final EtecPresentismoHorasRepository repository;

    private final EtecPresentismoHorasValidator validator;

    @Transactional
    @Override
    public EtecPresentismoHoras createEtecPresentismoHoras(EtecPresentismoHoras presentismo) {
        validator.validarAlta(presentismo);
        presentismo.setMotivo(presentismo.getMotivo().trim());
        return repository.save(presentismo);
    }
}
