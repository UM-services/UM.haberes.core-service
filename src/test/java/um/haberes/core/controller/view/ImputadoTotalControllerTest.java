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
import um.haberes.core.exception.view.ImputadoTotalException;
import um.haberes.core.kotlin.model.view.ImputadoTotal;
import um.haberes.core.service.view.ImputadoTotalService;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ImputadoTotalControllerTest {

    @Mock
    private ImputadoTotalService service;

    private ImputadoTotalController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new ImputadoTotalController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private ImputadoTotal sampleImputadoTotal() {
        ImputadoTotal imputadoTotal = new ImputadoTotal();
        imputadoTotal.setUniqueId("2024-6");
        imputadoTotal.setAnho(2024);
        imputadoTotal.setMes(6);
        imputadoTotal.setTotal(new BigDecimal("9999.99"));
        return imputadoTotal;
    }

    @Test
    void findByPeriodo_returnsOkWithImputadoTotalBody() throws Exception {
        when(service.findByPeriodo(2024, 6)).thenReturn(sampleImputadoTotal());

        mockMvc.perform(get("/api/haberes/core/imputadototal/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "uniqueId": "2024-6",
                          "anho": 2024,
                          "mes": 6,
                          "total": 9999.99
                        }
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findByPeriodo_whenServiceThrowsImputadoTotalException_returnsBadRequest() throws Exception {
        when(service.findByPeriodo(2024, 1)).thenThrow(new ImputadoTotalException(2024, 1));

        mockMvc.perform(get("/api/haberes/core/imputadototal/periodo/{anho}/{mes}", 2024, 1))
                .andExpect(status().isBadRequest());
    }
}
