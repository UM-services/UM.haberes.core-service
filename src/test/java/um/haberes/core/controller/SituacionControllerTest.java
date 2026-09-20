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
import um.haberes.core.kotlin.model.Situacion;
import um.haberes.core.service.SituacionService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SituacionControllerTest {

    @Mock
    private SituacionService service;

    private SituacionController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new SituacionController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private Situacion sampleSituacion() {
        Situacion situacion = new Situacion();
        situacion.setSituacionId(5);
        situacion.setNombre("TITULAR");
        situacion.setInterino((byte) 0);
        situacion.setOrdinario((byte) 1);
        situacion.setPlanta((byte) 1);
        situacion.setContratado((byte) 0);
        situacion.setSecundario((byte) 0);
        return situacion;
    }

    private static final String SITUACION_JSON = """
            {
              "situacionId": 5,
              "nombre": "TITULAR",
              "interino": 0,
              "ordinario": 1,
              "planta": 1,
              "contratado": 0,
              "secundario": 0,
              "created": null,
              "updated": null
            }
            """;

    @Test
    void findAll_returnsOkWithListOfSituacion() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleSituacion()));

        mockMvc.perform(get("/api/haberes/core/situacion/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + SITUACION_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findBySituacionID_returnsOkWithSituacionBody() throws Exception {
        when(service.findBySituacionId(5)).thenReturn(sampleSituacion());

        mockMvc.perform(get("/api/haberes/core/situacion/{situacionId}", 5))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(SITUACION_JSON, JsonCompareMode.STRICT));
    }
}
