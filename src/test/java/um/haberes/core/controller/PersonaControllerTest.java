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
import um.haberes.core.exception.PersonaException;
import um.haberes.core.kotlin.model.Persona;
import um.haberes.core.kotlin.model.view.PersonaSearch;
import um.haberes.core.service.PersonaService;
import um.haberes.core.util.transfer.FileInfo;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PersonaControllerTest {

    @Mock
    private PersonaService service;

    private PersonaController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new PersonaController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private Persona samplePersona() {
        Persona persona = new Persona();
        persona.setLegajoId(100L);
        persona.setDocumento(new BigDecimal("30123456789"));
        persona.setApellido("Perez");
        persona.setNombre("Juan");
        persona.setAjusteDocente(1);
        persona.setAjusteAdministrativo(2);
        persona.setEstadoCivil("S");
        persona.setSituacionId(1);
        persona.setReemplazoDesarraigo((byte) 0);
        persona.setMitadDesarraigo((byte) 1);
        persona.setCuil("20123456789");
        persona.setPosgrado(0);
        persona.setEstado(1);
        persona.setLiquida("S");
        persona.setEstadoAfip(1);
        persona.setDependenciaId(20);
        persona.setSalida("X");
        persona.setObraSocial(55L);
        persona.setActividadAfip(1);
        persona.setLocalidadAfip(100);
        persona.setSituacionAfip(3);
        persona.setModeloContratacionAfip(1);
        persona.setDirectivoEtec((byte) 1);
        return persona;
    }

    private String personaBodyJson() throws Exception {
        return new ObjectMapper().writeValueAsString(samplePersona());
    }

    private static final String PERSONA_JSON = """
            {
              "legajoId": 100,
              "documento": 30123456789,
              "apellido": "Perez",
              "nombre": "Juan",
              "nacimiento": null,
              "altaDocente": null,
              "ajusteDocente": 1,
              "altaAdministrativa": null,
              "ajusteAdministrativo": 2,
              "estadoCivil": "S",
              "situacionId": 1,
              "reemplazoDesarraigo": 0,
              "mitadDesarraigo": 1,
              "cuil": "20123456789",
              "posgrado": 0,
              "estado": 1,
              "liquida": "S",
              "estadoAfip": 1,
              "dependenciaId": 20,
              "salida": "X",
              "obraSocial": 55,
              "actividadAfip": 1,
              "localidadAfip": 100,
              "situacionAfip": 3,
              "modeloContratacionAfip": 1,
              "directivoEtec": 1,
              "dependencia": null,
              "afipSituacion": null,
              "apellidoNombre": "Perez, Juan",
              "created": null,
              "updated": null
            }
            """;

    private PersonaSearch samplePersonaSearch() {
        PersonaSearch search = new PersonaSearch();
        search.setLegajoId(100L);
        search.setDocumento(new BigDecimal("30123456789"));
        search.setApellido("Perez");
        search.setNombre("Juan");
        search.setSearch("PEREZ, JUAN");
        return search;
    }

    private static final String PERSONA_SEARCH_JSON = """
            {
              "legajoId": 100,
              "documento": 30123456789,
              "apellido": "Perez",
              "nombre": "Juan",
              "nacimiento": null,
              "altaDocente": null,
              "ajusteDocente": 0,
              "altaAdministrativa": null,
              "ajusteAdministrativo": 0,
              "estadoCivil": "",
              "situacionId": null,
              "reemplazoDesarraigo": 0,
              "mitadDesarraigo": 0,
              "cuil": "",
              "posgrado": 0,
              "estado": 1,
              "liquida": "S",
              "estadoAfip": 1,
              "dependenciaId": null,
              "salida": null,
              "obraSocial": null,
              "actividadAfip": null,
              "localidadAfip": null,
              "situacionAfip": null,
              "modeloContratacionAfip": null,
              "search": "PEREZ, JUAN",
              "dependencia": null,
              "created": null,
              "updated": null
            }
            """;

    @Test
    void findAll_returnsOkWithListOfPersona() throws Exception {
        when(service.findAll()).thenReturn(List.of(samplePersona()));

        mockMvc.perform(get("/api/haberes/core/persona/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + PERSONA_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllLegajos_returnsOkWithListOfPersona() throws Exception {
        when(service.findAllLegajos(anyList())).thenReturn(List.of(samplePersona()));

        mockMvc.perform(post("/api/haberes/core/persona/legajos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(List.of(100L, 200L))))
                .andExpect(status().isOk())
                .andExpect(content().json("[" + PERSONA_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllDocente_returnsOkWithListOfPersona() throws Exception {
        when(service.findAllDocente(2024, 6)).thenReturn(List.of(samplePersona()));

        mockMvc.perform(get("/api/haberes/core/persona/docente/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json("[" + PERSONA_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllNoDocente_returnsOkWithListOfPersona() throws Exception {
        when(service.findAllNoDocente(2024, 6)).thenReturn(List.of(samplePersona()));

        mockMvc.perform(get("/api/haberes/core/persona/nodocente/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json("[" + PERSONA_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllBySemestre_returnsOkWithListOfPersona() throws Exception {
        when(service.findAllBySemestre(2024, 1)).thenReturn(List.of(samplePersona()));

        mockMvc.perform(get("/api/haberes/core/persona/semestre/{anho}/{semestre}", 2024, 1))
                .andExpect(status().isOk())
                .andExpect(content().json("[" + PERSONA_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByDesarraigo_returnsOkWithListOfPersona() throws Exception {
        when(service.findAllByDesarraigo(2024, 6)).thenReturn(List.of(samplePersona()));

        mockMvc.perform(get("/api/haberes/core/persona/desarraigo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json("[" + PERSONA_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllLiquidables_returnsOkWithListOfPersona() throws Exception {
        when(service.findAllLiquidables()).thenReturn(List.of(samplePersona()));

        mockMvc.perform(get("/api/haberes/core/persona/liquidables"))
                .andExpect(status().isOk())
                .andExpect(content().json("[" + PERSONA_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByFacultad_returnsOkWithListOfPersona() throws Exception {
        when(service.findAllByFacultad(1)).thenReturn(List.of(samplePersona()));

        mockMvc.perform(get("/api/haberes/core/persona/facultad/{facultadId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("[" + PERSONA_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByStrings_returnsOkWithListOfPersonaSearch() throws Exception {
        when(service.findByStrings(anyList())).thenReturn(List.of(samplePersonaSearch()));

        mockMvc.perform(post("/api/haberes/core/persona/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(List.of("perez", "123"))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + PERSONA_SEARCH_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByLegajoId_returnsOkWithPersonaBody() throws Exception {
        when(service.findByLegajoId(100L)).thenReturn(samplePersona());

        mockMvc.perform(get("/api/haberes/core/persona/{legajoId}", 100))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(PERSONA_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void findByLegajoId_whenServiceThrowsPersonaException_returnsBadRequest() throws Exception {
        when(service.findByLegajoId(99L)).thenThrow(new PersonaException(99L));

        mockMvc.perform(get("/api/haberes/core/persona/{legajoId}", 99))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByDocumento_returnsOkWithPersonaBody() throws Exception {
        when(service.findByDocumento(any())).thenReturn(samplePersona());

        mockMvc.perform(get("/api/haberes/core/persona/documento/{documento}", "30123456789"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(PERSONA_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void findByDocumento_whenServiceThrowsPersonaException_returnsBadRequest() throws Exception {
        when(service.findByDocumento(any())).thenThrow(new PersonaException(new BigDecimal("9")));

        mockMvc.perform(get("/api/haberes/core/persona/documento/{documento}", "9"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_returnsOkWithPersonaBody() throws Exception {
        when(service.add(any(Persona.class))).thenReturn(samplePersona());

        mockMvc.perform(post("/api/haberes/core/persona/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personaBodyJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(PERSONA_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithPersonaBody() throws Exception {
        when(service.update(any(Persona.class), any())).thenReturn(samplePersona());

        mockMvc.perform(put("/api/haberes/core/persona/{legajoId}", 100)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personaBodyJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(PERSONA_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void saveAll_returnsOkWithListOfPersona() throws Exception {
        when(service.saveall(anyList())).thenReturn(List.of(samplePersona()));

        mockMvc.perform(put("/api/haberes/core/persona/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(List.of(samplePersona()))))
                .andExpect(status().isOk())
                .andExpect(content().json("[" + PERSONA_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void upload_returnsOkWithListOfPersona() throws Exception {
        when(service.upload(any(FileInfo.class))).thenReturn(List.of(samplePersona()));

        mockMvc.perform(post("/api/haberes/core/persona/upload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new FileInfo("personas.xlsx", "e30="))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + PERSONA_JSON + "]", JsonCompareMode.STRICT));
    }
}
