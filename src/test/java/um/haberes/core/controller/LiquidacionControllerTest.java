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
import um.haberes.core.hexagonal.liquidaciones.liquidacion.application.exception.LiquidacionException;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.application.service.LiquidacionService;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.LiquidacionPeriodoForward;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.web.controller.LiquidacionController;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.web.dto.LiquidacionPeriodoResponse;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.web.dto.LiquidacionRequest;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.web.dto.LiquidacionResponse;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.web.mapper.LiquidacionDtoMapper;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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

    @Mock
    private LiquidacionDtoMapper liquidacionDtoMapper;

    private LiquidacionController controller;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        controller = new LiquidacionController(service, liquidacionDtoMapper);
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

    private LiquidacionResponse sampleResponse() {
        return LiquidacionResponse.builder()
                .liquidacionId(1L)
                .legajoId(100L)
                .anho(2024)
                .mes(6)
                .dependenciaId(7)
                .salida("DOC")
                .totalRemunerativo(new BigDecimal("100.50"))
                .totalNoRemunerativo(new BigDecimal("20.25"))
                .totalDeduccion(new BigDecimal("15.75"))
                .totalNeto(new BigDecimal("105.00"))
                .bloqueado((byte) 0)
                .estado(1)
                .liquida("USER")
                .key("100.2024.6")
                .build();
    }

    private LiquidacionRequest sampleRequest() {
        LiquidacionRequest request = new LiquidacionRequest();
        request.setLiquidacionId(1L);
        request.setLegajoId(100L);
        request.setAnho(2024);
        request.setMes(6);
        request.setDependenciaId(7);
        request.setSalida("DOC");
        request.setTotalRemunerativo(new BigDecimal("100.50"));
        request.setTotalNoRemunerativo(new BigDecimal("20.25"));
        request.setTotalDeduccion(new BigDecimal("15.75"));
        request.setTotalNeto(new BigDecimal("105.00"));
        request.setBloqueado((byte) 0);
        request.setEstado(1);
        request.setLiquida("USER");
        return request;
    }

    private LiquidacionPeriodoForward sampleLiquidacionPeriodo() {
        return LiquidacionPeriodoForward.builder()
                .liquidacionId(1L)
                .legajoId(100L)
                .anho(2024)
                .mes(6)
                .dependenciaId(7)
                .salida("DOC")
                .totalRemunerativo(new BigDecimal("100.50"))
                .totalNoRemunerativo(new BigDecimal("20.25"))
                .totalDeduccion(new BigDecimal("15.75"))
                .totalNeto(new BigDecimal("105.00"))
                .bloqueado((byte) 0)
                .periodo(202406L)
                .build();
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
              "key": "100.2024.6"
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
              "periodo": 202406
            }
            """;

    private String liquidacionRequestJson() throws Exception {
        return objectMapper.writeValueAsString(sampleRequest());
    }

    @Test
    void findAllByPeriodo_returnsOkWithListOfLiquidacion() throws Exception {
        when(service.getLiquidacionesByPeriodo(2024, 6, 100)).thenReturn(List.of(sampleLiquidacion()));
        when(liquidacionDtoMapper.toResponse(any(Liquidacion.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/liquidacion/periodo/{anho}/{mes}/{limit}", 2024, 6, 100))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLiquidacion + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByPeriodoLegajo_returnsOkWithListOfLiquidacion() throws Exception {
        when(service.getLiquidacionesByPeriodoLegajo(2024, 6, 100L, 100)).thenReturn(List.of(sampleLiquidacion()));
        when(liquidacionDtoMapper.toResponse(any(Liquidacion.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/liquidacion/periodolegajo/{anho}/{mes}/{legajoId}/{limit}",
                        2024, 6, 100L, 100))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLiquidacion + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllBySemestre_returnsOkWithListOfLiquidacion() throws Exception {
        when(service.getLiquidacionesBySemestre(2024, 1, 100)).thenReturn(List.of(sampleLiquidacion()));
        when(liquidacionDtoMapper.toResponse(any(Liquidacion.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/liquidacion/semestre/{anho}/{semestre}/{limit}", 2024, 1, 100))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLiquidacion + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllBySemestreLegajo_returnsOkWithListOfLiquidacion() throws Exception {
        when(service.getLiquidacionesBySemestreLegajo(2024, 1, 100L, 100)).thenReturn(List.of(sampleLiquidacion()));
        when(liquidacionDtoMapper.toResponse(any(Liquidacion.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/liquidacion/semestrelegajo/{anho}/{semestre}/{legajoId}/{limit}",
                        2024, 1, 100L, 100))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLiquidacion + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByLegajo_returnsOkWithListOfLiquidacion() throws Exception {
        when(service.getLiquidacionesByLegajo(100L)).thenReturn(List.of(sampleLiquidacion()));
        when(liquidacionDtoMapper.toResponse(any(Liquidacion.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/liquidacion/legajo/{legajoId}", 100L))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLiquidacion + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByLegajoForward_returnsOkWithListOfLiquidacionPeriodo() throws Exception {
        when(service.getLiquidacionesByLegajoForward(100L, 2024, 6))
                .thenReturn(List.of(sampleLiquidacionPeriodo()));
        when(liquidacionDtoMapper.toPeriodoResponse(any(LiquidacionPeriodoForward.class))).thenReturn(
                LiquidacionPeriodoResponse.builder()
                        .liquidacionId(1L)
                        .legajoId(100L)
                        .anho(2024)
                        .mes(6)
                        .dependenciaId(7)
                        .salida("DOC")
                        .totalRemunerativo(new BigDecimal("100.50"))
                        .totalNoRemunerativo(new BigDecimal("20.25"))
                        .totalDeduccion(new BigDecimal("15.75"))
                        .totalNeto(new BigDecimal("105.00"))
                        .bloqueado((byte) 0)
                        .periodo(202406L)
                        .build());

        mockMvc.perform(get("/api/haberes/core/liquidacion/legajoforward/{legajoId}/{anho}/{mes}", 100L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLiquidacionPeriodo + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByDependencia_returnsOkWithListOfLiquidacion() throws Exception {
        when(service.getLiquidacionesByDependencia(7, 2024, 6, "DOC")).thenReturn(List.of(sampleLiquidacion()));
        when(liquidacionDtoMapper.toResponse(any(Liquidacion.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/liquidacion/dependencia/{dependenciaId}/{anho}/{mes}/{salida}",
                        7, 2024, 6, "DOC"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLiquidacion + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByAcreditado_returnsOkWithListOfLiquidacion() throws Exception {
        when(service.getLiquidacionesByAcreditado(2024, 6)).thenReturn(List.of(sampleLiquidacion()));
        when(liquidacionDtoMapper.toResponse(any(Liquidacion.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/liquidacion/acreditado/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLiquidacion + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByLiquidacionId_returnsOkWithLiquidacion() throws Exception {
        when(service.getLiquidacionById(1L)).thenReturn(sampleLiquidacion());
        when(liquidacionDtoMapper.toResponse(any(Liquidacion.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/liquidacion/{liquidacionId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedLiquidacion, JsonCompareMode.STRICT));
    }

    @Test
    void findByLiquidacionId_whenServiceThrowsLiquidacionException_returnsNotFound() throws Exception {
        when(service.getLiquidacionById(99L)).thenThrow(new LiquidacionException(99L));

        mockMvc.perform(get("/api/haberes/core/liquidacion/{liquidacionId}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByUnique_returnsOkWithLiquidacion() throws Exception {
        when(service.getLiquidacionByUniqueKey(100L, 2024, 6)).thenReturn(sampleLiquidacion());
        when(liquidacionDtoMapper.toResponse(any(Liquidacion.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/liquidacion/unique/{legajoId}/{anho}/{mes}", 100L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedLiquidacion, JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_whenServiceThrowsLiquidacionException_returnsNotFound() throws Exception {
        when(service.getLiquidacionByUniqueKey(100L, 2024, 6))
                .thenThrow(new LiquidacionException(100L, 2024, 6));

        mockMvc.perform(get("/api/haberes/core/liquidacion/unique/{legajoId}/{anho}/{mes}", 100L, 2024, 6))
                .andExpect(status().isNotFound());
    }

    @Test
    void add_returnsCreatedWithSavedLiquidacion() throws Exception {
        when(liquidacionDtoMapper.toDomain(any())).thenReturn(sampleLiquidacion());
        when(service.add(any(Liquidacion.class))).thenReturn(sampleLiquidacion());
        when(liquidacionDtoMapper.toResponse(any(Liquidacion.class))).thenReturn(sampleResponse());

        mockMvc.perform(post("/api/haberes/core/liquidacion/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(liquidacionRequestJson()))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedLiquidacion, JsonCompareMode.STRICT));
    }

    @Test
    void addVersion_returnsCreatedWithSavedLiquidacion() throws Exception {
        when(liquidacionDtoMapper.toDomain(any())).thenReturn(sampleLiquidacion());
        when(service.addVersion(any(Liquidacion.class), any())).thenReturn(sampleLiquidacion());
        when(liquidacionDtoMapper.toResponse(any(Liquidacion.class))).thenReturn(sampleResponse());

        mockMvc.perform(post("/api/haberes/core/liquidacion/version/{version}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(liquidacionRequestJson()))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedLiquidacion, JsonCompareMode.STRICT));
    }

    @Test
    void acreditado_returnsCreatedWithLiquidacion() throws Exception {
        when(liquidacionDtoMapper.toDomain(any())).thenReturn(sampleLiquidacion());
        when(service.acreditado(any(Liquidacion.class), any(OffsetDateTime.class)))
                .thenReturn(sampleLiquidacion());
        when(liquidacionDtoMapper.toResponse(any(Liquidacion.class))).thenReturn(sampleResponse());

        mockMvc.perform(post("/api/haberes/core/liquidacion/acreditado/{fecha}", "2024-06-30T12:00:00+00:00")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(liquidacionRequestJson()))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedLiquidacion, JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithUpdatedLiquidacion() throws Exception {
        when(liquidacionDtoMapper.toDomain(any())).thenReturn(sampleLiquidacion());
        when(service.update(any(Liquidacion.class), anyLong())).thenReturn(sampleLiquidacion());
        when(liquidacionDtoMapper.toResponse(any(Liquidacion.class))).thenReturn(sampleResponse());

        mockMvc.perform(put("/api/haberes/core/liquidacion/{liquidacionId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(liquidacionRequestJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedLiquidacion, JsonCompareMode.STRICT));
    }

    @Test
    void saveall_returnsOkWithListOfSavedLiquidacion() throws Exception {
        when(liquidacionDtoMapper.toDomain(any())).thenReturn(sampleLiquidacion());
        when(service.saveAll(anyList(), any())).thenReturn(List.of(sampleLiquidacion()));
        when(liquidacionDtoMapper.toResponse(any(Liquidacion.class))).thenReturn(sampleResponse());

        String body = objectMapper.writeValueAsString(List.of(sampleRequest()));

        mockMvc.perform(put("/api/haberes/core/liquidacion/saveall/{version}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLiquidacion + "]", JsonCompareMode.STRICT));
    }

    @Test
    void updateVersion_returnsOkWithUpdatedLiquidacion() throws Exception {
        when(liquidacionDtoMapper.toDomain(any())).thenReturn(sampleLiquidacion());
        when(service.updateVersion(any(Liquidacion.class), anyLong(), any())).thenReturn(sampleLiquidacion());
        when(liquidacionDtoMapper.toResponse(any(Liquidacion.class))).thenReturn(sampleResponse());

        mockMvc.perform(put("/api/haberes/core/liquidacion/version/{liquidacionId}/{version}", 1L, 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(liquidacionRequestJson()))
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
