package um.haberes.core.hexagonal.etec.presentismo_horas.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.presentismo_horas.application.exception.EtecPresentismoHorasException;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.model.EtecPresentismoHoras;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.ports.in.CreateEtecPresentismoHorasUseCase;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.ports.in.DeleteEtecPresentismoHorasUseCase;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.ports.in.GetEtecPresentismoHorasUseCase;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.ports.in.UpdateEtecPresentismoHorasUseCase;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.ports.out.EtecPresentismoHorasRepository;

@Service
@RequiredArgsConstructor
public class EtecPresentismoHorasService {

    private final GetEtecPresentismoHorasUseCase getUseCase;

    private final CreateEtecPresentismoHorasUseCase createUseCase;

    private final UpdateEtecPresentismoHorasUseCase updateUseCase;

    private final DeleteEtecPresentismoHorasUseCase deleteUseCase;

    private final EtecPresentismoHorasRepository repository;

    public EtecPresentismoHoras findById(Long presentismoHorasId) {
        return getUseCase.getById(presentismoHorasId)
                .orElseThrow(() -> new EtecPresentismoHorasException(presentismoHorasId));
    }

    public Optional<EtecPresentismoHoras> findByLegajoAndPeriodo(
            Long legajoId, Integer anho, Integer mes) {
        return getUseCase.getByLegajoIdAndPeriodo(legajoId, anho, mes);
    }

    public List<EtecPresentismoHoras> findAllByPeriodo(Integer anho, Integer mes) {
        return getUseCase.getByPeriodo(anho, mes);
    }

    public boolean tieneCargosHoras(Long legajoId, Integer anho, Integer mes) {
        return getUseCase.tieneCargosHoras(legajoId, anho, mes);
    }

    public boolean existe(Long legajoId, Integer anho, Integer mes) {
        return repository.existsByLegajoIdAndPeriodo(legajoId, anho, mes);
    }

    public EtecPresentismoHoras add(EtecPresentismoHoras presentismo) {
        return createUseCase.createEtecPresentismoHoras(presentismo);
    }

    public EtecPresentismoHoras update(Long presentismoHorasId, EtecPresentismoHoras presentismo) {
        return updateUseCase.updateEtecPresentismoHoras(presentismoHorasId, presentismo)
                .orElseThrow(() -> new EtecPresentismoHorasException(presentismoHorasId));
    }

    public void delete(Long presentismoHorasId) {
        deleteUseCase.deleteEtecPresentismoHoras(presentismoHorasId);
    }
}
