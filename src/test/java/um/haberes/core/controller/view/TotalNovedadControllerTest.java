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
import um.haberes.core.kotlin.model.view.TotalNovedad;
import um.haberes.core.service.view.TotalNovedadService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TotalNovedadControllerTest {

    @Mock
    private TotalNovedadService service;

    @InjectMocks
    private TotalNovedadController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private TotalNovedad sampleTotalNovedad() {
        TotalNovedad totalNovedad = new TotalNovedad();
        totalNovedad.setUniqueId("2024-6-100");
        totalNovedad.setAnho(2024);
        totalNovedad.setMes(6);
        totalNovedad.setCodigoId(100);
        totalNovedad.setTotal(new BigDecimal("1234.56"));
        return totalNovedad;
    }

    @Test
    void findAllByPeriodo_returnsOkWithTotalNovedadListBody() throws Exception {
        when(service.findAllByPeriodo(2024, 6)).thenReturn(List.of(sampleTotalNovedad()));

        mockMvc.perform(get("/api/haberes/core/totalnovedad/periodo/{anho}/{mes}", 2024, 6))
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
    void findByUnique_returnsOkWithTotalNovedadBody() throws Exception {
        when(service.findByUnique(2024, 6, 100)).thenReturn(sampleTotalNovedad());

        mockMvc.perform(get("/api/haberes/core/totalnovedad/unique/{anho}/{mes}/{codigoId}", 2024, 6, 100))
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
}
