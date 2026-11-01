package um.haberes.core.hexagonal.personas.dependencia.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;

public interface GetDependenciaByFacultadAndGeograficaUseCase {

    Optional<Dependencia> getDependenciaByFacultadAndGeografica(Integer facultadId, Integer geograficaId);
}
