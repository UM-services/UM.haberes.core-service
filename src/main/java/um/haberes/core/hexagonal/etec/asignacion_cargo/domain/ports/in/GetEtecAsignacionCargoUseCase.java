package um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.in;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.model.EtecAsignacionCargo;

public interface GetEtecAsignacionCargoUseCase {

    Optional<EtecAsignacionCargo> getById(Long asignacionCargoId);

    List<EtecAsignacionCargo> getByLegajo(Long legajoId);

    List<EtecAsignacionCargo> getVigentesByPeriodo(Integer anho, Integer mes);

    List<EtecAsignacionCargo> getVigentesByLegajoAndPeriodo(Long legajoId, Integer anho, Integer mes);

    List<Integer> getCategoriasAsignadas();

    boolean existsByCategoria(Integer categoriaId);
}
