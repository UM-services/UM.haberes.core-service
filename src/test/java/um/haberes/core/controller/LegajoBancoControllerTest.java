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
import um.haberes.core.exception.LegajoBancoException;
import um.haberes.core.model.LegajoBancoEntity;
import um.haberes.core.service.LegajoBancoService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LegajoBancoControllerTest {

    @Mock
    private LegajoBancoService service;

    @InjectMocks
    private LegajoBancoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private LegajoBancoEntity sampleLegajoBanco() {
        LegajoBancoEntity legajoBanco = new LegajoBancoEntity();
        legajoBanco.setLegajoBancoId(3L);
        legajoBanco.setLegajoId(123L);
        legajoBanco.setAnho(2024);
        legajoBanco.setMes(6);
        legajoBanco.setCbu("0720000000000000000001");
        legajoBanco.setFijo(new BigDecimal("50.25"));
        legajoBanco.setPorcentaje(new BigDecimal("49.75"));
        legajoBanco.setResto((byte) 1);
        legajoBanco.setAcreditado(new BigDecimal("1000.00"));
        return legajoBanco;
    }

    private String legajoBancoJson() {
        return """
                {
                  "legajoBancoId": 3,
                  "legajoId": 123,
                  "anho": 2024,
                  "mes": 6,
                  "cbu": "0720000000000000000001",
                  "fijo": 50.25,
                  "porcentaje": 49.75,
                  "resto": 1,
                  "acreditado": 1000.00,
                  "persona": null,
                  "liquidacion": null,
                  "created": null,
                  "updated": null
                }
                """;
    }

    @Test
    void findByLegajoBancoId_returnsOkWithLegajoBanco() throws Exception {
        when(service.findByLegajoBancoId(3L)).thenReturn(sampleLegajoBanco());

        mockMvc.perform(get("/api/haberes/core/legajobanco/{legajoBancoId}", 3))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(legajoBancoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByLegajoBancoId_whenServiceThrowsLegajoBancoException_returnsBadRequest() throws Exception {
        when(service.findByLegajoBancoId(101L)).thenThrow(new LegajoBancoException(101L));

        mockMvc.perform(get("/api/haberes/core/legajobanco/{legajoBancoId}", 101))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByUnique_returnsOkWithLegajoBanco() throws Exception {
        when(service.findByUnique(123L, 2024, 6, "0720000000000000000001")).thenReturn(sampleLegajoBanco());

        mockMvc.perform(get("/api/haberes/core/legajobanco/unique/{legajoId}/{anho}/{mes}/{cbu}",
                        123, 2024, 6, "0720000000000000000001"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(legajoBancoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_whenServiceThrowsLegajoBancoException_returnsBadRequest() throws Exception {
        when(service.findByUnique(999L, 2024, 6, "0720000000000000009999"))
                .thenThrow(new LegajoBancoException(999L, 2024, 6, "0720000000000000009999"));

        mockMvc.perform(get("/api/haberes/core/legajobanco/unique/{legajoId}/{anho}/{mes}/{cbu}",
                        999, 2024, 6, "0720000000000000009999"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findLastByLegajoId_returnsOkWithLegajoBanco() throws Exception {
        when(service.findLastByLegajoId(123L)).thenReturn(sampleLegajoBanco());

        mockMvc.perform(get("/api/haberes/core/legajobanco/lastlegajo/{legajoId}", 123))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(legajoBancoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findLastByLegajoId_whenServiceThrowsLegajoBancoException_returnsBadRequest() throws Exception {
        when(service.findLastByLegajoId(999L)).thenThrow(new LegajoBancoException(999L));

        mockMvc.perform(get("/api/haberes/core/legajobanco/lastlegajo/{legajoId}", 999))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findAllByLegajoId_returnsOkWithListOfLegajoBancos() throws Exception {
        when(service.findAllByLegajoId(123L)).thenReturn(List.of(sampleLegajoBanco()));

        mockMvc.perform(get("/api/haberes/core/legajobanco/legajo/{legajoId}", 123))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + legajoBancoJson() + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByLegajoPeriodo_returnsOkWithListOfLegajoBancos() throws Exception {
        when(service.findAllByLegajoPeriodo(123L, 2024, 6)).thenReturn(List.of(sampleLegajoBanco()));

        mockMvc.perform(get("/api/haberes/core/legajobanco/legajoperiodo/{legajoId}/{anho}/{mes}", 123, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + legajoBancoJson() + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findLegajoCbuPrincipal_returnsOkWithLegajoBanco() throws Exception {
        when(service.findLegajoCbuPrincipal(123L, 2024, 6)).thenReturn(sampleLegajoBanco());

        mockMvc.perform(get("/api/haberes/core/legajobanco/cbuprincipal/{legajoId}/{anho}/{mes}", 123, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(legajoBancoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findLegajoCbuPrincipal_whenServiceThrowsLegajoBancoException_returnsBadRequest() throws Exception {
        when(service.findLegajoCbuPrincipal(999L, 2024, 6)).thenThrow(new LegajoBancoException(999L, 2024, 6));

        mockMvc.perform(get("/api/haberes/core/legajobanco/cbuprincipal/{legajoId}/{anho}/{mes}", 999, 2024, 6))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findAllPeriodo_returnsOkWithListOfLegajoBancos() throws Exception {
        when(service.findAllPeriodo(2024, 6)).thenReturn(List.of(sampleLegajoBanco()));

        mockMvc.perform(get("/api/haberes/core/legajobanco/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + legajoBancoJson() + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllPeriodoSantander_returnsOkWithListOfLegajoBancos() throws Exception {
        when(service.findAllPeriodoSantander(123L, 2024, 6)).thenReturn(List.of(sampleLegajoBanco()));

        mockMvc.perform(get("/api/haberes/core/legajobanco/periodosantander/{legajoId}/{anho}/{mes}", 123, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + legajoBancoJson() + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllPeriodoOtrosBancos_returnsOkWithListOfLegajoBancos() throws Exception {
        when(service.findAllPeriodoOtrosBancos(123L, 2024, 6)).thenReturn(List.of(sampleLegajoBanco()));

        mockMvc.perform(get("/api/haberes/core/legajobanco/periodootrosbancos/{legajoId}/{anho}/{mes}", 123, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + legajoBancoJson() + "]", JsonCompareMode.STRICT));
    }

    @Test
    void add_returnsOkWithSavedLegajoBanco() throws Exception {
        LegajoBancoEntity legajoBanco = sampleLegajoBanco();
        when(service.add(any(LegajoBancoEntity.class))).thenReturn(legajoBanco);

        mockMvc.perform(post("/api/haberes/core/legajobanco/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(legajoBanco)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(legajoBancoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithUpdatedLegajoBanco() throws Exception {
        LegajoBancoEntity legajoBanco = sampleLegajoBanco();
        when(service.update(any(LegajoBancoEntity.class), anyLong())).thenReturn(legajoBanco);

        mockMvc.perform(put("/api/haberes/core/legajobanco/{legajobancoId}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(legajoBanco)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(legajoBancoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllSantander_returnsOkWithListOfLegajoBancos() throws Exception {
        when(service.findAllSantander("T", 2024, 6, 0)).thenReturn(List.of(sampleLegajoBanco()));

        mockMvc.perform(get("/api/haberes/core/legajobanco/santander/{salida}/{anho}/{mes}/{dependenciaId}",
                        "T", 2024, 6, 0))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + legajoBancoJson() + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllSantanderConCodigo_returnsOkWithListOfLegajoBancos() throws Exception {
        when(service.findAllSantanderConCodigo("T", 2024, 6, 0, 5)).thenReturn(List.of(sampleLegajoBanco()));

        mockMvc.perform(get("/api/haberes/core/legajobanco/santander/{salida}/{anho}/{mes}/{dependenciaId}/codigo/{codigoId}",
                        "T", 2024, 6, 0, 5))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + legajoBancoJson() + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllOtrosBancos_returnsOkWithListOfLegajoBancos() throws Exception {
        when(service.findAllOtrosBancos("T", 2024, 6, 0)).thenReturn(List.of(sampleLegajoBanco()));

        mockMvc.perform(get("/api/haberes/core/legajobanco/otrosbancos/{salida}/{anho}/{mes}/{dependenciaId}",
                        "T", 2024, 6, 0))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + legajoBancoJson() + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllOtrosBancosConCodigo_returnsOkWithListOfLegajoBancos() throws Exception {
        when(service.findAllOtrosBancosConCodigo("T", 2024, 6, 0, 5)).thenReturn(List.of(sampleLegajoBanco()));

        mockMvc.perform(get("/api/haberes/core/legajobanco/otrosbancos/{salida}/{anho}/{mes}/{dependenciaId}/codigo/{codigoId}",
                        "T", 2024, 6, 0, 5))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + legajoBancoJson() + "]", JsonCompareMode.STRICT));
    }

    @Test
    void deleteAllByPeriodo_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/legajobanco/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/legajobanco/{legajobancoId}", 3))
                .andExpect(status().isNoContent());
    }
}
