package um.haberes.core.hexagonal.personas.persona_facultad.infrastructure.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import um.haberes.core.hexagonal.facultad.domain.model.Facultad;
import um.haberes.core.hexagonal.facultad.infrastructure.web.mapper.FacultadDtoMapper;
import um.haberes.core.hexagonal.geografica.infrastructure.web.mapper.GeograficaDtoMapper;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.web.mapper.DependenciaDtoMapper;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.mapper.PersonaDtoMapper;
import um.haberes.core.hexagonal.personas.persona_facultad.application.exception.PersonaFacultadException;
import um.haberes.core.hexagonal.personas.persona_facultad.application.service.PersonaFacultadService;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.model.PersonaFacultad;
import um.haberes.core.hexagonal.personas.persona_facultad.infrastructure.web.mapper.PersonaFacultadDtoMapper;

@ExtendWith(MockitoExtension.class)
class PersonaFacultadControllerTest {

    private static final Long LEGAJO = 100L;

    private static final Integer FACULTAD = 1;

    @Mock
    private PersonaFacultadService service;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        FacultadDtoMapper facultadDtoMapper = new FacultadDtoMapper();
        PersonaDtoMapper personaDtoMapper = new PersonaDtoMapper(
                new DependenciaDtoMapper(facultadDtoMapper, new GeograficaDtoMapper()));
        PersonaFacultadDtoMapper dtoMapper = new PersonaFacultadDtoMapper(personaDtoMapper, facultadDtoMapper);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new PersonaFacultadController(service, dtoMapper))
                .build();
    }

    private PersonaFacultad sample() {
        return PersonaFacultad.builder()
                .personaFacultadId(3L)
                .legajoId(LEGAJO)
                .facultadId(FACULTAD)
                .persona(Persona.builder()
                        .legajoId(LEGAJO)
                        .documento(new BigDecimal("30111222"))
                        .apellido("PEREZ")
                        .nombre("ANA")
                        .build())
                .facultad(Facultad.builder().facultadId(FACULTAD).nombre("INGENIERIA").build())
                .build();
    }

    @Test
    void findAllByPersonaDevuelveElContratoEsperado() throws Exception {
        when(service.findAllByPersona(LEGAJO)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/haberes/core/personaFacultad/persona/{legajoId}", LEGAJO))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].personaFacultadId").value(3))
                .andExpect(jsonPath("$[0].legajoId").value(LEGAJO))
                .andExpect(jsonPath("$[0].facultadId").value(FACULTAD))
                .andExpect(jsonPath("$[0].persona.apellido").value("PEREZ"))
                .andExpect(jsonPath("$[0].persona.nombre").value("ANA"))
                .andExpect(jsonPath("$[0].facultad.nombre").value("INGENIERIA"));
    }

    @Test
    void findAllByFacultadDevuelveElContratoEsperado() throws Exception {
        when(service.findAllByFacultad(FACULTAD)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/haberes/core/personaFacultad/facultad/{facultadId}", FACULTAD))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].facultad.facultadId").value(FACULTAD))
                .andExpect(jsonPath("$[0].persona.legajoId").value(LEGAJO));
    }

    @Test
    void addDevuelveLaFacultadHabilitada() throws Exception {
        when(service.add(any(PersonaFacultad.class))).thenReturn(sample());

        mockMvc.perform(post("/api/haberes/core/personaFacultad/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"legajoId\":100,\"facultadId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personaFacultadId").value(3))
                .andExpect(jsonPath("$.facultad.nombre").value("INGENIERIA"));
    }

    @Test
    void addDuplicadoDevuelve409() throws Exception {
        when(service.add(any(PersonaFacultad.class)))
                .thenThrow(PersonaFacultadException.alreadyExists(LEGAJO, FACULTAD));

        mockMvc.perform(post("/api/haberes/core/personaFacultad/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"legajoId\":100,\"facultadId\":1}"))
                .andExpect(status().isConflict());
    }

    @Test
    void deleteByUniqueDevuelve204() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/personaFacultad/unique/{legajoId}/{facultadId}", LEGAJO, FACULTAD))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
}
