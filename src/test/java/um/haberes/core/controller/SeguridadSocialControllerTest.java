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
import um.haberes.core.exception.SeguridadSocialException;
import um.haberes.core.kotlin.model.SeguridadSocial;
import um.haberes.core.service.SeguridadSocialService;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SeguridadSocialControllerTest {

    @Mock
    private SeguridadSocialService service;

    private SeguridadSocialController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new SeguridadSocialController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private SeguridadSocial sampleSeguridadSocial() {
        SeguridadSocial seguridadSocial = new SeguridadSocial();
        seguridadSocial.setSeguridadSocialId(4L);
        seguridadSocial.setAnho(2024);
        seguridadSocial.setMes(6);
        seguridadSocial.setCc351(new BigDecimal("1.50"));
        seguridadSocial.setCc301(new BigDecimal("2.50"));
        seguridadSocial.setCc352(new BigDecimal("3.50"));
        seguridadSocial.setCc302(new BigDecimal("4.50"));
        seguridadSocial.setCc312(new BigDecimal("5.50"));
        seguridadSocial.setCc028(new BigDecimal("6.50"));
        return seguridadSocial;
    }

    private String seguridadSocialBodyJson() throws Exception {
        return new ObjectMapper().writeValueAsString(sampleSeguridadSocial());
    }

    private static final String SEGURIDAD_SOCIAL_JSON = """
            {
              "seguridadSocialId": 4,
              "anho": 2024,
              "mes": 6,
              "cc351": 1.50,
              "cc301": 2.50,
              "cc352": 3.50,
              "cc302": 4.50,
              "cc312": 5.50,
              "cc028": 6.50,
              "created": null,
              "updated": null
            }
            """;

    @Test
    void findByUnique_returnsOkWithSeguridadSocialBody() throws Exception {
        when(service.findByUnique(2024, 6)).thenReturn(sampleSeguridadSocial());

        mockMvc.perform(get("/api/haberes/core/seguridadSocial/unique/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(SEGURIDAD_SOCIAL_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_whenServiceThrowsSeguridadSocialException_returnsBadRequest() throws Exception {
        when(service.findByUnique(2024, 1)).thenThrow(new SeguridadSocialException(2024, 1));

        mockMvc.perform(get("/api/haberes/core/seguridadSocial/unique/{anho}/{mes}", 2024, 1))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_returnsOkWithSeguridadSocialBody() throws Exception {
        when(service.add(any(SeguridadSocial.class))).thenReturn(sampleSeguridadSocial());

        mockMvc.perform(post("/api/haberes/core/seguridadSocial/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(seguridadSocialBodyJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(SEGURIDAD_SOCIAL_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithSeguridadSocialBody() throws Exception {
        when(service.update(any(SeguridadSocial.class), any())).thenReturn(sampleSeguridadSocial());

        mockMvc.perform(put("/api/haberes/core/seguridadSocial/{seguridadSocialId}", 4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(seguridadSocialBodyJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(SEGURIDAD_SOCIAL_JSON, JsonCompareMode.STRICT));
    }
}
