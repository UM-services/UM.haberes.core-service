package um.haberes.core.hexagonal.etec.presentismo.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.presentismo.application.exception.EtecPresentismoException;
import um.haberes.core.hexagonal.etec.presentismo.application.service.EtecPresentismoValidator;
import um.haberes.core.hexagonal.etec.presentismo.domain.model.EtecPresentismo;
import um.haberes.core.hexagonal.etec.presentismo.domain.ports.in.UpdateEtecPresentismoUseCase;
import um.haberes.core.hexagonal.etec.presentismo.domain.ports.out.EtecPresentismoRepository;

@Component
@RequiredArgsConstructor
public class UpdateEtecPresentismoUseCaseImpl implements UpdateEtecPresentismoUseCase {

    private final EtecPresentismoRepository repository;

    private final EtecPresentismoValidator validator;

    @Transactional
    @Override
    public Optional<EtecPresentismo> updateEtecPresentismo(Long presentismoId, EtecPresentismo presentismo) {
        EtecPresentismo actual = repository.findById(presentismoId)
                .orElseThrow(() -> new EtecPresentismoException(presentismoId));
        validator.validarMotivo(presentismo.getMotivo());
        validator.validarPeriodoAbierto(actual.getAnho(), actual.getMes());
        actual.setMotivo(presentismo.getMotivo().trim());
        return Optional.of(repository.save(actual));
    }
}
