package um.haberes.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import um.haberes.core.exception.AdicionalCursoRangoException;
import um.haberes.core.kotlin.model.AdicionalCursoRango;
import um.haberes.core.service.AdicionalCursoRangoService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AdicionalCursoRangoControllerTest {

    @Mock
    private AdicionalCursoRangoService service;

    @InjectMocks
    private AdicionalCursoRangoController controller;

    private MockMvc mockMvc;

    private static final String ADICIONAL_CURSO_RANGO_JSON = """
            {
              "adicionalCursoRangoId": 1,
              "horasDesde": 1,
              "horasHasta": 10,
              "porcentaje": 5.50,
              "adicionalCursoTablaId": 2,
              "created": null,
              "updated": null
            }
            """;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private AdicionalCursoRango sampleAdicionalCursoRango() {
        AdicionalCursoRango adicionalCursoRango = new AdicionalCursoRango();
        adicionalCursoRango.setAdicionalCursoRangoId(1L);
        adicionalCursoRango.setHorasDesde(1);
        adicionalCursoRango.setHorasHasta(10);
        adicionalCursoRango.setPorcentaje(new BigDecimal("5.50"));
        adicionalCursoRango.setAdicionalCursoTablaId(2L);
        return adicionalCursoRango;
    }

    @Test
    void findAllByAdicionalCursoTabla_returnsOkWithListOfRangos() throws Exception {
        when(service.findAllByAdicionalCursoTabla(2L)).thenReturn(List.of(sampleAdicionalCursoRango()));

        mockMvc.perform(get("/api/haberes/core/adicionalCursoRango/tabla/{adicionalCursoTablaId}", 2))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + ADICIONAL_CURSO_RANGO_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByAdicionalCursoRangoId_returnsOkWithRangoBody() throws Exception {
        when(service.findByAdicionalCursoRangoId(1L)).thenReturn(sampleAdicionalCursoRango());

        mockMvc.perform(get("/api/haberes/core/adicionalCursoRango/{adicionalCursoRangoId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ADICIONAL_CURSO_RANGO_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void findByAdicionalCursoRangoId_whenServiceThrowsAdicionalCursoRangoException_returnsBadRequest() throws Exception {
        when(service.findByAdicionalCursoRangoId(99L)).thenThrow(mock(AdicionalCursoRangoException.class));

        mockMvc.perform(get("/api/haberes/core/adicionalCursoRango/{adicionalCursoRangoId}", 99))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_returnsOkWithSavedRango() throws Exception {
        String requestJson = new ObjectMapper().writeValueAsString(sampleAdicionalCursoRango());
        when(service.add(any(AdicionalCursoRango.class))).thenReturn(sampleAdicionalCursoRango());

        mockMvc.perform(post("/api/haberes/core/adicionalCursoRango/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ADICIONAL_CURSO_RANGO_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void deleteByAdicionalCursoRangoId_returnsNoContentWithEmptyBody() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/adicionalCursoRango/{adicionalCursoRangoId}", 1))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
}
