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
import um.haberes.core.exception.AcreditacionException;
import um.haberes.core.kotlin.model.Acreditacion;
import um.haberes.core.service.AcreditacionService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AcreditacionControllerTest {

    @Mock
    private AcreditacionService service;

    @InjectMocks
    private AcreditacionController controller;

    private MockMvc mockMvc;

    private static final String ACREDITACION_JSON = """
            {
              "acreditacionId": 1,
              "anho": 2024,
              "mes": 6,
              "acreditado": 1,
              "limiteNovedades": null,
              "fechaContable": null,
              "ordenContable": 5,
              "sueldosOriginal": 1000.50,
              "sueldosAjustados": 1200.75,
              "contribucionesPatronales": 300.25,
              "created": null,
              "updated": null
            }
            """;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private Acreditacion sampleAcreditacion() {
        Acreditacion acreditacion = new Acreditacion();
        acreditacion.setAcreditacionId(1L);
        acreditacion.setAnho(2024);
        acreditacion.setMes(6);
        acreditacion.setAcreditado((byte) 1);
        acreditacion.setOrdenContable(5);
        acreditacion.setSueldosOriginal(new BigDecimal("1000.50"));
        acreditacion.setSueldosAjustados(new BigDecimal("1200.75"));
        acreditacion.setContribucionesPatronales(new BigDecimal("300.25"));
        return acreditacion;
    }

    @Test
    void findAll_returnsOkWithListOfAcreditaciones() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleAcreditacion()));

        mockMvc.perform(get("/api/haberes/core/acreditacion/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + ACREDITACION_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByAcreditacionId_returnsOkWithAcreditacionBody() throws Exception {
        when(service.findByAcreditacionId(1L)).thenReturn(sampleAcreditacion());

        mockMvc.perform(get("/api/haberes/core/acreditacion/{acreditacionId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ACREDITACION_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void findByAcreditacionId_whenServiceThrowsAcreditacionException_returnsBadRequest() throws Exception {
        when(service.findByAcreditacionId(99L)).thenThrow(new AcreditacionException(99L));

        mockMvc.perform(get("/api/haberes/core/acreditacion/{acreditacionId}", 99))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByPeriodo_returnsOkWithAcreditacionBody() throws Exception {
        when(service.findByPeriodo(2024, 6)).thenReturn(sampleAcreditacion());

        mockMvc.perform(get("/api/haberes/core/acreditacion/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ACREDITACION_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void findByPeriodo_whenServiceThrowsAcreditacionException_returnsBadRequest() throws Exception {
        when(service.findByPeriodo(2024, 1)).thenThrow(new AcreditacionException(2024, 1));

        mockMvc.perform(get("/api/haberes/core/acreditacion/periodo/{anho}/{mes}", 2024, 1))
                .andExpect(status().isBadRequest());
    }

    @Test
    void delete_returnsNoContentWithEmptyBody() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/acreditacion/{acreditacionId}", 1))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    void add_returnsOkWithSavedAcreditacion() throws Exception {
        String requestJson = new ObjectMapper().writeValueAsString(sampleAcreditacion());
        when(service.add(any(Acreditacion.class))).thenReturn(sampleAcreditacion());

        mockMvc.perform(post("/api/haberes/core/acreditacion/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ACREDITACION_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void add_whenServiceThrowsAcreditacionException_returnsBadRequest() throws Exception {
        String requestJson = new ObjectMapper().writeValueAsString(sampleAcreditacion());
        when(service.add(any(Acreditacion.class))).thenThrow(new AcreditacionException(2L));

        mockMvc.perform(post("/api/haberes/core/acreditacion/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_returnsOkWithUpdatedAcreditacion() throws Exception {
        String requestJson = new ObjectMapper().writeValueAsString(sampleAcreditacion());
        when(service.update(any(Acreditacion.class), eq(1L))).thenReturn(sampleAcreditacion());

        mockMvc.perform(put("/api/haberes/core/acreditacion/{acreditacionId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ACREDITACION_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void update_whenServiceThrowsAcreditacionException_returnsBadRequest() throws Exception {
        String requestJson = new ObjectMapper().writeValueAsString(sampleAcreditacion());
        when(service.update(any(Acreditacion.class), eq(99L))).thenThrow(new AcreditacionException(99L));

        mockMvc.perform(put("/api/haberes/core/acreditacion/{acreditacionId}", 99)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }
}
