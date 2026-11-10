package um.haberes.core.hexagonal.personas.persona_facultad.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.model.PersonaFacultad;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.ports.out.PersonaFacultadRepository;
import um.haberes.core.hexagonal.personas.persona_facultad.infrastructure.persistence.mapper.PersonaFacultadMapper;
import um.haberes.core.hexagonal.personas.persona_facultad.infrastructure.persistence.repository.JpaPersonaFacultadRepository;

@Component
@RequiredArgsConstructor
public class JpaPersonaFacultadRepositoryAdapter implements PersonaFacultadRepository {

    private final JpaPersonaFacultadRepository jpaPersonaFacultadRepository;

    private final PersonaFacultadMapper personaFacultadMapper;

    @Override
    public List<PersonaFacultad> findAllByLegajoId(Long legajoId) {
        return jpaPersonaFacultadRepository.findAllByLegajoId(legajoId).stream()
                .map(personaFacultadMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonaFacultad> findAllByFacultadId(Integer facultadId) {
        return jpaPersonaFacultadRepository.findAllByFacultadId(facultadId).stream()
                .map(personaFacultadMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PersonaFacultad> findByLegajoIdAndFacultadId(Long legajoId, Integer facultadId) {
        return jpaPersonaFacultadRepository.findFirstByLegajoIdAndFacultadId(legajoId, facultadId)
                .map(personaFacultadMapper::toDomain);
    }

    @Override
    public PersonaFacultad save(PersonaFacultad personaFacultad) {
        return personaFacultadMapper.toDomain(
                jpaPersonaFacultadRepository.save(personaFacultadMapper.toEntity(personaFacultad)));
    }

    @Override
    public void deleteByLegajoIdAndFacultadId(Long legajoId, Integer facultadId) {
        jpaPersonaFacultadRepository.deleteByLegajoIdAndFacultadId(legajoId, facultadId);
    }
}
