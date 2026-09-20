package um.haberes.core.controller.view;

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
import um.haberes.core.model.view.NovedadExiste;
import um.haberes.core.service.view.NovedadExisteService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NovedadExisteControllerTest {

    @Mock
    private NovedadExisteService service;

    @InjectMocks
    private NovedadExisteController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private NovedadExiste sampleNovedadExiste() {
        NovedadExiste novedadExiste = new NovedadExiste();
        novedadExiste.setUniqueId("100-2024-6");
        novedadExiste.setLegajoId(100L);
        novedadExiste.setAnho(2024);
        novedadExiste.setMes(6);
        return novedadExiste;
    }

    @Test
    void findAllByPeriodo_returnsOkWithNovedadExisteListBody() throws Exception {
        when(service.findAllByPeriodo(2024, 6)).thenReturn(List.of(sampleNovedadExiste()));

        mockMvc.perform(get("/api/haberes/core/novedadExiste/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          {
                            "uniqueId": "100-2024-6",
                            "legajoId": 100,
                            "anho": 2024,
                            "mes": 6
                          }
                        ]
                        """, JsonCompareMode.STRICT));
    }
}
