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
import um.haberes.core.kotlin.model.view.ImputadoAporte;
import um.haberes.core.service.view.ImputadoAporteService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ImputadoAporteControllerTest {

    @Mock
    private ImputadoAporteService service;

    @InjectMocks
    private ImputadoAporteController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private ImputadoAporte sampleImputadoAporte() {
        ImputadoAporte imputadoAporte = new ImputadoAporte();
        imputadoAporte.setUniqueId("2024-6-30");
        imputadoAporte.setAnho(2024);
        imputadoAporte.setMes(6);
        imputadoAporte.setCuentaAportes(new BigDecimal("4500.00"));
        imputadoAporte.setPorcentajeImputado(new BigDecimal("11.00"));
        imputadoAporte.setTotalImputado(new BigDecimal("495.00"));
        return imputadoAporte;
    }

    @Test
    void findAllByPeriodo_returnsOkWithImputadoAporteListBody() throws Exception {
        when(service.findAllByPeriodo(2024, 6)).thenReturn(List.of(sampleImputadoAporte()));

        mockMvc.perform(get("/api/haberes/core/imputadoaporte/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          {
                            "uniqueId": "2024-6-30",
                            "anho": 2024,
                            "mes": 6,
                            "cuentaAportes": 4500.00,
                            "porcentajeImputado": 11.00,
                            "totalImputado": 495.00
                          }
                        ]
                        """, JsonCompareMode.STRICT));
    }
}
