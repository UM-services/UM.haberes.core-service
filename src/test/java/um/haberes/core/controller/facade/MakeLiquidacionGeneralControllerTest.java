package um.haberes.core.controller.facade;

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
import um.haberes.core.service.facade.MakeLiquidacionGeneralService;
import um.haberes.core.service.facade.liquidaciones.LiquidacionProceso;
import um.haberes.core.service.facade.liquidaciones.LiquidacionProcesoService;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MakeLiquidacionGeneralControllerTest {

    @Mock
    private MakeLiquidacionGeneralService service;

    @Mock
    private LiquidacionProcesoService liquidacionProcesoService;

    @InjectMocks
    private MakeLiquidacionGeneralController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void liquidacionGeneral_returnsAcceptedWithProcesoId() throws Exception {
        LiquidacionProceso proceso = new LiquidacionProceso(5);
        when(service.liquidacionGeneral(2024, 6, false)).thenReturn(proceso);

        mockMvc.perform(get("/api/haberes/core/makeLiquidacionGeneral/general/{anho}/{mes}/{force}", 2024, 6, false))
                .andExpect(status().isAccepted())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {"procesoId": "%s"}
                        """.formatted(proceso.getId()), JsonCompareMode.STRICT));
    }

    @Test
    void getProgress_returnsOkWithProceso() throws Exception {
        LiquidacionProceso proceso = new LiquidacionProceso(5);
        proceso.legajoProcesado();
        when(liquidacionProcesoService.getProceso(proceso.getId())).thenReturn(Optional.of(proceso));

        mockMvc.perform(get("/api/haberes/core/makeLiquidacionGeneral/general/progress/{procesoId}", proceso.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "id": "%s",
                          "total": 5,
                          "procesados": 1,
                          "status": "RUNNING",
                          "progreso": 20.0
                        }
                        """.formatted(proceso.getId()), JsonCompareMode.STRICT));
    }

    @Test
    void getProgress_whenProcesoMissing_returnsNotFound() throws Exception {
        when(liquidacionProcesoService.getProceso("missing-id")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/haberes/core/makeLiquidacionGeneral/general/progress/{procesoId}", "missing-id"))
                .andExpect(status().isNotFound());
    }
}
