package um.haberes.core.controller.facade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import um.haberes.core.service.facade.DesignacionToolService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DesignacionToolControllerTest {

    @Mock
    private DesignacionToolService service;

    private DesignacionToolController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new DesignacionToolController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void convertirGradoByLegajo_returnsNoContent() throws Exception {
        mockMvc.perform(get("/api/haberes/core/designaciontool/convertirbylegajo/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(service).convertirGradoByLegajo(100L, 2024, 6, false);
    }

    @Test
    void redesignarGradoByLegajo_returnsNoContent() throws Exception {
        mockMvc.perform(get("/api/haberes/core/designaciontool/redesignarbylegajo/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(service).redesignarGradoByLegajo(100L, 2024, 6, false);
    }

    @Test
    void redesignarGradoByLegajo_whenPeriodoBefore201904_appliesExcepcion() throws Exception {
        mockMvc.perform(get("/api/haberes/core/designaciontool/redesignarbylegajo/{legajoId}/{anho}/{mes}", 100, 2019, 3))
                .andExpect(status().isNoContent());

        verify(service).redesignarGradoByLegajo(100L, 2019, 3, true);
    }

    @Test
    void fusionarGradoByLegajo_returnsNoContent() throws Exception {
        mockMvc.perform(get("/api/haberes/core/designaciontool/fusionarbylegajo/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(service).fusionarGradoByLegajo(100L, 2024, 6, null, false);
    }

    @Test
    void desarraigoGradoByLegajo_returnsNoContent() throws Exception {
        mockMvc.perform(get("/api/haberes/core/designaciontool/desarraigobylegajo/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(service).desarraigoGradoByLegajo(100L, 2024, 6);
    }

    @Test
    void duplicarByLegajo_returnsNoContent() throws Exception {
        mockMvc.perform(get("/api/haberes/core/designaciontool/duplicarbylegajo/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(service).duplicarByLegajo(100L, 2024, 6);
    }

    @Test
    void deleteZombies_returnsNoContent() throws Exception {
        mockMvc.perform(get("/api/haberes/core/designaciontool/deletezombies/{anho}/{mes}", 2024, 6))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(service).deleteZombies(2024, 6);
    }

    @Test
    void indiceAntiguedad_returnsOkWithListOfBigDecimal() throws Exception {
        when(service.indiceAntiguedad(100L, 2024, 6)).thenReturn(List.of(new BigDecimal("0.4"), new BigDecimal("15")));

        mockMvc.perform(get("/api/haberes/core/designaciontool/indiceantiguedad/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          0.4,
                          15
                        ]
                        """, JsonCompareMode.STRICT));
    }
}
