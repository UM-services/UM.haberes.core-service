package um.haberes.core.controller.view;

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
import um.haberes.core.exception.view.TotalSalidaException;
import um.haberes.core.model.view.TotalSalida;
import um.haberes.core.service.view.TotalSalidaService;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TotalSalidaControllerTest {

    @Mock
    private TotalSalidaService service;

    @InjectMocks
    private TotalSalidaController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private TotalSalida sampleTotalSalida() {
        TotalSalida totalSalida = new TotalSalida();
        totalSalida.setUniqueId("2024-06");
        totalSalida.setAnho(2024);
        totalSalida.setMes(6);
        totalSalida.setTotalRemunerativo(new BigDecimal("100.50"));
        totalSalida.setTotalNoRemunerativo(new BigDecimal("20.25"));
        totalSalida.setTotalDeduccion(new BigDecimal("15.75"));
        totalSalida.setTotalNeto(new BigDecimal("105.00"));
        return totalSalida;
    }

    @Test
    void findAByPeriodo_returnsOkWithTotalSalidaBody() throws Exception {
        when(service.findByPeriodo(2024, 6)).thenReturn(sampleTotalSalida());

        mockMvc.perform(get("/api/haberes/core/totalsalida/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "uniqueId": "2024-06",
                          "anho": 2024,
                          "mes": 6,
                          "totalRemunerativo": 100.50,
                          "totalNoRemunerativo": 20.25,
                          "totalDeduccion": 15.75,
                          "totalNeto": 105.00
                        }
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findAByPeriodo_whenServiceThrowsTotalSalidaException_returnsBadRequest() throws Exception {
        when(service.findByPeriodo(anyInt(), anyInt())).thenThrow(new TotalSalidaException());

        mockMvc.perform(get("/api/haberes/core/totalsalida/periodo/{anho}/{mes}", 2024, 1))
                .andExpect(status().isBadRequest());
    }
}
