package um.haberes.core.hexagonal.etec.asignacion_cargo.application.usecases;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.model.EtecAsignacionCargo;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.in.GetEtecAsignacionCargoUseCase;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.out.EtecAsignacionCargoRepository;

@Component
@RequiredArgsConstructor
public class GetEtecAsignacionCargoUseCaseImpl implements GetEtecAsignacionCargoUseCase {

    private final EtecAsignacionCargoRepository repository;

    @Override
    public Optional<EtecAsignacionCargo> getById(Long asignacionCargoId) {
        return repository.findById(asignacionCargoId);
    }

    @Override
    public List<EtecAsignacionCargo> getByLegajo(Long legajoId) {
        return repository.findAllByLegajoId(legajoId);
    }

    @Override
    public List<EtecAsignacionCargo> getVigentesByPeriodo(Integer anho, Integer mes) {
        return repository.findAllVigentesByPeriodo(anho, mes);
    }

    @Override
    public List<EtecAsignacionCargo> getVigentesByLegajoAndPeriodo(Long legajoId, Integer anho, Integer mes) {
        return repository.findAllVigentesByLegajoAndPeriodo(legajoId, anho, mes);
    }

    @Override
    public List<Integer> getCategoriasAsignadas() {
        return repository.findDistinctCategoriaIds();
    }

    @Override
    public boolean existsByCategoria(Integer categoriaId) {
        return repository.existsByCategoriaId(categoriaId);
    }
}
