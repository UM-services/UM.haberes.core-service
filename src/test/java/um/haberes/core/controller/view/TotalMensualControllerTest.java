package um.haberes.core.controller.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import um.haberes.core.exception.view.TotalMensualException;
import um.haberes.core.kotlin.model.view.TotalMensual;
import um.haberes.core.service.view.TotalMensualService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TotalMensualControllerTest {

    @Mock
    private TotalMensualService service;

    private TotalMensualController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new TotalMensualController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private TotalMensual sampleTotalMensual() {
        TotalMensual totalMensual = new TotalMensual();
        totalMensual.setUniqueId("2024-6-100");
        totalMensual.setAnho(2024);
        totalMensual.setMes(6);
        totalMensual.setCodigoId(100);
        totalMensual.setTotal(new BigDecimal("1234.56"));
        return totalMensual;
    }

    @Test
    void findAllByPeriodo_returnsOkWithTotalMensualListBody() throws Exception {
        when(service.findAllByPeriodo(2024, 6)).thenReturn(List.of(sampleTotalMensual()));

        mockMvc.perform(get("/api/haberes/core/totalmensual/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          {
                            "uniqueId": "2024-6-100",
                            "anho": 2024,
                            "mes": 6,
                            "codigoId": 100,
                            "total": 1234.56
                          }
                        ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_returnsOkWithTotalMensualBody() throws Exception {
        when(service.findByUnique(2024, 6, 100)).thenReturn(sampleTotalMensual());

        mockMvc.perform(get("/api/haberes/core/totalmensual/unique/{anho}/{mes}/{codigoId}", 2024, 6, 100))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "uniqueId": "2024-6-100",
                          "anho": 2024,
                          "mes": 6,
                          "codigoId": 100,
                          "total": 1234.56
                        }
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_whenServiceThrowsTotalMensualException_returnsNotFound() throws Exception {
        when(service.findByUnique(2024, 1, 999)).thenThrow(new TotalMensualException(2024, 1, 999));

        mockMvc.perform(get("/api/haberes/core/totalmensual/unique/{anho}/{mes}/{codigoId}", 2024, 1, 999))
                .andExpect(status().isNotFound());
    }
}
