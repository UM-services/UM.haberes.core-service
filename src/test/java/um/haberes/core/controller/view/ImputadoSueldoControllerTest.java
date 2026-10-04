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
import um.haberes.core.model.view.ImputadoSueldo;
import um.haberes.core.service.view.ImputadoSueldoService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ImputadoSueldoControllerTest {

    @Mock
    private ImputadoSueldoService service;

    @InjectMocks
    private ImputadoSueldoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private ImputadoSueldo sampleImputadoSueldo() {
        ImputadoSueldo imputadoSueldo = new ImputadoSueldo();
        imputadoSueldo.setUniqueId("2024-6-20");
        imputadoSueldo.setAnho(2024);
        imputadoSueldo.setMes(6);
        imputadoSueldo.setCuentaSueldos(new BigDecimal("3000.00"));
        imputadoSueldo.setTotalImputado(new BigDecimal("1500.00"));
        return imputadoSueldo;
    }

    @Test
    void findAllByPeriodo_returnsOkWithImputadoSueldoListBody() throws Exception {
        when(service.findAllByPeriodo(2024, 6)).thenReturn(List.of(sampleImputadoSueldo()));

        mockMvc.perform(get("/api/haberes/core/imputadosueldo/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          {
                            "uniqueId": "2024-6-20",
                            "anho": 2024,
                            "mes": 6,
                            "cuentaSueldos": 3000.00,
                            "totalImputado": 1500.00
                          }
                        ]
                        """, JsonCompareMode.STRICT));
    }
}
