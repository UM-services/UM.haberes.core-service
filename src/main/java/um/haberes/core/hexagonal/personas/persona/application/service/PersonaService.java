package um.haberes.core.hexagonal.personas.persona.application.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona.application.exception.PersonaException;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.model.PersonaSearch;
import um.haberes.core.hexagonal.personas.persona.domain.model.UploadedFile;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.CreatePersonaUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetAllPersonasUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetDocentesUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetNoDocentesUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetPersonaByDocumentoUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetPersonaByLegajoUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetPersonasByDesarraigoUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetPersonasByFacultadUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetPersonasByFiltroUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetPersonasByLegajoIdsUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetPersonasBySemestreUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetPersonasLiquidablesUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetPersonasLiquidadosUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetPersonasOrderByDependenciaUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.SaveAllPersonasUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.SearchPersonasUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.UpdatePersonaUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.UploadContactosUseCase;

@Service
@RequiredArgsConstructor
public class PersonaService {

    private final GetAllPersonasUseCase getAllPersonasUseCase;
    private final GetDocentesUseCase getDocentesUseCase;
    private final GetNoDocentesUseCase getNoDocentesUseCase;
    private final GetPersonasBySemestreUseCase getPersonasBySemestreUseCase;
    private final SearchPersonasUseCase searchPersonasUseCase;
    private final GetPersonasByLegajoIdsUseCase getPersonasByLegajoIdsUseCase;
    private final GetPersonasByDesarraigoUseCase getPersonasByDesarraigoUseCase;
    private final GetPersonasLiquidadosUseCase getPersonasLiquidadosUseCase;
    private final GetPersonasByFiltroUseCase getPersonasByFiltroUseCase;
    private final GetPersonasLiquidablesUseCase getPersonasLiquidablesUseCase;
    private final GetPersonasOrderByDependenciaUseCase getPersonasOrderByDependenciaUseCase;
    private final GetPersonasByFacultadUseCase getPersonasByFacultadUseCase;
    private final GetPersonaByLegajoUseCase getPersonaByLegajoUseCase;
    private final GetPersonaByDocumentoUseCase getPersonaByDocumentoUseCase;
    private final CreatePersonaUseCase createPersonaUseCase;
    private final UpdatePersonaUseCase updatePersonaUseCase;
    private final SaveAllPersonasUseCase saveAllPersonasUseCase;
    private final UploadContactosUseCase uploadContactosUseCase;

    public List<Persona> findAll() {
        return getAllPersonasUseCase.getAllPersonas();
    }

    public List<Persona> findAllDocente(Integer anho, Integer mes) {
        return getDocentesUseCase.getDocentes(anho, mes);
    }

    public List<Persona> findAllNoDocente(Integer anho, Integer mes) {
        return getNoDocentesUseCase.getNoDocentes(anho, mes);
    }

    public List<Persona> findAllBySemestre(Integer anho, Integer semestre) {
        return getPersonasBySemestreUseCase.getPersonasBySemestre(anho, semestre);
    }

    public List<PersonaSearch> findByStrings(List<String> conditions) {
        return searchPersonasUseCase.searchPersonas(conditions);
    }

    public List<Persona> findAllLegajos(List<Long> legajos) {
        return getPersonasByLegajoIdsUseCase.getPersonasByLegajoIds(legajos);
    }

    public List<Persona> findAllByDesarraigo(Integer anho, Integer mes) {
        return getPersonasByDesarraigoUseCase.getPersonasByDesarraigo(anho, mes);
    }

    public List<Persona> findAllByLiquidado(Integer anho, Integer mes) {
        return getPersonasLiquidadosUseCase.getPersonasLiquidados(anho, mes);
    }

    public List<Persona> findAllByFiltro(String filtro) {
        return getPersonasByFiltroUseCase.getPersonasByFiltro(filtro);
    }

    public List<Persona> findAllLiquidables() {
        return getPersonasLiquidablesUseCase.getPersonasLiquidables();
    }

    public List<Persona> findAllOrderByDependencia() {
        return getPersonasOrderByDependenciaUseCase.getPersonasOrderByDependencia();
    }

    public List<Persona> findAllByFacultad(Integer facultadId) {
        return getPersonasByFacultadUseCase.getPersonasByFacultad(facultadId);
    }

    public Persona findByLegajoId(Long legajoId) {
        return getPersonaByLegajoUseCase.getPersonaByLegajo(legajoId)
                .orElseThrow(() -> new PersonaException(legajoId));
    }

    public Persona findByDocumento(BigDecimal documento) {
        return getPersonaByDocumentoUseCase.getPersonaByDocumento(documento)
                .orElseThrow(() -> new PersonaException(documento));
    }

    public Persona add(Persona persona) {
        return createPersonaUseCase.createPersona(persona);
    }

    public Persona update(Persona persona, Long legajoId) {
        return updatePersonaUseCase.updatePersona(legajoId, persona)
                .orElseThrow(() -> new PersonaException(legajoId));
    }

    public List<Persona> saveall(List<Persona> personas) {
        return saveAllPersonasUseCase.saveAllPersonas(personas);
    }

    public List<Persona> upload(UploadedFile file) {
        return uploadContactosUseCase.uploadContactos(file);
    }
}
