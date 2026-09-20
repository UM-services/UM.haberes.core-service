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
import um.haberes.core.exception.LiquidacionAdicionalException;
import um.haberes.core.model.LiquidacionAdicionalEntity;
import um.haberes.core.service.LiquidacionAdicionalService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LiquidacionAdicionalControllerTest {

    @Mock
    private LiquidacionAdicionalService service;

    private LiquidacionAdicionalController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new LiquidacionAdicionalController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private LiquidacionAdicionalEntity sampleLiquidacionAdicional() {
        LiquidacionAdicionalEntity liquidacionAdicional = new LiquidacionAdicionalEntity();
        liquidacionAdicional.setLiquidacionAdicionalId(1L);
        liquidacionAdicional.setLegajoId(100L);
        liquidacionAdicional.setAnho(2024);
        liquidacionAdicional.setMes(6);
        liquidacionAdicional.setDependenciaId(7);
        liquidacionAdicional.setAdicional(new BigDecimal("50.25"));
        return liquidacionAdicional;
    }

    private final String expectedLiquidacionAdicional = """
            {
              "liquidacionAdicionalId": 1,
              "legajoId": 100,
              "anho": 2024,
              "mes": 6,
              "dependenciaId": 7,
              "adicional": 50.25,
              "persona": null,
              "dependencia": null,
              "created": null,
              "updated": null
            }
            """;

    @Test
    void findAllByLegajo_returnsOkWithListOfLiquidacionAdicional() throws Exception {
        when(service.findAllByLegajo(100L, 2024, 6)).thenReturn(List.of(sampleLiquidacionAdicional()));

        mockMvc.perform(get("/api/haberes/core/liquidacion-adicional/legajo/{legajoId}/{anho}/{mes}", 100L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLiquidacionAdicional + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByDependencia_returnsOkWithLiquidacionAdicional() throws Exception {
        when(service.findByDependencia(100L, 2024, 6, 7)).thenReturn(sampleLiquidacionAdicional());

        mockMvc.perform(get("/api/haberes/core/liquidacion-adicional/dependencia/{legajoId}/{anho}/{mes}/{dependenciaId}",
                        100L, 2024, 6, 7))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedLiquidacionAdicional, JsonCompareMode.STRICT));
    }

    @Test
    void findByDependencia_whenServiceThrowsLiquidacionAdicionalException_returnsBadRequest() throws Exception {
        when(service.findByDependencia(100L, 2024, 6, 99))
                .thenThrow(new LiquidacionAdicionalException(100L, 2024, 6, 99));

        mockMvc.perform(get("/api/haberes/core/liquidacion-adicional/dependencia/{legajoId}/{anho}/{mes}/{dependenciaId}",
                        100L, 2024, 6, 99))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteAllByLegajo_returnsNoContentWithEmptyBody() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/liquidacion-adicional/legajo/{legajoId}/{anho}/{mes}",
                        100L, 2024, 6))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    void add_returnsOkWithSavedLiquidacionAdicional() throws Exception {
        when(service.add(any(LiquidacionAdicionalEntity.class))).thenReturn(sampleLiquidacionAdicional());

        String body = new ObjectMapper().writeValueAsString(sampleLiquidacionAdicional());

        mockMvc.perform(post("/api/haberes/core/liquidacion-adicional/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedLiquidacionAdicional, JsonCompareMode.STRICT));
    }
}
