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
import um.haberes.core.exception.AfipSituacionException;
import um.haberes.core.model.AfipSituacionEntity;
import um.haberes.core.service.AfipSituacionService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AfipSituacionControllerTest {

    @Mock
    private AfipSituacionService service;

    @InjectMocks
    private AfipSituacionController controller;

    private MockMvc mockMvc;

    private static final String AFIP_SITUACION_JSON = """
            {
              "afipSituacionId": 5,
              "descripcion": "Situacion de revista",
              "created": null,
              "updated": null
            }
            """;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private AfipSituacionEntity sampleAfipSituacion() {
        AfipSituacionEntity afipSituacion = new AfipSituacionEntity();
        afipSituacion.setAfipSituacionId(5);
        afipSituacion.setDescripcion("Situacion de revista");
        return afipSituacion;
    }

    @Test
    void findAll_returnsOkWithListOfAfipSituaciones() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleAfipSituacion()));

        mockMvc.perform(get("/api/haberes/core/afipSituacion/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + AFIP_SITUACION_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByAfipSituacionId_returnsOkWithAfipSituacionBody() throws Exception {
        when(service.findByAfipSituacionId(5)).thenReturn(sampleAfipSituacion());

        mockMvc.perform(get("/api/haberes/core/afipSituacion/{afipSituacionId}", 5))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(AFIP_SITUACION_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void findByAfipSituacionId_whenServiceThrowsAfipSituacionException_returnsBadRequest() throws Exception {
        when(service.findByAfipSituacionId(99)).thenThrow(new AfipSituacionException(99));

        mockMvc.perform(get("/api/haberes/core/afipSituacion/{afipSituacionId}", 99))
                .andExpect(status().isBadRequest());
    }
}
