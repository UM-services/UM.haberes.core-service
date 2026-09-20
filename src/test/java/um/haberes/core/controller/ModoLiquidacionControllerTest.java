package um.haberes.core.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import um.haberes.core.kotlin.model.ModoLiquidacion;
import um.haberes.core.service.ModoLiquidacionService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ModoLiquidacionControllerTest {

    @Mock
    private ModoLiquidacionService service;

    private ModoLiquidacionController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new ModoLiquidacionController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private ModoLiquidacion sampleModoLiquidacion() {
        ModoLiquidacion modoLiquidacion = new ModoLiquidacion();
        modoLiquidacion.setModoLiquidacionId(1);
        modoLiquidacion.setDescripcion("Deposito en cuenta sueldo");
        return modoLiquidacion;
    }

    private final String expectedModoLiquidacion = """
            {
              "modoLiquidacionId": 1,
              "descripcion": "Deposito en cuenta sueldo",
              "created": null,
              "updated": null
            }
            """;

    @Test
    void findAll_returnsOkWithListOfModoLiquidacion() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleModoLiquidacion()));

        mockMvc.perform(get("/api/haberes/core/modoLiquidacion/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedModoLiquidacion + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByModoLiquidacionId_returnsOkWithModoLiquidacion() throws Exception {
        when(service.findByModoLiquidacionId(1)).thenReturn(sampleModoLiquidacion());

        mockMvc.perform(get("/api/haberes/core/modoLiquidacion/{modoLiquidacionId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedModoLiquidacion, JsonCompareMode.STRICT));
    }
}
