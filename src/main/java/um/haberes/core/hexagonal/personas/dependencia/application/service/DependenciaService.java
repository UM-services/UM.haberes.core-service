package um.haberes.core.hexagonal.personas.dependencia.application.service;

import java.util.List;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.dependencia.application.exception.DependenciaException;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;
import um.haberes.core.hexagonal.personas.dependencia.domain.ports.in.GetAllDependenciasUseCase;
import um.haberes.core.hexagonal.personas.dependencia.domain.ports.in.GetDependenciaByFacultadAndGeograficaUseCase;
import um.haberes.core.hexagonal.personas.dependencia.domain.ports.in.GetDependenciaByIdUseCase;
import um.haberes.core.hexagonal.personas.dependencia.domain.ports.in.GetDependenciasByFacultadAndGeograficaUseCase;
import um.haberes.core.hexagonal.personas.dependencia.domain.ports.in.GetDependenciasByIdsUseCase;

@Service
@RequiredArgsConstructor
public class DependenciaService {

    private final GetAllDependenciasUseCase getAllDependenciasUseCase;
    private final GetDependenciasByFacultadAndGeograficaUseCase getDependenciasByFacultadAndGeograficaUseCase;
    private final GetDependenciaByIdUseCase getDependenciaByIdUseCase;
    private final GetDependenciaByFacultadAndGeograficaUseCase getDependenciaByFacultadAndGeograficaUseCase;
    private final GetDependenciasByIdsUseCase getDependenciasByIdsUseCase;

    @Cacheable("dependencias")
    public List<Dependencia> findAll() {
        return getAllDependenciasUseCase.getAllDependencias();
    }

    public List<Dependencia> findAllByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId) {
        return getDependenciasByFacultadAndGeograficaUseCase.getDependenciasByFacultadAndGeografica(facultadId, geograficaId);
    }

    public Dependencia findByDependenciaId(Integer dependenciaId) {
        return getDependenciaByIdUseCase.getDependenciaById(dependenciaId)
                .orElseThrow(() -> new DependenciaException(dependenciaId));
    }

    public Dependencia findFirstByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId) {
        return getDependenciaByFacultadAndGeograficaUseCase.getDependenciaByFacultadAndGeografica(facultadId, geograficaId)
                .orElseThrow(() -> new DependenciaException(facultadId, geograficaId));
    }

    public List<Dependencia> findAllByIds(Set<Integer> dependenciaIds) {
        return getDependenciasByIdsUseCase.getDependenciasByIds(dependenciaIds);
    }
}
