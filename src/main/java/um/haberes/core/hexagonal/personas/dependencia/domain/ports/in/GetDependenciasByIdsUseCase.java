package um.haberes.core.hexagonal.personas.dependencia.domain.ports.in;

import java.util.List;
import java.util.Set;

import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;

public interface GetDependenciasByIdsUseCase {

    List<Dependencia> getDependenciasByIds(Set<Integer> dependenciaIds);
}
