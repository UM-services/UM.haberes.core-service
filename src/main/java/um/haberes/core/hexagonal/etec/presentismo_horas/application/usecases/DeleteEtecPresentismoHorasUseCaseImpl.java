package um.haberes.core.hexagonal.etec.presentismo_horas.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.presentismo_horas.application.exception.EtecPresentismoHorasException;
import um.haberes.core.hexagonal.etec.presentismo_horas.application.service.EtecPresentismoHorasValidator;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.model.EtecPresentismoHoras;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.ports.in.DeleteEtecPresentismoHorasUseCase;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.ports.out.EtecPresentismoHorasRepository;

@Component
@RequiredArgsConstructor
public class DeleteEtecPresentismoHorasUseCaseImpl implements DeleteEtecPresentismoHorasUseCase {

    private final EtecPresentismoHorasRepository repository;

    private final EtecPresentismoHorasValidator validator;

    @Transactional
    @Override
    public void deleteEtecPresentismoHoras(Long presentismoHorasId) {
        EtecPresentismoHoras actual = repository.findById(presentismoHorasId)
                .orElseThrow(() -> new EtecPresentismoHorasException(presentismoHorasId));
        validator.validarPeriodoAbierto(actual.getAnho(), actual.getMes());
        repository.deleteById(presentismoHorasId);
    }
}
