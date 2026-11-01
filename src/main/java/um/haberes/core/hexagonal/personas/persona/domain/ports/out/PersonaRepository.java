package um.haberes.core.hexagonal.personas.persona.domain.ports.out;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;

public interface PersonaRepository {

    Persona save(Persona persona);

    List<Persona> saveAll(List<Persona> personas);

    Optional<Persona> update(Long legajoId, Persona persona);

    Optional<Persona> findByLegajoId(Long legajoId);

    Optional<Persona> findByDocumento(BigDecimal documento);

    List<Persona> findAll();

    List<Persona> findAllOrderByDependencia();

    List<Persona> findAllLiquidables();

    List<Persona> findAllByApellidoContaining(String filtro);

    List<Persona> findAllByLegajoIds(List<Long> legajoIds);

    List<Persona> findAllByLegajoIdsOrderedByNombre(List<Long> legajoIds);

    List<Persona> findAllByLegajoIdsOrderedByLegajoId(List<Long> legajoIds);
}
