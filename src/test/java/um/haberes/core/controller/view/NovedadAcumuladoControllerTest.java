package um.haberes.core.controller.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import um.haberes.core.model.view.NovedadAcumulado;
import um.haberes.core.service.view.NovedadAcumuladoService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NovedadAcumuladoControllerTest {

    @Mock
    private NovedadAcumuladoService service;

    @InjectMocks
    private NovedadAcumuladoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void devuelveLasNovedadesDelCodigoYPeriodo() throws Exception {
        NovedadAcumulado novedad = new NovedadAcumulado("2026.9.1001.31", 1001L, 2026, 9, 31,
                new BigDecimal("1500.50"), 2);
        when(service.findAllByCodigo(31, 2026, 9)).thenReturn(List.of(novedad));

        mockMvc.perform(get("/api/haberes/core/novedadacumulado/codigo/31/2026/9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].legajoId").value(1001))
                .andExpect(jsonPath("$[0].codigoId").value(31))
                .andExpect(jsonPath("$[0].cantidad").value(2))
                .andExpect(jsonPath("$[0].importe").value(1500.50));
    }
}
