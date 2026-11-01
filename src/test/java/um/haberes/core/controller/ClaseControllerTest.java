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
import um.haberes.core.exception.ClaseException;
import um.haberes.core.model.ClaseEntity;
import um.haberes.core.service.ClaseService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ClaseControllerTest {

    @Mock
    private ClaseService service;

    @InjectMocks
    private ClaseController controller;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private ClaseEntity sampleClase() {
        ClaseEntity clase = new ClaseEntity();
        clase.setClaseId(1);
        clase.setNombre("Primera");
        clase.setValorHora(new BigDecimal("45.60"));
        return clase;
    }

    private String claseJson() {
        return """
                {
                  "claseId": 1,
                  "nombre": "Primera",
                  "valorHora": 45.60,
                  "created": null,
                  "updated": null
                }
                """;
    }

    @Test
    void findAll_returnsOkWithListOfClase() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleClase()));

        mockMvc.perform(get("/api/haberes/core/clase/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [ {
                          "claseId": 1,
                          "nombre": "Primera",
                          "valorHora": 45.60,
                          "created": null,
                          "updated": null
                        } ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findByClaseId_returnsOkWithClase() throws Exception {
        when(service.findByClaseId(1)).thenReturn(sampleClase());

        mockMvc.perform(get("/api/haberes/core/clase/{claseId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(claseJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByClaseId_whenServiceThrowsClaseException_returnsBadRequest() throws Exception {
        when(service.findByClaseId(999)).thenThrow(new ClaseException(999));

        mockMvc.perform(get("/api/haberes/core/clase/{claseId}", 999))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findLast_returnsOkWithClase() throws Exception {
        when(service.findLast()).thenReturn(sampleClase());

        mockMvc.perform(get("/api/haberes/core/clase/last"))
                .andExpect(status().isOk())
                .andExpect(content().json(claseJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findLast_whenServiceThrowsClaseException_returnsBadRequest() throws Exception {
        when(service.findLast()).thenThrow(new ClaseException());

        mockMvc.perform(get("/api/haberes/core/clase/last"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void delete_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/clase/{claseId}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void add_returnsOkWithClase() throws Exception {
        when(service.add(any(ClaseEntity.class))).thenReturn(sampleClase());

        mockMvc.perform(post("/api/haberes/core/clase/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleClase())))
                .andExpect(status().isOk())
                .andExpect(content().json(claseJson(), JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithClase() throws Exception {
        when(service.update(any(ClaseEntity.class), anyInt())).thenReturn(sampleClase());

        mockMvc.perform(put("/api/haberes/core/clase/{claseId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleClase())))
                .andExpect(status().isOk())
                .andExpect(content().json(claseJson(), JsonCompareMode.STRICT));
    }
}
