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
import um.haberes.core.kotlin.model.view.CargoLiquidacionDocente;
import um.haberes.core.service.view.CargoLiquidacionDocenteService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CargoLiquidacionDocenteControllerTest {

    @Mock
    private CargoLiquidacionDocenteService service;

    @InjectMocks
    private CargoLiquidacionDocenteController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private CargoLiquidacionDocente sampleCargoLiquidacionDocente() {
        CargoLiquidacionDocente cargoLiquidacionDocente = new CargoLiquidacionDocente();
        cargoLiquidacionDocente.setUniqueId("100-2024-6");
        cargoLiquidacionDocente.setLegajoId(100L);
        cargoLiquidacionDocente.setAnho(2024);
        cargoLiquidacionDocente.setMes(6);
        return cargoLiquidacionDocente;
    }

    @Test
    void findAllByPeriodo_returnsOkWithCargoLiquidacionDocenteListBody() throws Exception {
        when(service.findAllByPeriodo(2024, 6)).thenReturn(List.of(sampleCargoLiquidacionDocente()));

        mockMvc.perform(get("/api/haberes/core/cargoLiquidacionDocente/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          {
                            "uniqueId": "100-2024-6",
                            "legajoId": 100,
                            "anho": 2024,
                            "mes": 6
                          }
                        ]
                        """, JsonCompareMode.STRICT));
    }
}
