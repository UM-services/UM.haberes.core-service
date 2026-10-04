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
import um.haberes.core.model.AfipConceptoSueldoEntity;
import um.haberes.core.model.view.AfipConceptoSueldoSearch;
import um.haberes.core.service.AfipConceptoSueldoService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AfipConceptoSueldoControllerTest {

    @Mock
    private AfipConceptoSueldoService service;

    @InjectMocks
    private AfipConceptoSueldoController controller;

    private MockMvc mockMvc;

    private static final String AFIP_CONCEPTO_SUELDO_JSON = """
            {
              "afipConceptoSueldoId": 3,
              "descripcion": "Retribuciones del personal",
              "asignado": 1,
              "created": null,
              "updated": null
            }
            """;

    private static final String AFIP_CONCEPTO_SUELDO_SEARCH_JSON = """
            {
              "afipConceptoSueldoId": 3,
              "descripcion": "Retribuciones del personal",
              "asignado": 1,
              "search": "retribuciones"
            }
            """;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private AfipConceptoSueldoEntity sampleAfipConceptoSueldo() {
        AfipConceptoSueldoEntity afipConceptoSueldo = new AfipConceptoSueldoEntity();
        afipConceptoSueldo.setAfipConceptoSueldoId(3L);
        afipConceptoSueldo.setDescripcion("Retribuciones del personal");
        afipConceptoSueldo.setAsignado((byte) 1);
        return afipConceptoSueldo;
    }

    private AfipConceptoSueldoSearch sampleAfipConceptoSueldoSearch() {
        AfipConceptoSueldoSearch search = new AfipConceptoSueldoSearch();
        search.setAfipConceptoSueldoId(3L);
        search.setDescripcion("Retribuciones del personal");
        search.setAsignado((byte) 1);
        search.setSearch("retribuciones");
        return search;
    }

    @Test
    void findByAfipConceptoSueldoId_returnsOkWithAfipConceptoSueldoBody() throws Exception {
        when(service.findByAfipConceptoSueldoId(3L)).thenReturn(sampleAfipConceptoSueldo());

        mockMvc.perform(get("/api/haberes/core/afipConceptoSueldo/{afipConceptoSueldoId}", 3))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(AFIP_CONCEPTO_SUELDO_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void findAllByAsignadoAndConditions_returnsOkWithListOfSearchResults() throws Exception {
        List<String> conditions = List.of("retribuciones", "3");
        String requestJson = new ObjectMapper().writeValueAsString(conditions);
        when(service.findAllByAsignadoAndConditions(conditions)).thenReturn(List.of(sampleAfipConceptoSueldoSearch()));

        mockMvc.perform(post("/api/haberes/core/afipConceptoSueldo/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + AFIP_CONCEPTO_SUELDO_SEARCH_JSON + "]", JsonCompareMode.STRICT));
    }
}
