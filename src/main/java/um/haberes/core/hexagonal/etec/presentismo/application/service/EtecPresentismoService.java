package um.haberes.core.hexagonal.etec.presentismo.application.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.presentismo.application.exception.EtecPresentismoException;
import um.haberes.core.hexagonal.etec.presentismo.domain.model.EtecPresentismo;
import um.haberes.core.hexagonal.etec.presentismo.domain.ports.in.CreateEtecPresentismoUseCase;
import um.haberes.core.hexagonal.etec.presentismo.domain.ports.in.DeleteEtecPresentismoUseCase;
import um.haberes.core.hexagonal.etec.presentismo.domain.ports.in.GetEtecPresentismoUseCase;
import um.haberes.core.hexagonal.etec.presentismo.domain.ports.in.UpdateEtecPresentismoUseCase;
import um.haberes.core.hexagonal.etec.presentismo.domain.ports.out.EtecPresentismoRepository;

@Service
@RequiredArgsConstructor
public class EtecPresentismoService {

    private final GetEtecPresentismoUseCase getUseCase;

    private final CreateEtecPresentismoUseCase createUseCase;

    private final UpdateEtecPresentismoUseCase updateUseCase;

    private final DeleteEtecPresentismoUseCase deleteUseCase;

    private final EtecPresentismoRepository repository;

    public EtecPresentismo findById(Long presentismoId) {
        return getUseCase.getById(presentismoId)
                .orElseThrow(() -> new EtecPresentismoException(presentismoId));
    }

    public Optional<EtecPresentismo> findByAsignacionAndPeriodo(
            Long asignacionCargoId, Integer anho, Integer mes) {
        return getUseCase.getByAsignacionCargoIdAndPeriodo(asignacionCargoId, anho, mes);
    }

    public List<EtecPresentismo> findAllByLegajoAndPeriodo(Long legajoId, Integer anho, Integer mes) {
        return getUseCase.getByLegajoIdAndPeriodo(legajoId, anho, mes);
    }

    public List<EtecPresentismo> findAllByPeriodo(Integer anho, Integer mes) {
        return getUseCase.getByPeriodo(anho, mes);
    }

    public Set<Integer> categoriasSinPresentismo(Long legajoId, Integer anho, Integer mes) {
        return getUseCase.getCategoriasSinPresentismo(legajoId, anho, mes);
    }

    public EtecPresentismo add(EtecPresentismo presentismo) {
        return createUseCase.createEtecPresentismo(presentismo);
    }

    public EtecPresentismo update(Long presentismoId, EtecPresentismo presentismo) {
        return updateUseCase.updateEtecPresentismo(presentismoId, presentismo)
                .orElseThrow(() -> new EtecPresentismoException(presentismoId));
    }

    public void delete(Long presentismoId) {
        deleteUseCase.deleteEtecPresentismo(presentismoId);
    }

    /**
     * Baja en cascada al eliminar una asignación de cargo ETEC. No valida el período: la
     * baja de la asignación ya está permitida y sus pérdidas de presentismo la siguen para
     * no dejar filas huérfanas. Cada borrado pasa por JPA y queda auditado.
     */
    @Transactional
    public void deleteAllByAsignacionCargoId(Long asignacionCargoId) {
        repository.deleteAllByAsignacionCargoId(asignacionCargoId);
    }
}
