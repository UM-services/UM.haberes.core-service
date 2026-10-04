package um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.adapter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.PersonaRepository;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.mapper.PersonaMapper;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.repository.JpaPersonaRepository;

@Component
@RequiredArgsConstructor
public class JpaPersonaRepositoryAdapter implements PersonaRepository {

    private static final Sort POR_APELLIDO_NOMBRE = Sort.by("apellido").ascending()
            .and(Sort.by("nombre").ascending());

    private static final Sort POR_LEGAJO_ID = Sort.by("legajoId").ascending();

    private final JpaPersonaRepository jpaPersonaRepository;

    private final PersonaMapper personaMapper;

    @Override
    public Persona save(Persona persona) {
        PersonaEntity saved = jpaPersonaRepository.save(personaMapper.toEntity(persona));
        return personaMapper.toDomain(saved);
    }

    @Override
    public List<Persona> saveAll(List<Persona> personas) {
        List<PersonaEntity> entities = personas.stream()
                .map(personaMapper::toEntity)
                .collect(Collectors.toList());
        return jpaPersonaRepository.saveAll(entities).stream()
                .map(personaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Persona> update(Long legajoId, Persona persona) {
        if (!jpaPersonaRepository.existsById(legajoId)) {
            return Optional.empty();
        }
        PersonaEntity entity = personaMapper.toEntity(persona);
        entity.setLegajoId(legajoId);
        PersonaEntity saved = jpaPersonaRepository.save(entity);
        return Optional.of(personaMapper.toDomain(saved));
    }

    @Override
    public Optional<Persona> findByLegajoId(Long legajoId) {
        return jpaPersonaRepository.findByLegajoId(legajoId).map(personaMapper::toDomain);
    }

    @Override
    public Optional<Persona> findByDocumento(BigDecimal documento) {
        return jpaPersonaRepository.findTopByDocumentoOrderByLegajoIdDesc(documento).map(personaMapper::toDomain);
    }

    @Override
    public List<Persona> findAll() {
        return jpaPersonaRepository.findAll().stream()
                .map(personaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Persona> findAllOrderByDependencia() {
        Sort sort = Sort.by("dependencia.dependenciaId")
                .and(Sort.by("apellido").and(Sort.by("nombre").and(Sort.by("legajoId"))));
        return jpaPersonaRepository.findAll(sort).stream()
                .map(personaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Persona> findAllLiquidables() {
        return jpaPersonaRepository.findAllByLiquida("S", Sort.by("apellido").and(Sort.by("nombre"))).stream()
                .map(personaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Persona> findAllByApellidoContaining(String filtro) {
        return jpaPersonaRepository.findAllByApellidoLike("%" + filtro + "%").stream()
                .map(personaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Persona> findAllByLegajoIds(List<Long> legajoIds) {
        return findAllByLegajoIdsConOrden(legajoIds, Sort.unsorted());
    }

    @Override
    public List<Persona> findAllByLegajoIdsOrderedByNombre(List<Long> legajoIds) {
        return findAllByLegajoIdsConOrden(legajoIds, POR_APELLIDO_NOMBRE);
    }

    @Override
    public List<Persona> findAllByLegajoIdsOrderedByLegajoId(List<Long> legajoIds) {
        return findAllByLegajoIdsConOrden(legajoIds, POR_LEGAJO_ID);
    }

    private List<Persona> findAllByLegajoIdsConOrden(List<Long> legajoIds, Sort sort) {
        return jpaPersonaRepository.findAllByLegajoIdIn(legajoIds, sort).stream()
                .map(personaMapper::toDomain)
                .collect(Collectors.toList());
    }
}
