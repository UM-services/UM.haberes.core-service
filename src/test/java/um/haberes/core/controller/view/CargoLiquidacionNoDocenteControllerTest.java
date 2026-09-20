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
import um.haberes.core.model.view.CargoLiquidacionNoDocente;
import um.haberes.core.service.view.CargoLiquidacionNoDocenteService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CargoLiquidacionNoDocenteControllerTest {

    @Mock
    private CargoLiquidacionNoDocenteService service;

    @InjectMocks
    private CargoLiquidacionNoDocenteController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private CargoLiquidacionNoDocente sampleCargoLiquidacionNoDocente() {
        CargoLiquidacionNoDocente cargoLiquidacionNoDocente = new CargoLiquidacionNoDocente();
        cargoLiquidacionNoDocente.setUniqueId("100-2024-6");
        cargoLiquidacionNoDocente.setLegajoId(100L);
        cargoLiquidacionNoDocente.setAnho(2024);
        cargoLiquidacionNoDocente.setMes(6);
        return cargoLiquidacionNoDocente;
    }

    @Test
    void findAllByPeriodo_returnsOkWithCargoLiquidacionNoDocenteListBody() throws Exception {
        when(service.findAllByPeriodo(2024, 6)).thenReturn(List.of(sampleCargoLiquidacionNoDocente()));

        mockMvc.perform(get("/api/haberes/core/cargoLiquidacionNoDocente/periodo/{anho}/{mes}", 2024, 6))
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
