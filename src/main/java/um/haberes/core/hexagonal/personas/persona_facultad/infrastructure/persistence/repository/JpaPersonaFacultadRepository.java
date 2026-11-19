package um.haberes.core.hexagonal.personas.persona_facultad.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.core.hexagonal.personas.persona_facultad.infrastructure.persistence.entity.PersonaFacultadEntity;

@Repository
public interface JpaPersonaFacultadRepository extends JpaRepository<PersonaFacultadEntity, Long> {

    List<PersonaFacultadEntity> findAllByLegajoId(Long legajoId);

    List<PersonaFacultadEntity> findAllByFacultadId(Integer facultadId);

    Optional<PersonaFacultadEntity> findFirstByLegajoIdAndFacultadId(Long legajoId, Integer facultadId);

    void deleteByLegajoIdAndFacultadId(Long legajoId, Integer facultadId);
}
