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
import um.haberes.core.exception.AnotadorException;
import um.haberes.core.kotlin.model.Anotador;
import um.haberes.core.service.AnotadorService;

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
class AnotadorControllerTest {

    @Mock
    private AnotadorService service;

    @InjectMocks
    private AnotadorController controller;

    private MockMvc mockMvc;

    private static final String ANOTADOR_JSON = """
            {
              "anotadorId": 10,
              "legajoId": 100,
              "anho": 2024,
              "mes": 6,
              "facultadId": 1,
              "anotacion": "Observacion de cargo",
              "visado": 1,
              "ipVisado": "10.0.0.1",
              "user": "admin",
              "respuesta": "conforme",
              "autorizado": 0,
              "rechazado": 0,
              "rectorado": 0,
              "transferido": 0,
              "persona": null,
              "facultad": null,
              "created": null,
              "updated": null
            }
            """;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private Anotador sampleAnotador() {
        Anotador anotador = new Anotador();
        anotador.setAnotadorId(10L);
        anotador.setLegajoId(100L);
        anotador.setAnho(2024);
        anotador.setMes(6);
        anotador.setFacultadId(1);
        anotador.setAnotacion("Observacion de cargo");
        anotador.setVisado((byte) 1);
        anotador.setIpVisado("10.0.0.1");
        anotador.setUser("admin");
        anotador.setRespuesta("conforme");
        anotador.setAutorizado((byte) 0);
        anotador.setRechazado((byte) 0);
        anotador.setRectorado((byte) 0);
        anotador.setTransferido((byte) 0);
        return anotador;
    }

    @Test
    void findAllByLegajo_returnsOkWithListOfAnotadores() throws Exception {
        when(service.findAllByLegajo(100L)).thenReturn(List.of(sampleAnotador()));

        mockMvc.perform(get("/api/haberes/core/anotador/legajo/{legajoId}", 100))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + ANOTADOR_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findPendientes_returnsOkWithListOfAnotadores() throws Exception {
        when(service.findPendientes(2024, 6)).thenReturn(List.of(sampleAnotador()));

        mockMvc.perform(get("/api/haberes/core/anotador/pendiente/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + ANOTADOR_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findPendientesByFiltro_returnsOkWithListOfAnotadores() throws Exception {
        when(service.findPendientesFiltro(2024, 6, "lopez")).thenReturn(List.of(sampleAnotador()));

        mockMvc.perform(get("/api/haberes/core/anotador/pendientefiltro/{anho}/{mes}/{filtro}", 2024, 6, "lopez"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + ANOTADOR_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findPendientesByFacultad_returnsOkWithListOfAnotadores() throws Exception {
        when(service.findPendientesByFacultad(1, 2024, 6)).thenReturn(List.of(sampleAnotador()));

        mockMvc.perform(get("/api/haberes/core/anotador/pendientefacultad/{facultadId}/{anho}/{mes}", 1, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + ANOTADOR_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findRevisados_returnsOkWithListOfAnotadores() throws Exception {
        when(service.findRevisados(2024, 6)).thenReturn(List.of(sampleAnotador()));

        mockMvc.perform(get("/api/haberes/core/anotador/revisado/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + ANOTADOR_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findRevisadosByFiltro_returnsOkWithListOfAnotadores() throws Exception {
        when(service.findRevisadosFiltro(2024, 6, "lopez")).thenReturn(List.of(sampleAnotador()));

        mockMvc.perform(get("/api/haberes/core/anotador/revisadofiltro/{anho}/{mes}/{filtro}", 2024, 6, "lopez"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + ANOTADOR_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findRevisadosByFacultad_returnsOkWithListOfAnotadores() throws Exception {
        when(service.findRevisadosByFacultad(1, 2024, 6)).thenReturn(List.of(sampleAnotador()));

        mockMvc.perform(get("/api/haberes/core/anotador/revisadofacultad/{facultadId}/{anho}/{mes}", 1, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + ANOTADOR_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAutorizadosByFacultad_returnsOkWithListOfAnotadores() throws Exception {
        when(service.findAutorizadosByFacultad(1, 2024, 6)).thenReturn(List.of(sampleAnotador()));

        mockMvc.perform(get("/api/haberes/core/anotador/autorizadofacultad/{facultadId}/{anho}/{mes}", 1, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + ANOTADOR_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findRechazadosByFacultad_returnsOkWithListOfAnotadores() throws Exception {
        when(service.findRechazadosByFacultad(1, 2024, 6)).thenReturn(List.of(sampleAnotador()));

        mockMvc.perform(get("/api/haberes/core/anotador/rechazadofacultad/{facultadId}/{anho}/{mes}", 1, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + ANOTADOR_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByAnotadorId_returnsOkWithAnotadorBody() throws Exception {
        when(service.findByAnotadorId(10L)).thenReturn(sampleAnotador());

        mockMvc.perform(get("/api/haberes/core/anotador/{anotadorId}", 10))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ANOTADOR_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void findByAnotadorId_whenServiceThrowsAnotadorException_returnsBadRequest() throws Exception {
        when(service.findByAnotadorId(99L)).thenThrow(new AnotadorException(99L));

        mockMvc.perform(get("/api/haberes/core/anotador/{anotadorId}", 99))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_returnsOkWithSavedAnotador() throws Exception {
        String requestJson = new ObjectMapper().writeValueAsString(sampleAnotador());
        when(service.add(any(Anotador.class))).thenReturn(sampleAnotador());

        mockMvc.perform(post("/api/haberes/core/anotador/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ANOTADOR_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithUpdatedAnotador() throws Exception {
        String requestJson = new ObjectMapper().writeValueAsString(sampleAnotador());
        when(service.update(any(Anotador.class), eq(10L))).thenReturn(sampleAnotador());

        mockMvc.perform(put("/api/haberes/core/anotador/{anotadorId}", 10)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ANOTADOR_JSON, JsonCompareMode.STRICT));
    }
}
