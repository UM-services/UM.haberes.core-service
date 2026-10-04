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
import um.haberes.core.exception.AntiguedadException;
import um.haberes.core.exception.view.AntiguedadPeriodoException;
import um.haberes.core.model.AntiguedadEntity;
import um.haberes.core.model.view.AntiguedadPeriodo;
import um.haberes.core.service.AntiguedadService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AntiguedadControllerTest {

    @Mock
    private AntiguedadService service;

    private AntiguedadController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new AntiguedadController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private AntiguedadEntity sampleAntiguedad() {
        AntiguedadEntity antiguedad = new AntiguedadEntity();
        antiguedad.setAntiguedadId(1L);
        antiguedad.setLegajoId(100L);
        antiguedad.setAnho(2024);
        antiguedad.setMes(6);
        antiguedad.setMesesDocentes(12);
        antiguedad.setMesesAdministrativos(3);
        return antiguedad;
    }

    private String antiguedadBodyJson() throws Exception {
        return new ObjectMapper().writeValueAsString(new AntiguedadEntity());
    }

    private static final String ANTIGUEDAD_JSON = """
            {
              "antiguedadId": 1,
              "legajoId": 100,
              "anho": 2024,
              "mes": 6,
              "mesesDocentes": 12,
              "mesesAdministrativos": 3,
              "persona": null,
              "created": null,
              "updated": null
            }
            """;

    @Test
    void findByUnique_returnsOkWithAntiguedadBody() throws Exception {
        when(service.findByUnique(100L, 2024, 6)).thenReturn(sampleAntiguedad());

        mockMvc.perform(get("/api/haberes/core/antiguedad/unique/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ANTIGUEDAD_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_whenServiceThrowsAntiguedadException_returnsBadRequest() throws Exception {
        when(service.findByUnique(100L, 2024, 6)).thenThrow(new AntiguedadException(100L, 2024, 6));

        mockMvc.perform(get("/api/haberes/core/antiguedad/unique/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findLastByUnique_returnsOkWithAntiguedadPeriodoBody() throws Exception {
        AntiguedadPeriodo periodo = new AntiguedadPeriodo();
        periodo.setAntiguedadId(1L);
        periodo.setLegajoId(100L);
        periodo.setAnho(2024);
        periodo.setMes(6);
        periodo.setMesesDocentes(12);
        periodo.setMesesAdministrativos(3);
        periodo.setPeriodo(202406L);
        when(service.findLastByUnique(100L, 2024, 6)).thenReturn(periodo);

        mockMvc.perform(get("/api/haberes/core/antiguedad/last/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "antiguedadId": 1,
                          "legajoId": 100,
                          "anho": 2024,
                          "mes": 6,
                          "mesesDocentes": 12,
                          "mesesAdministrativos": 3,
                          "periodo": 202406,
                          "created": null,
                          "updated": null
                        }
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findLastByUnique_whenServiceThrowsAntiguedadPeriodoException_returnsBadRequest() throws Exception {
        when(service.findLastByUnique(100L, 2024, 6)).thenThrow(new AntiguedadPeriodoException(100L, 2024, 6));

        mockMvc.perform(get("/api/haberes/core/antiguedad/last/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findAllByPeriodo_returnsOkWithListOfAntiguedad() throws Exception {
        when(service.findAllByPeriodo(2024, 6, 10)).thenReturn(List.of(sampleAntiguedad()));

        mockMvc.perform(get("/api/haberes/core/antiguedad/periodo/{anho}/{mes}/{limit}", 2024, 6, 10))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + ANTIGUEDAD_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByPeriodo_whenLimitIsZero_usesDefaultLimit30000() throws Exception {
        when(service.findAllByPeriodo(2024, 6, 30000)).thenReturn(List.of());

        mockMvc.perform(get("/api/haberes/core/antiguedad/periodo/{anho}/{mes}/{limit}", 2024, 6, 0))
                .andExpect(status().isOk())
                .andExpect(content().json("[]", JsonCompareMode.STRICT));
    }

    @Test
    void add_returnsOkWithAntiguedadBody() throws Exception {
        when(service.add(any(AntiguedadEntity.class))).thenReturn(sampleAntiguedad());

        mockMvc.perform(post("/api/haberes/core/antiguedad/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(antiguedadBodyJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ANTIGUEDAD_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithAntiguedadBody() throws Exception {
        when(service.update(any(AntiguedadEntity.class), anyLong())).thenReturn(sampleAntiguedad());

        mockMvc.perform(put("/api/haberes/core/antiguedad/{antiguedadId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(antiguedadBodyJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ANTIGUEDAD_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void saveAll_returnsOkWithListOfAntiguedad() throws Exception {
        when(service.saveAll(anyList())).thenReturn(List.of(sampleAntiguedad()));

        mockMvc.perform(put("/api/haberes/core/antiguedad/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(List.of(new AntiguedadEntity()))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + ANTIGUEDAD_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void calculate_returnsNoContent() throws Exception {
        mockMvc.perform(get("/api/haberes/core/antiguedad/calculate/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
}
