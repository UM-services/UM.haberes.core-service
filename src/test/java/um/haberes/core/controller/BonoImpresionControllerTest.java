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
import um.haberes.core.model.BonoImpresionEntity;
import um.haberes.core.service.BonoImpresionService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BonoImpresionControllerTest {

    @Mock
    private BonoImpresionService service;

    private BonoImpresionController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new BonoImpresionController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private BonoImpresionEntity sampleBonoImpresion() {
        BonoImpresionEntity bonoImpresion = new BonoImpresionEntity();
        bonoImpresion.setBonoImpresionId(1L);
        bonoImpresion.setLegajoId(100L);
        bonoImpresion.setAnho(2024);
        bonoImpresion.setMes(6);
        bonoImpresion.setLegajoIdSolicitud(200L);
        bonoImpresion.setIpAddress("127.0.0.1");
        return bonoImpresion;
    }

    @Test
    void add_returnsOkWithBonoImpresionBody() throws Exception {
        when(service.add(any(BonoImpresionEntity.class))).thenReturn(sampleBonoImpresion());

        mockMvc.perform(post("/api/haberes/core/bonoimpresion/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new BonoImpresionEntity())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "bonoImpresionId": 1,
                          "legajoId": 100,
                          "anho": 2024,
                          "mes": 6,
                          "legajoIdSolicitud": 200,
                          "fecha": null,
                          "ipAddress": "127.0.0.1",
                          "created": null,
                          "updated": null
                        }
                        """, JsonCompareMode.STRICT));
    }
}
