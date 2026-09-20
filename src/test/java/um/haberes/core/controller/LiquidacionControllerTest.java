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
import um.haberes.core.exception.LiquidacionException;
import um.haberes.core.kotlin.model.Liquidacion;
import um.haberes.core.kotlin.model.view.LiquidacionPeriodo;
import um.haberes.core.service.LiquidacionService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LiquidacionControllerTest {

    @Mock
    private LiquidacionService service;

    private LiquidacionController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new LiquidacionController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private Liquidacion sampleLiquidacion() {
        Liquidacion liquidacion = new Liquidacion();
        liquidacion.setLiquidacionId(1L);
        liquidacion.setLegajoId(100L);
        liquidacion.setAnho(2024);
        liquidacion.setMes(6);
        liquidacion.setDependenciaId(7);
        liquidacion.setSalida("DOC");
        liquidacion.setTotalRemunerativo(new BigDecimal("100.50"));
        liquidacion.setTotalNoRemunerativo(new BigDecimal("20.25"));
        liquidacion.setTotalDeduccion(new BigDecimal("15.75"));
        liquidacion.setTotalNeto(new BigDecimal("105.00"));
        liquidacion.setBloqueado((byte) 0);
        liquidacion.setEstado(1);
        liquidacion.setLiquida("USER");
        return liquidacion;
    }

    private LiquidacionPeriodo sampleLiquidacionPeriodo() {
        LiquidacionPeriodo liquidacionPeriodo = new LiquidacionPeriodo();
        liquidacionPeriodo.setLiquidacionId(1L);
        liquidacionPeriodo.setLegajoId(100L);
        liquidacionPeriodo.setAnho(2024);
        liquidacionPeriodo.setMes(6);
        liquidacionPeriodo.setDependenciaId(7);
        liquidacionPeriodo.setSalida("DOC");
        liquidacionPeriodo.setTotalRemunerativo(new BigDecimal("100.50"));
        liquidacionPeriodo.setTotalNoRemunerativo(new BigDecimal("20.25"));
        liquidacionPeriodo.setTotalDeduccion(new BigDecimal("15.75"));
        liquidacionPeriodo.setTotalNeto(new BigDecimal("105.00"));
        liquidacionPeriodo.setBloqueado((byte) 0);
        liquidacionPeriodo.setPeriodo(202406L);
        return liquidacionPeriodo;
    }

    private final String expectedLiquidacion = """
            {
              "liquidacionId": 1,
              "legajoId": 100,
              "anho": 2024,
              "mes": 6,
              "fechaLiquidacion": null,
              "fechaAcreditacion": null,
              "dependenciaId": 7,
              "salida": "DOC",
              "totalRemunerativo": 100.50,
              "totalNoRemunerativo": 20.25,
              "totalDeduccion": 15.75,
              "totalNeto": 105.00,
              "bloqueado": 0,
              "estado": 1,
              "liquida": "USER",
              "persona": null,
              "dependencia": null,
              "created": null,
              "updated": null
            }
            """;

    private final String expectedLiquidacionPeriodo = """
            {
              "liquidacionId": 1,
              "legajoId": 100,
              "anho": 2024,
              "mes": 6,
              "fechaLiquidacion": null,
              "dependenciaId": 7,
              "salida": "DOC",
              "totalRemunerativo": 100.50,
              "totalNoRemunerativo": 20.25,
              "totalDeduccion": 15.75,
              "totalNeto": 105.00,
              "bloqueado": 0,
              "periodo": 202406,
              "created": null,
              "updated": null
            }
            """;

    private String liquidacionJson() throws Exception {
        return new ObjectMapper().writeValueAsString(sampleLiquidacion());
    }

    @Test
    void findAllByPeriodo_returnsOkWithListOfLiquidacion() throws Exception {
        when(service.findAllByPeriodo(2024, 6, 100)).thenReturn(List.of(sampleLiquidacion()));

        mockMvc.perform(get("/api/haberes/core/liquidacion/periodo/{anho}/{mes}/{limit}", 2024, 6, 100))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLiquidacion + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByPeriodoLegajo_returnsOkWithListOfLiquidacion() throws Exception {
        when(service.findAllByPeriodoLegajo(2024, 6, 100L, 100)).thenReturn(List.of(sampleLiquidacion()));

        mockMvc.perform(get("/api/haberes/core/liquidacion/periodolegajo/{anho}/{mes}/{legajoId}/{limit}",
                        2024, 6, 100L, 100))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLiquidacion + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllBySemestre_returnsOkWithListOfLiquidacion() throws Exception {
        when(service.findAllBySemestre(2024, 1, 100)).thenReturn(List.of(sampleLiquidacion()));

        mockMvc.perform(get("/api/haberes/core/liquidacion/semestre/{anho}/{semestre}/{limit}", 2024, 1, 100))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLiquidacion + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllBySemestreLegajo_returnsOkWithListOfLiquidacion() throws Exception {
        when(service.findAllBySemestreLegajo(2024, 1, 100L, 100)).thenReturn(List.of(sampleLiquidacion()));

        mockMvc.perform(get("/api/haberes/core/liquidacion/semestrelegajo/{anho}/{semestre}/{legajoId}/{limit}",
                        2024, 1, 100L, 100))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLiquidacion + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByLegajo_returnsOkWithListOfLiquidacion() throws Exception {
        when(service.findAllByLegajo(100L)).thenReturn(List.of(sampleLiquidacion()));

        mockMvc.perform(get("/api/haberes/core/liquidacion/legajo/{legajoId}", 100L))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLiquidacion + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByLegajoForward_returnsOkWithListOfLiquidacionPeriodo() throws Exception {
        when(service.findAllByLegajoForward(100L, 2024, 6)).thenReturn(List.of(sampleLiquidacionPeriodo()));

        mockMvc.perform(get("/api/haberes/core/liquidacion/legajoforward/{legajoId}/{anho}/{mes}", 100L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLiquidacionPeriodo + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByDependencia_returnsOkWithListOfLiquidacion() throws Exception {
        when(service.findAllByDependencia(7, 2024, 6, "DOC")).thenReturn(List.of(sampleLiquidacion()));

        mockMvc.perform(get("/api/haberes/core/liquidacion/dependencia/{dependenciaId}/{anho}/{mes}/{salida}",
                        7, 2024, 6, "DOC"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLiquidacion + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByAcreditado_returnsOkWithListOfLiquidacion() throws Exception {
        when(service.findAllByAcreditado(2024, 6)).thenReturn(List.of(sampleLiquidacion()));

        mockMvc.perform(get("/api/haberes/core/liquidacion/acreditado/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLiquidacion + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByLiquidacionId_returnsOkWithLiquidacion() throws Exception {
        when(service.findByLiquidacionId(1L)).thenReturn(sampleLiquidacion());

        mockMvc.perform(get("/api/haberes/core/liquidacion/{liquidacionId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedLiquidacion, JsonCompareMode.STRICT));
    }

    @Test
    void findByLiquidacionId_whenServiceThrowsLiquidacionException_returnsBadRequest() throws Exception {
        when(service.findByLiquidacionId(99L)).thenThrow(new LiquidacionException(99L));

        mockMvc.perform(get("/api/haberes/core/liquidacion/{liquidacionId}", 99L))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByUnique_returnsOkWithLiquidacion() throws Exception {
        when(service.findByLegajoIdAndAnhoAndMes(100L, 2024, 6)).thenReturn(sampleLiquidacion());

        mockMvc.perform(get("/api/haberes/core/liquidacion/unique/{legajoId}/{anho}/{mes}", 100L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedLiquidacion, JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_whenServiceThrowsLiquidacionException_returnsBadRequest() throws Exception {
        when(service.findByLegajoIdAndAnhoAndMes(100L, 2024, 6))
                .thenThrow(new LiquidacionException(100L, 2024, 6));

        mockMvc.perform(get("/api/haberes/core/liquidacion/unique/{legajoId}/{anho}/{mes}", 100L, 2024, 6))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_returnsOkWithSavedLiquidacion() throws Exception {
        when(service.add(any(Liquidacion.class))).thenReturn(sampleLiquidacion());

        mockMvc.perform(post("/api/haberes/core/liquidacion/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(liquidacionJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedLiquidacion, JsonCompareMode.STRICT));
    }

    @Test
    void addVersion_returnsOkWithSavedLiquidacion() throws Exception {
        when(service.addVersion(any(Liquidacion.class), any())).thenReturn(sampleLiquidacion());

        mockMvc.perform(post("/api/haberes/core/liquidacion/version/{version}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(liquidacionJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedLiquidacion, JsonCompareMode.STRICT));
    }

    @Test
    void acreditado_returnsOkWithLiquidacion() throws Exception {
        when(service.acreditado(any(Liquidacion.class), any(OffsetDateTime.class)))
                .thenReturn(sampleLiquidacion());

        mockMvc.perform(post("/api/haberes/core/liquidacion/acreditado/{fecha}", "2024-06-30T12:00:00+00:00")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(liquidacionJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedLiquidacion, JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithUpdatedLiquidacion() throws Exception {
        when(service.update(any(Liquidacion.class), anyLong())).thenReturn(sampleLiquidacion());

        mockMvc.perform(put("/api/haberes/core/liquidacion/{liquidacionId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(liquidacionJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedLiquidacion, JsonCompareMode.STRICT));
    }

    @Test
    void saveall_returnsOkWithListOfSavedLiquidacion() throws Exception {
        when(service.saveall(anyList(), any())).thenReturn(List.of(sampleLiquidacion()));

        String body = new ObjectMapper().writeValueAsString(List.of(sampleLiquidacion()));

        mockMvc.perform(put("/api/haberes/core/liquidacion/saveall/{version}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLiquidacion + "]", JsonCompareMode.STRICT));
    }

    @Test
    void updateVersion_returnsOkWithUpdatedLiquidacion() throws Exception {
        when(service.updateVersion(any(Liquidacion.class), anyLong(), any())).thenReturn(sampleLiquidacion());

        mockMvc.perform(put("/api/haberes/core/liquidacion/version/{liquidacionId}/{version}", 1L, 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(liquidacionJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedLiquidacion, JsonCompareMode.STRICT));
    }

    @Test
    void deleteByPeriodo_returnsNoContentWithEmptyBody() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/liquidacion/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
}
