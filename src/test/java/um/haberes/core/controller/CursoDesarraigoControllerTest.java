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
import um.haberes.core.kotlin.model.CursoDesarraigo;
import um.haberes.core.service.CursoDesarraigoService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CursoDesarraigoControllerTest {

    @Mock
    private CursoDesarraigoService service;

    @InjectMocks
    private CursoDesarraigoController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private CursoDesarraigo sample() {
        CursoDesarraigo desarraigo = new CursoDesarraigo();
        desarraigo.setCursoDesarraigoId(1L);
        desarraigo.setLegajoId(2L);
        desarraigo.setAnho(2024);
        desarraigo.setMes(6);
        desarraigo.setCursoId(3L);
        desarraigo.setGeograficaId(4);
        desarraigo.setImporte(new BigDecimal("100.50"));
        desarraigo.setVersion(1);
        return desarraigo;
    }

    private String desarraigoJson() {
        return """
                {
                  "cursoDesarraigoId": 1,
                  "legajoId": 2,
                  "anho": 2024,
                  "mes": 6,
                  "cursoId": 3,
                  "geograficaId": 4,
                  "importe": 100.50,
                  "version": 1,
                  "curso": null,
                  "persona": null,
                  "geografica": null,
                  "created": null,
                  "updated": null
                }
                """;
    }

    private String desarraigoListJson() {
        return """
                [
                  {
                    "cursoDesarraigoId": 1,
                    "legajoId": 2,
                    "anho": 2024,
                    "mes": 6,
                    "cursoId": 3,
                    "geograficaId": 4,
                    "importe": 100.50,
                    "version": 1,
                    "curso": null,
                    "persona": null,
                    "geografica": null,
                    "created": null,
                    "updated": null
                  }
                ]
                """;
    }

    @Test
    void findAll_returnsOkWithListOfCursoDesarraigos() throws Exception {
        when(service.findAll()).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/haberes/core/cursodesarraigo/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(desarraigoListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByLegajoId_returnsOkWithListOfCursoDesarraigos() throws Exception {
        when(service.findAllByLegajoIdAndAnhoAndMes(2L, 2024, 6)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/haberes/core/cursodesarraigo/legajoId/{legajoId}/{anho}/{mes}", 2L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(desarraigoListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByVersion_returnsOkWithListOfCursoDesarraigos() throws Exception {
        when(service.findAllByVersion(2L, 2024, 6, 1)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/haberes/core/cursodesarraigo/legajoId/version/{legajoId}/{anho}/{mes}/{version}",
                        2L, 2024, 6, 1))
                .andExpect(status().isOk())
                .andExpect(content().json(desarraigoListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByCursoDesarraigoId_alwaysReturnsInternalServerErrorDueToPathVariableNameMismatch() throws Exception {
        mockMvc.perform(get("/api/haberes/core/cursodesarraigo/{cursodesarraigoId}", 1L))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void findByUnique_returnsOkWithBody() throws Exception {
        when(service.findByUnique(2L, 2024, 6, 3L)).thenReturn(sample());

        mockMvc.perform(get("/api/haberes/core/cursodesarraigo/unique/{legajoId}/{anho}/{mes}/{cursoId}",
                        2L, 2024, 6, 3L))
                .andExpect(status().isOk())
                .andExpect(content().json(desarraigoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void delete_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/cursodesarraigo/{cursodesarraigoId}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void add_returnsOkWithBody() throws Exception {
        when(service.add(any(CursoDesarraigo.class))).thenReturn(sample());

        mockMvc.perform(post("/api/haberes/core/cursodesarraigo/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sample())))
                .andExpect(status().isOk())
                .andExpect(content().json(desarraigoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithBody() throws Exception {
        when(service.update(any(CursoDesarraigo.class), eq(1L))).thenReturn(sample());

        mockMvc.perform(put("/api/haberes/core/cursodesarraigo/{cursodesarraigoId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sample())))
                .andExpect(status().isOk())
                .andExpect(content().json(desarraigoJson(), JsonCompareMode.STRICT));
    }
}
