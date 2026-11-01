package um.haberes.core.hexagonal.personas.dependencia.domain.ports.out;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;

public interface DependenciaRepository {

    List<Dependencia> findAll();

    List<Dependencia> findAllByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId);

    Optional<Dependencia> findByDependenciaId(Integer dependenciaId);

    Optional<Dependencia> findFirstByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId);

    List<Dependencia> findAllByDependenciaIdIn(Set<Integer> dependenciaIds);
}
