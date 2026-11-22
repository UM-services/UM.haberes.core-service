package um.haberes.core.hexagonal.etec.presentismo_horas.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.presentismo_horas.application.exception.EtecPresentismoHorasException;
import um.haberes.core.hexagonal.etec.presentismo_horas.application.service.EtecPresentismoHorasValidator;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.model.EtecPresentismoHoras;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.ports.in.UpdateEtecPresentismoHorasUseCase;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.ports.out.EtecPresentismoHorasRepository;

@Component
@RequiredArgsConstructor
public class UpdateEtecPresentismoHorasUseCaseImpl implements UpdateEtecPresentismoHorasUseCase {

    private final EtecPresentismoHorasRepository repository;

    private final EtecPresentismoHorasValidator validator;

    @Transactional
    @Override
    public Optional<EtecPresentismoHoras> updateEtecPresentismoHoras(
            Long presentismoHorasId, EtecPresentismoHoras presentismo) {
        EtecPresentismoHoras actual = repository.findById(presentismoHorasId)
                .orElseThrow(() -> new EtecPresentismoHorasException(presentismoHorasId));
        validator.validarMotivo(presentismo.getMotivo());
        validator.validarPeriodoAbierto(actual.getAnho(), actual.getMes());
        actual.setMotivo(presentismo.getMotivo().trim());
        return Optional.of(repository.save(actual));
    }
}
