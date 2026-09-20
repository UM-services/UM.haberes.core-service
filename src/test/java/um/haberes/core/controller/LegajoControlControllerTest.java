package um.haberes.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import um.haberes.core.kotlin.model.LegajoControl;
import um.haberes.core.service.LegajoControlService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LegajoControlControllerTest {

    @Mock
    private LegajoControlService service;

    private LegajoControlController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new LegajoControlController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private LegajoControl sampleLegajoControl() {
        LegajoControl legajoControl = new LegajoControl();
        legajoControl.setLegajoControlId(1L);
        legajoControl.setLegajoId(100L);
        legajoControl.setAnho(2024);
        legajoControl.setMes(6);
        legajoControl.setLiquidado((byte) 1);
        legajoControl.setFusionado((byte) 0);
        legajoControl.setBonoEnviado((byte) 0);
        return legajoControl;
    }

    private final String expectedLegajoControl = """
            {
              "legajoControlId": 1,
              "legajoId": 100,
              "anho": 2024,
              "mes": 6,
              "liquidado": 1,
              "fusionado": 0,
              "bonoEnviado": 0,
              "persona": null,
              "created": null,
              "updated": null
            }
            """;

    @Test
    void findAllByPeriodo_returnsOkWithListOfLegajoControl() throws Exception {
        when(service.findAllByPeriodo(2024, 6)).thenReturn(List.of(sampleLegajoControl()));

        mockMvc.perform(get("/api/haberes/core/legajocontrol/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLegajoControl + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllLiquidadoByPeriodo_returnsOkWithListOfLiquidados() throws Exception {
        when(service.findAllLiquidadoByPeriodo(2024, 6)).thenReturn(List.of(sampleLegajoControl()));

        mockMvc.perform(get("/api/haberes/core/legajocontrol/liquidado/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLegajoControl + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllDependenciaByPeriodo_returnsOkWithFilteredList() throws Exception {
        when(service.findAllDependenciaByPeriodo(2024, 6, 7, "DOC")).thenReturn(List.of(sampleLegajoControl()));

        mockMvc.perform(get("/api/haberes/core/legajocontrol/dependencia/{anho}/{mes}/{dependenciaId}/{filtro}",
                        2024, 6, 7, "DOC"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLegajoControl + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_returnsOkWithLegajoControl() throws Exception {
        when(service.findByUnique(100L, 2024, 6)).thenReturn(sampleLegajoControl());

        mockMvc.perform(get("/api/haberes/core/legajocontrol/unique/{legajoId}/{anho}/{mes}", 100L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedLegajoControl, JsonCompareMode.STRICT));
    }

    @Test
    void add_returnsOkWithSavedLegajoControl() throws Exception {
        when(service.add(any(LegajoControl.class))).thenReturn(sampleLegajoControl());

        String body = new ObjectMapper().writeValueAsString(sampleLegajoControl());

        mockMvc.perform(post("/api/haberes/core/legajocontrol/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedLegajoControl, JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithUpdatedLegajoControl() throws Exception {
        when(service.update(any(LegajoControl.class), anyLong())).thenReturn(sampleLegajoControl());

        String body = new ObjectMapper().writeValueAsString(sampleLegajoControl());

        mockMvc.perform(put("/api/haberes/core/legajocontrol/{legajocontrolId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedLegajoControl, JsonCompareMode.STRICT));
    }

    @Test
    void saveAll_returnsOkWithListOfSavedLegajoControl() throws Exception {
        when(service.saveAll(anyList())).thenReturn(List.of(sampleLegajoControl()));

        String body = new ObjectMapper().writeValueAsString(List.of(sampleLegajoControl()));

        mockMvc.perform(put("/api/haberes/core/legajocontrol/saveall")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLegajoControl + "]", JsonCompareMode.STRICT));
    }
}
