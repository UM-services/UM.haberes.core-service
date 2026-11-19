package um.haberes.core.hexagonal.personas.persona_facultad.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.personas.persona_facultad.domain.model.PersonaFacultad;

public interface PersonaFacultadRepository {

    List<PersonaFacultad> findAllByLegajoId(Long legajoId);

    List<PersonaFacultad> findAllByFacultadId(Integer facultadId);

    Optional<PersonaFacultad> findByLegajoIdAndFacultadId(Long legajoId, Integer facultadId);

    PersonaFacultad save(PersonaFacultad personaFacultad);

    void deleteByLegajoIdAndFacultadId(Long legajoId, Integer facultadId);
}
