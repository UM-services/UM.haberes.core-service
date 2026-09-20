package um.haberes.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import um.haberes.core.model.PersonaFacultadEntity;
import um.haberes.core.service.PersonaFacultadService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PersonaFacultadControllerTest {

    @Mock
    private PersonaFacultadService service;

    private PersonaFacultadController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new PersonaFacultadController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private PersonaFacultadEntity samplePersonaFacultad() {
        PersonaFacultadEntity personaFacultad = new PersonaFacultadEntity();
        personaFacultad.setPersonaFacultadId(3L);
        personaFacultad.setLegajoId(100L);
        personaFacultad.setFacultadId(1);
        return personaFacultad;
    }

    private static final String PERSONA_FACULTAD_JSON = """
            {
              "personaFacultadId": 3,
              "legajoId": 100,
              "facultadId": 1,
              "persona": null,
              "facultad": null,
              "created": null,
              "updated": null
            }
            """;

    @Test
    void findAllByFacultad_returnsOkWithListOfPersonaFacultad() throws Exception {
        when(service.findAllByFacultad(1)).thenReturn(List.of(samplePersonaFacultad()));

        mockMvc.perform(get("/api/haberes/core/personaFacultad/facultad/{facultadId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + PERSONA_FACULTAD_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByPersona_returnsOkWithListOfPersonaFacultad() throws Exception {
        when(service.findAllByPersona(100L)).thenReturn(List.of(samplePersonaFacultad()));

        mockMvc.perform(get("/api/haberes/core/personaFacultad/persona/{legajoId}", 100))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + PERSONA_FACULTAD_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void add_returnsOkWithPersonaFacultadBody() throws Exception {
        when(service.add(any(PersonaFacultadEntity.class))).thenReturn(samplePersonaFacultad());

        mockMvc.perform(post("/api/haberes/core/personaFacultad/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(samplePersonaFacultad())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(PERSONA_FACULTAD_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void deleteByUnique_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/personaFacultad/unique/{legajoId}/{facultadId}", 100, 1))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
}
