package um.haberes.core.hexagonal.etec.presentismo_horas.application.usecases;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.presentismo_horas.application.service.EtecPresentismoHorasValidator;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.model.EtecPresentismoHoras;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.ports.in.GetEtecPresentismoHorasUseCase;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.ports.out.EtecPresentismoHorasRepository;

@Component
@RequiredArgsConstructor
public class GetEtecPresentismoHorasUseCaseImpl implements GetEtecPresentismoHorasUseCase {

    private final EtecPresentismoHorasRepository repository;

    private final EtecPresentismoHorasValidator validator;

    @Override
    public Optional<EtecPresentismoHoras> getById(Long presentismoHorasId) {
        return repository.findById(presentismoHorasId);
    }

    @Override
    public Optional<EtecPresentismoHoras> getByLegajoIdAndPeriodo(
            Long legajoId, Integer anho, Integer mes) {
        return repository.findByLegajoIdAndPeriodo(legajoId, anho, mes);
    }

    @Override
    public List<EtecPresentismoHoras> getByPeriodo(Integer anho, Integer mes) {
        return repository.findAllByPeriodo(anho, mes);
    }

    @Override
    public boolean tieneCargosHoras(Long legajoId, Integer anho, Integer mes) {
        return validator.tieneCargosHoras(legajoId, anho, mes);
    }
}
