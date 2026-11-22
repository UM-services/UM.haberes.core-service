package um.haberes.core.hexagonal.etec.presentismo.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.presentismo.application.exception.EtecPresentismoException;
import um.haberes.core.hexagonal.etec.presentismo.application.service.EtecPresentismoValidator;
import um.haberes.core.hexagonal.etec.presentismo.domain.model.EtecPresentismo;
import um.haberes.core.hexagonal.etec.presentismo.domain.ports.in.DeleteEtecPresentismoUseCase;
import um.haberes.core.hexagonal.etec.presentismo.domain.ports.out.EtecPresentismoRepository;

@Component
@RequiredArgsConstructor
public class DeleteEtecPresentismoUseCaseImpl implements DeleteEtecPresentismoUseCase {

    private final EtecPresentismoRepository repository;

    private final EtecPresentismoValidator validator;

    @Transactional
    @Override
    public void deleteEtecPresentismo(Long presentismoId) {
        EtecPresentismo actual = repository.findById(presentismoId)
                .orElseThrow(() -> new EtecPresentismoException(presentismoId));
        validator.validarPeriodoAbierto(actual.getAnho(), actual.getMes());
        repository.deleteById(presentismoId);
    }
}
