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
import um.haberes.core.model.NivelEntity;
import um.haberes.core.service.NivelService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NivelControllerTest {

    @Mock
    private NivelService service;

    private NivelController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new NivelController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private NivelEntity sampleNivel() {
        return new NivelEntity(1, "Grado");
    }

    @Test
    void findAll_returnsOkWithListOfNivel() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleNivel()));

        mockMvc.perform(get("/api/haberes/core/nivel/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          {
                            "nivelId": 1,
                            "nombre": "Grado",
                            "created": null,
                            "updated": null
                          }
                        ]
                        """, JsonCompareMode.STRICT));
    }
}
