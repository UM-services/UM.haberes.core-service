package um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.model.EtecAsignacionCargo;

public interface EtecAsignacionCargoRepository {

    EtecAsignacionCargo save(EtecAsignacionCargo asignacion);

    Optional<EtecAsignacionCargo> findById(Long asignacionCargoId);

    List<EtecAsignacionCargo> findAllByLegajoId(Long legajoId);

    List<EtecAsignacionCargo> findAllVigentesByPeriodo(Integer anho, Integer mes);

    List<EtecAsignacionCargo> findAllVigentesByLegajoAndPeriodo(Long legajoId, Integer anho, Integer mes);

    List<Integer> findDistinctCategoriaIds();

    boolean existsByCategoriaId(Integer categoriaId);

    void deleteById(Long asignacionCargoId);
}
