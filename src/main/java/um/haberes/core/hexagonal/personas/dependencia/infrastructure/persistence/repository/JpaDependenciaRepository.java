package um.haberes.core.hexagonal.personas.dependencia.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.core.hexagonal.personas.dependencia.infrastructure.persistence.entity.DependenciaEntity;

@Repository
public interface JpaDependenciaRepository extends JpaRepository<DependenciaEntity, Integer> {

    List<DependenciaEntity> findAllByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId);

    Optional<DependenciaEntity> findFirstByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId);

    Optional<DependenciaEntity> findByDependenciaId(Integer dependenciaId);

    List<DependenciaEntity> findAllByDependenciaIdIn(Set<Integer> dependenciaIds);
}
