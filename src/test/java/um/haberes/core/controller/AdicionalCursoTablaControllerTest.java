package um.haberes.core.controller;

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
import um.haberes.core.exception.AdicionalCursoTablaException;
import um.haberes.core.kotlin.model.AdicionalCursoTabla;
import um.haberes.core.service.AdicionalCursoTablaService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AdicionalCursoTablaControllerTest {

    @Mock
    private AdicionalCursoTablaService service;

    @InjectMocks
    private AdicionalCursoTablaController controller;

    private MockMvc mockMvc;

    private static final String ADICIONAL_CURSO_TABLA_JSON = """
            {
              "adicionalCursoTablaId": 1,
              "periodoDesde": 202401,
              "periodoHasta": 202412,
              "facultadId": 1,
              "geograficaId": null,
              "facultad": null,
              "geografica": null,
              "adicionalCursoRangos": null,
              "created": null,
              "updated": null
            }
            """;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private AdicionalCursoTabla sampleAdicionalCursoTabla() {
        AdicionalCursoTabla adicionalCursoTabla = new AdicionalCursoTabla();
        adicionalCursoTabla.setAdicionalCursoTablaId(1L);
        adicionalCursoTabla.setPeriodoDesde(202401L);
        adicionalCursoTabla.setPeriodoHasta(202412L);
        adicionalCursoTabla.setFacultadId(1);
        return adicionalCursoTabla;
    }

    @Test
    void findAll_returnsOkWithListOfTablas() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleAdicionalCursoTabla()));

        mockMvc.perform(get("/api/haberes/core/adicionalCursoTabla/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + ADICIONAL_CURSO_TABLA_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByAdicionalCursoTablaId_returnsOkWithTablaBody() throws Exception {
        when(service.findByAdicionalCursoTablaId(1L)).thenReturn(sampleAdicionalCursoTabla());

        mockMvc.perform(get("/api/haberes/core/adicionalCursoTabla/{adicionalCursoTablaId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ADICIONAL_CURSO_TABLA_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void findByAdicionalCursoTablaId_whenServiceThrowsAdicionalCursoTablaException_returnsBadRequest() throws Exception {
        when(service.findByAdicionalCursoTablaId(99L)).thenThrow(new AdicionalCursoTablaException(99L));

        mockMvc.perform(get("/api/haberes/core/adicionalCursoTabla/{adicionalCursoTablaId}", 99))
                .andExpect(status().isBadRequest());
    }
}
