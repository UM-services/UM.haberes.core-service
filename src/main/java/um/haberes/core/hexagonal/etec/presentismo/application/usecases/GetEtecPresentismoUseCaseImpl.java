package um.haberes.core.hexagonal.etec.presentismo.application.usecases;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.presentismo.domain.model.EtecPresentismo;
import um.haberes.core.hexagonal.etec.presentismo.domain.ports.in.GetEtecPresentismoUseCase;
import um.haberes.core.hexagonal.etec.presentismo.domain.ports.out.EtecPresentismoRepository;

@Component
@RequiredArgsConstructor
public class GetEtecPresentismoUseCaseImpl implements GetEtecPresentismoUseCase {

    private final EtecPresentismoRepository repository;

    @Override
    public Optional<EtecPresentismo> getById(Long presentismoId) {
        return repository.findById(presentismoId);
    }

    @Override
    public Optional<EtecPresentismo> getByAsignacionCargoIdAndPeriodo(
            Long asignacionCargoId, Integer anho, Integer mes) {
        return repository.findByAsignacionCargoIdAndPeriodo(asignacionCargoId, anho, mes);
    }

    @Override
    public List<EtecPresentismo> getByLegajoIdAndPeriodo(Long legajoId, Integer anho, Integer mes) {
        return repository.findAllByLegajoIdAndPeriodo(legajoId, anho, mes);
    }

    @Override
    public List<EtecPresentismo> getByPeriodo(Integer anho, Integer mes) {
        return repository.findAllByPeriodo(anho, mes);
    }

    @Override
    public Set<Integer> getCategoriasSinPresentismo(Long legajoId, Integer anho, Integer mes) {
        return repository.findAllByLegajoIdAndPeriodo(legajoId, anho, mes).stream()
                .map(EtecPresentismo::getCategoriaId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}
