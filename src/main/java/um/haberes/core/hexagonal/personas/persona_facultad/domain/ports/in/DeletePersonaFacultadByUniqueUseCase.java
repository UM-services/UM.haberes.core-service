package um.haberes.core.hexagonal.personas.persona_facultad.domain.ports.in;

public interface DeletePersonaFacultadByUniqueUseCase {

    void deletePersonaFacultadByUnique(Long legajoId, Integer facultadId);
}
