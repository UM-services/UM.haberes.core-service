package um.haberes.core.hexagonal.personas.persona_facultad.infrastructure.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import um.haberes.core.hexagonal.facultad.domain.model.Facultad;
import um.haberes.core.hexagonal.facultad.infrastructure.web.mapper.FacultadDtoMapper;
import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import um.haberes.core.hexagonal.geografica.infrastructure.web.mapper.GeograficaDtoMapper;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.web.mapper.DependenciaDtoMapper;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.mapper.PersonaDtoMapper;
import um.haberes.core.hexagonal.personas.persona_facultad.application.service.PersonaFacultadService;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.model.PersonaFacultad;
import um.haberes.core.hexagonal.personas.persona_facultad.infrastructure.web.mapper.PersonaFacultadDtoMapper;

/**
 * Contrato de compatibilidad con el cliente legacy `bonos.vb6`.
 *
 * El formulario `frmAsigCargo` sigue operando contra estos endpoints y parsea el JSON campo
 * por campo (`clsMODPersonaFacultad.fillObject`, `clsMODPersona.fillObject`,
 * `clsMODFacultad.fillObject`, `clsMODDependencia.fillObject`). Este test fija, como
 * contrato, los nombres que el VB6 lee: si alguno desaparece del response, falla acá en vez
 * de fallar en producción.
 */
@ExtendWith(MockitoExtension.class)
class PersonaFacultadLegacyContractTest {

    private static final Long LEGAJO = 100L;

    private static final Integer FACULTAD = 1;

    /** `clsMODPersonaFacultad.fillObject`. */
    private static final Set<String> PERSONA_FACULTAD_FIELDS = Set.of(
            "personaFacultadId", "legajoId", "facultadId", "persona", "facultad");

    /** `clsMODPersona.fillObject`. */
    private static final Set<String> PERSONA_FIELDS = Set.of(
            "legajoId", "documento", "apellido", "nombre", "nacimiento", "altaDocente", "ajusteDocente",
            "altaAdministrativa", "ajusteAdministrativo", "reemplazoDesarraigo", "mitadDesarraigo", "cuil",
            "estadoCivil", "situacionId", "posgrado", "estado", "liquida", "estadoAfip", "dependenciaId",
            "salida", "obraSocial", "actividadAfip", "localidadAfip", "situacionAfip",
            "modeloContratacionAfip", "directivoEtec", "dependencia");

    /** `clsMODFacultad.fillObject`. */
    private static final Set<String> FACULTAD_FIELDS = Set.of(
            "facultadId", "nombre", "reducido", "server", "backendServer", "backendPort", "dbName", "dsn");

    /** `clsMODDependencia.fillObject`. */
    private static final Set<String> DEPENDENCIA_FIELDS = Set.of(
            "dependenciaId", "nombre", "acronimo", "facultadId", "geograficaId", "facultad", "geografica");

    private final ObjectMapper objectMapper = new ObjectMapper();

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

    @Test
    void elResponseDePersonaConservaTodosLosCamposQueLeeElVb6() throws Exception {
        when(service.findAllByPersona(LEGAJO)).thenReturn(List.of(sample()));

        String body = mockMvc.perform(get("/api/haberes/core/personaFacultad/persona/{legajoId}", LEGAJO))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(body).startsWith("[");
        JsonNode root = objectMapper.readTree(body).get(0);

        assertThat(fieldNames(root)).containsAll(PERSONA_FACULTAD_FIELDS);
        assertThat(fieldNames(root.get("persona"))).containsAll(PERSONA_FIELDS);
        assertThat(fieldNames(root.get("facultad"))).containsAll(FACULTAD_FIELDS);
        assertThat(fieldNames(root.get("persona").get("dependencia"))).containsAll(DEPENDENCIA_FIELDS);

        // Tipos que el VB6 vuelca directo a TextBox / ComboBox.
        assertThat(root.get("legajoId").isNumber()).isTrue();
        assertThat(root.get("facultadId").isNumber()).isTrue();
        assertThat(root.get("persona").get("apellido").asText()).isEqualTo("PEREZ");
        assertThat(root.get("facultad").get("nombre").asText()).isEqualTo("INGENIERIA");
        // Las fechas conservan el prefijo ISO que parsea modConv.JSON2Date.
        assertThat(root.get("persona").get("nacimiento").asText())
                .matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*");
    }

    @Test
    void elPostAceptaElPayloadDelLegacyConPersonaFacultadId() throws Exception {
        when(service.add(any(PersonaFacultad.class))).thenReturn(sample());

        // Payload exacto de clsMODPersonaFacultad.makeParams (bonos.vb6).
        mockMvc.perform(post("/api/haberes/core/personaFacultad/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"personaFacultadId\":null,\"legajoId\":100,\"facultadId\":1}"))
                .andExpect(status().isOk());

        ArgumentCaptor<PersonaFacultad> captor = ArgumentCaptor.forClass(PersonaFacultad.class);
        verify(service).add(captor.capture());
        assertThat(captor.getValue().getLegajoId()).isEqualTo(LEGAJO);
        assertThat(captor.getValue().getFacultadId()).isEqualTo(FACULTAD);
    }

    @Test
    void laBajaDevuelveCuerpoVacioPorqueElVb6EvaluaResponseTextVacio() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/personaFacultad/unique/{legajoId}/{facultadId}", LEGAJO, FACULTAD))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    void sinRegistrosDevuelveElArrayVacioQueEsperaCollectionJson() throws Exception {
        when(service.findAllByPersona(LEGAJO)).thenReturn(List.of());

        mockMvc.perform(get("/api/haberes/core/personaFacultad/persona/{legajoId}", LEGAJO))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    private Set<String> fieldNames(JsonNode node) {
        Set<String> names = new java.util.HashSet<>();
        node.fieldNames().forEachRemaining(names::add);
        return names;
    }

    private PersonaFacultad sample() {
        Facultad facultad = Facultad.builder()
                .facultadId(FACULTAD)
                .nombre("INGENIERIA")
                .reducido("ING")
                .server("srv")
                .backendServer("backend")
                .backendPort(8080)
                .dbName("haberes")
                .dsn("dsn")
                .build();
        Geografica geografica = Geografica.builder()
                .geograficaId(1)
                .nombre("CENTRAL")
                .reducido("C")
                .desarraigo(BigDecimal.ZERO)
                .build();
        Dependencia dependencia = Dependencia.builder()
                .dependenciaId(7)
                .nombre("SISTEMAS")
                .acronimo("SIS")
                .facultadId(FACULTAD)
                .geograficaId(1)
                .facultad(facultad)
                .geografica(geografica)
                .build();
        Persona persona = Persona.builder()
                .legajoId(LEGAJO)
                .documento(new BigDecimal("30111222"))
                .apellido("PEREZ")
                .nombre("ANA")
                .nacimiento(OffsetDateTime.parse("1980-05-12T00:00:00Z"))
                .dependenciaId(7)
                .dependencia(dependencia)
                .build();
        return PersonaFacultad.builder()
                .personaFacultadId(3L)
                .legajoId(LEGAJO)
                .facultadId(FACULTAD)
                .persona(persona)
                .facultad(facultad)
                .build();
    }
}
