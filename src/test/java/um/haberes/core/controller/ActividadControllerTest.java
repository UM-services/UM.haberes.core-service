package um.haberes.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import um.haberes.core.exception.ActividadException;
import um.haberes.core.kotlin.model.Actividad;
import um.haberes.core.kotlin.model.view.ActividadPeriodo;
import um.haberes.core.service.ActividadService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ActividadControllerTest {

    @Mock
    private ActividadService service;

    @InjectMocks
    private ActividadController controller;

    private MockMvc mockMvc;

    private static final String ACTIVIDAD_JSON = """
            {
              "actividadId": 1,
              "legajoId": 100,
              "anho": 2024,
              "mes": 6,
              "docente": 1,
              "otras": 0,
              "clases": 1,
              "dependenciaId": 10,
              "persona": null,
              "dependencia": null,
              "created": null,
              "updated": null
            }
            """;

    private static final String ACTIVIDAD_PERIODO_JSON = """
            {
              "actividadId": 1,
              "legajoId": 100,
              "anho": 2024,
              "mes": 6,
              "docente": 1,
              "otras": 0,
              "clases": 1,
              "dependenciaId": 10,
              "periodo": 202406,
              "created": null,
              "updated": null
            }
            """;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private Actividad sampleActividad() {
        Actividad actividad = new Actividad();
        actividad.setActividadId(1L);
        actividad.setLegajoId(100L);
        actividad.setAnho(2024);
        actividad.setMes(6);
        actividad.setDocente((byte) 1);
        actividad.setOtras((byte) 0);
        actividad.setClases((byte) 1);
        actividad.setDependenciaId(10);
        return actividad;
    }

    private ActividadPeriodo sampleActividadPeriodo() {
        ActividadPeriodo actividadPeriodo = new ActividadPeriodo();
        actividadPeriodo.setActividadId(1L);
        actividadPeriodo.setLegajoId(100L);
        actividadPeriodo.setAnho(2024);
        actividadPeriodo.setMes(6);
        actividadPeriodo.setDocente((byte) 1);
        actividadPeriodo.setOtras((byte) 0);
        actividadPeriodo.setClases((byte) 1);
        actividadPeriodo.setDependenciaId(10);
        actividadPeriodo.setPeriodo(202406L);
        return actividadPeriodo;
    }

    @Test
    void findAllByLegajoId_returnsOkWithListOfActividades() throws Exception {
        when(service.findAllByLegajoId(100L)).thenReturn(List.of(sampleActividad()));

        mockMvc.perform(get("/api/haberes/core/actividad/legajo/{legajoId}", 100))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + ACTIVIDAD_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_returnsOkWithActividadBody() throws Exception {
        when(service.findByUnique(100L, 2024, 6)).thenReturn(sampleActividad());

        mockMvc.perform(get("/api/haberes/core/actividad/unique/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ACTIVIDAD_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_whenServiceThrowsActividadException_returnsBadRequest() throws Exception {
        when(service.findByUnique(999L, 2024, 6)).thenThrow(new ActividadException(999L, 2024, 6));

        mockMvc.perform(get("/api/haberes/core/actividad/unique/{legajoId}/{anho}/{mes}", 999, 2024, 6))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findAllByPeriodo_returnsOkWithListOfActividadPeriodo() throws Exception {
        when(service.findAllByPeriodo(100L, 2024, 6)).thenReturn(List.of(sampleActividadPeriodo()));

        mockMvc.perform(get("/api/haberes/core/actividad/periodo/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + ACTIVIDAD_PERIODO_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void add_returnsOkWithSavedActividad() throws Exception {
        String requestJson = new ObjectMapper().writeValueAsString(sampleActividad());
        when(service.add(any(Actividad.class))).thenReturn(sampleActividad());

        mockMvc.perform(post("/api/haberes/core/actividad/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ACTIVIDAD_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithUpdatedActividad() throws Exception {
        String requestJson = new ObjectMapper().writeValueAsString(sampleActividad());
        when(service.update(any(Actividad.class), eq(1L))).thenReturn(sampleActividad());

        mockMvc.perform(put("/api/haberes/core/actividad/{actividadId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ACTIVIDAD_JSON, JsonCompareMode.STRICT));
    }
}
