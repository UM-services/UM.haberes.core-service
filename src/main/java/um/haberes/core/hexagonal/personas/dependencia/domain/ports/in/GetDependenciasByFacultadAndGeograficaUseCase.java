package um.haberes.core.hexagonal.personas.dependencia.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;

public interface GetDependenciasByFacultadAndGeograficaUseCase {

    List<Dependencia> getDependenciasByFacultadAndGeografica(Integer facultadId, Integer geograficaId);
}
