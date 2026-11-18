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
import um.haberes.core.hexagonal.liquidaciones.acreditacion.application.exception.AcreditacionException;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.application.service.AcreditacionService;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.infrastructure.web.controller.AcreditacionController;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.infrastructure.web.mapper.AcreditacionDtoMapper;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
              "contribucionesPatronales": 300.25
                                        }
            """;

    @BeforeEach
    void setUp() {
        controller = new AcreditacionController(service, new AcreditacionDtoMapper());
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
    void findByAcreditacionId_whenServiceThrowsAcreditacionException_returnsNotFound() throws Exception {
        when(service.findByAcreditacionId(99L)).thenThrow(new AcreditacionException(99L));

        mockMvc.perform(get("/api/haberes/core/acreditacion/{acreditacionId}", 99))
                .andExpect(status().isNotFound());
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
    void findByPeriodo_whenServiceThrowsAcreditacionException_returnsNotFound() throws Exception {
        when(service.findByPeriodo(2024, 1)).thenThrow(new AcreditacionException(2024, 1));

        mockMvc.perform(get("/api/haberes/core/acreditacion/periodo/{anho}/{mes}", 2024, 1))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_returnsNoContentWithEmptyBody() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/acreditacion/{acreditacionId}", 1))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    void add_returnsCreatedWithSavedAcreditacion() throws Exception {
        String requestJson = new ObjectMapper().writeValueAsString(sampleAcreditacion());
        when(service.add(any(Acreditacion.class))).thenReturn(sampleAcreditacion());

        mockMvc.perform(post("/api/haberes/core/acreditacion/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ACREDITACION_JSON, JsonCompareMode.STRICT));
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
    void update_whenServiceThrowsAcreditacionException_returnsNotFound() throws Exception {
        String requestJson = new ObjectMapper().writeValueAsString(sampleAcreditacion());
        when(service.update(any(Acreditacion.class), eq(99L))).thenThrow(new AcreditacionException(99L));

        mockMvc.perform(put("/api/haberes/core/acreditacion/{acreditacionId}", 99)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());
    }

    private Acreditacion sampleAcreditacionConLimite() {
        Acreditacion acreditacion = sampleAcreditacion();
        acreditacion.setLimiteNovedades(OffsetDateTime.of(2026, 11, 30, 23, 59, 59, 0, ZoneOffset.ofHours(-3)));
        return acreditacion;
    }

    private static final String ACREDITACION_CON_LIMITE_JSON = """
            {
              "acreditacionId": 1,
              "anho": 2024,
              "mes": 6,
              "acreditado": 1,
              "limiteNovedades": "2026-12-01T02:59:59Z",
              "fechaContable": null,
              "ordenContable": 5,
              "sueldosOriginal": 1000.50,
              "sueldosAjustados": 1200.75,
              "contribucionesPatronales": 300.25
                                        }
            """;

    @Test
    void updateLimiteNovedades_returnsOkWithOnlyTheLimitChanged() throws Exception {
        String requestJson = """
                { "limiteNovedades": "2026-11-30T23:59:59-0300" }
                """;
        when(service.updateLimiteNovedades(eq(1L), any(OffsetDateTime.class)))
                .thenReturn(sampleAcreditacionConLimite());

        mockMvc.perform(patch("/api/haberes/core/acreditacion/{acreditacionId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ACREDITACION_CON_LIMITE_JSON, JsonCompareMode.STRICT));

        verify(service).updateLimiteNovedades(
                eq(1L),
                eq(OffsetDateTime.of(2026, 12, 1, 2, 59, 59, 0, ZoneOffset.UTC)));
    }

    @Test
    void updateLimiteNovedades_whenServiceThrowsAcreditacionException_returnsNotFound() throws Exception {
        String requestJson = """
                { "limiteNovedades": "2026-11-30T23:59:59-0300" }
                """;
        when(service.updateLimiteNovedades(eq(99L), any(OffsetDateTime.class)))
                .thenThrow(new AcreditacionException(99L));

        mockMvc.perform(patch("/api/haberes/core/acreditacion/{acreditacionId}", 99)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateLimiteNovedades_withoutLimite_returnsBadRequest() throws Exception {
        mockMvc.perform(patch("/api/haberes/core/acreditacion/{acreditacionId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());

        verify(service, never()).updateLimiteNovedades(any(), any());
    }

    @Test
    void add_acceptsTheLimitPayloadSentByTheFrontend() throws Exception {
        String requestJson = """
                { "anho": 2026, "mes": 12, "limiteNovedades": "2026-12-15T23:59:59-0300" }
                """;
        when(service.add(any(Acreditacion.class))).thenReturn(sampleAcreditacionConLimite());

        mockMvc.perform(post("/api/haberes/core/acreditacion/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());

        verify(service).add(argThat(acreditacion ->
                acreditacion.getAnho() == 2026
                        && acreditacion.getMes() == 12
                        && acreditacion.getLimiteNovedades().equals(
                                OffsetDateTime.of(2026, 12, 16, 2, 59, 59, 0, ZoneOffset.UTC))));
    }
}
