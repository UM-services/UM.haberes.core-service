package um.haberes.core.hexagonal.personas.dependencia.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;
import um.haberes.core.hexagonal.personas.dependencia.domain.ports.out.DependenciaRepository;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.persistence.mapper.DependenciaMapper;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.persistence.repository.JpaDependenciaRepository;

@Component
@RequiredArgsConstructor
public class JpaDependenciaRepositoryAdapter implements DependenciaRepository {

    private final JpaDependenciaRepository jpaDependenciaRepository;

    private final DependenciaMapper dependenciaMapper;

    @Override
    public List<Dependencia> findAll() {
        return jpaDependenciaRepository.findAll().stream()
                .map(dependenciaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dependencia> findAllByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId) {
        return jpaDependenciaRepository.findAllByFacultadIdAndGeograficaId(facultadId, geograficaId).stream()
                .map(dependenciaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Dependencia> findByDependenciaId(Integer dependenciaId) {
        return jpaDependenciaRepository.findByDependenciaId(dependenciaId).map(dependenciaMapper::toDomain);
    }

    @Override
    public Optional<Dependencia> findFirstByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId) {
        return jpaDependenciaRepository.findFirstByFacultadIdAndGeograficaId(facultadId, geograficaId)
                .map(dependenciaMapper::toDomain);
    }

    @Override
    public List<Dependencia> findAllByDependenciaIdIn(Set<Integer> dependenciaIds) {
        return jpaDependenciaRepository.findAllByDependenciaIdIn(dependenciaIds).stream()
                .map(dependenciaMapper::toDomain)
                .collect(Collectors.toList());
    }
}
