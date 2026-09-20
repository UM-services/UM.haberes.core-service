package um.haberes.core.controller.facade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import um.haberes.core.service.facade.LiquidacionEtecService;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LiquidacionEtecControllerTest {

    @Mock
    private LiquidacionEtecService service;

    private LiquidacionEtecController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new LiquidacionEtecController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void calcularPorcentajeAntiguedad_returnsOkWithBigDecimal() throws Exception {
        when(service.calcularPorcentajeAntiguedad(100L, 2024, 6, 3, 12)).thenReturn(new BigDecimal("0.2"));

        mockMvc.perform(get("/api/haberes/core/liquidacion-etec/calcularPorcentajeAntiguedad/{legajoId}/{anho}/{mes}/{facultadId}/{geograficaId}",
                        100, 2024, 6, 3, 12))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("0.2", JsonCompareMode.STRICT));
    }
}
