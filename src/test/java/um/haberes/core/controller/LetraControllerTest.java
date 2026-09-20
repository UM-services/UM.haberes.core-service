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
import um.haberes.core.kotlin.model.Letra;
import um.haberes.core.service.LetraService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LetraControllerTest {

    @Mock
    private LetraService service;

    private LetraController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new LetraController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private Letra sampleLetra() {
        Letra letra = new Letra();
        letra.setLetraId(1L);
        letra.setLegajoId(100L);
        letra.setAnho(2024);
        letra.setMes(6);
        letra.setNeto(new BigDecimal("105.00"));
        letra.setCadena("A.B.C");
        return letra;
    }

    private final String expectedLetra = """
            {
              "letraId": 1,
              "legajoId": 100,
              "anho": 2024,
              "mes": 6,
              "neto": 105.00,
              "cadena": "A.B.C",
              "created": null,
              "updated": null
            }
            """;

    @Test
    void findAllByPeriodo_returnsOkWithListOfLetra() throws Exception {
        when(service.findAllByPeriodo(2024, 6, 100)).thenReturn(List.of(sampleLetra()));

        mockMvc.perform(get("/api/haberes/core/letra/periodo/{anho}/{mes}/{limit}", 2024, 6, 100))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLetra + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByPeriodo_whenLimitIsZero_usesDefaultLimitOf30000() throws Exception {
        when(service.findAllByPeriodo(2024, 6, 30000)).thenReturn(List.of(sampleLetra()));

        mockMvc.perform(get("/api/haberes/core/letra/periodo/{anho}/{mes}/{limit}", 2024, 6, 0))
                .andExpect(status().isOk())
                .andExpect(content().json("[" + expectedLetra + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_returnsOkWithLetra() throws Exception {
        when(service.findByUnique(100L, 2024, 6)).thenReturn(sampleLetra());

        mockMvc.perform(get("/api/haberes/core/letra/unique/{legajoId}/{anho}/{mes}", 100L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedLetra, JsonCompareMode.STRICT));
    }

    @Test
    void add_returnsOkWithSavedLetra() throws Exception {
        when(service.add(any(Letra.class))).thenReturn(sampleLetra());

        String body = new ObjectMapper().writeValueAsString(sampleLetra());

        mockMvc.perform(post("/api/haberes/core/letra/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedLetra, JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithUpdatedLetra() throws Exception {
        when(service.update(any(Letra.class), anyLong())).thenReturn(sampleLetra());

        String body = new ObjectMapper().writeValueAsString(sampleLetra());

        mockMvc.perform(put("/api/haberes/core/letra/{letraId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedLetra, JsonCompareMode.STRICT));
    }

    @Test
    void saveAll_returnsOkWithListOfSavedLetra() throws Exception {
        when(service.saveAll(anyList())).thenReturn(List.of(sampleLetra()));

        String body = new ObjectMapper().writeValueAsString(List.of(sampleLetra()));

        mockMvc.perform(put("/api/haberes/core/letra/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedLetra + "]", JsonCompareMode.STRICT));
    }

    @Test
    void deleteByPeriodo_returnsNoContentWithEmptyBody() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/letra/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
}
