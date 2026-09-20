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
import um.haberes.core.kotlin.model.Curso;
import um.haberes.core.service.CursoCargoService;
import um.haberes.core.service.CursoService;

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
class CursoControllerTest {

    @Mock
    private CursoService service;

    @Mock
    private CursoCargoService cursoCargoService;

    @InjectMocks
    private CursoController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private Curso sampleCurso() {
        Curso curso = new Curso();
        curso.setCursoId(1L);
        curso.setNombre("Curso 1");
        curso.setFacultadId(2);
        curso.setGeograficaId(3);
        curso.setAnual((byte) 1);
        curso.setSemestre1((byte) 1);
        curso.setSemestre2((byte) 0);
        curso.setNivelId(4);
        curso.setAdicionalCargaHoraria((byte) 2);
        return curso;
    }

    private String cursoJson() {
        return """
                {
                  "cursoId": 1,
                  "nombre": "Curso 1",
                  "facultadId": 2,
                  "geograficaId": 3,
                  "anual": 1,
                  "semestre1": 1,
                  "semestre2": 0,
                  "nivelId": 4,
                  "adicionalCargaHoraria": 2,
                  "facultad": null,
                  "geografica": null,
                  "nivel": null,
                  "created": null,
                  "updated": null
                }
                """;
    }

    private String cursoListJson() {
        return """
                [
                  {
                    "cursoId": 1,
                    "nombre": "Curso 1",
                    "facultadId": 2,
                    "geograficaId": 3,
                    "anual": 1,
                    "semestre1": 1,
                    "semestre2": 0,
                    "nivelId": 4,
                    "adicionalCargaHoraria": 2,
                    "facultad": null,
                    "geografica": null,
                    "nivel": null,
                    "created": null,
                    "updated": null
                  }
                ]
                """;
    }

    @Test
    void findAll_returnsOkWithListOfCursos() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleCurso()));

        mockMvc.perform(get("/api/haberes/core/curso/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(cursoListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByGeograficaAndConditions_returnsOkWithListOfCursos() throws Exception {
        when(service.findAllByGeograficaAndConditions(2, 3, List.of("anual=1")))
                .thenReturn(List.of(sampleCurso()));

        mockMvc.perform(post("/api/haberes/core/curso/geografica/{facultadId}/{geograficaId}", 2, 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of("anual=1"))))
                .andExpect(status().isOk())
                .andExpect(content().json(cursoListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByFacultadIdAndGeograficaId_returnsOkWithListOfCursos() throws Exception {
        when(service.findAllByFacultadIdAndGeograficaId(2, 3)).thenReturn(List.of(sampleCurso()));

        mockMvc.perform(get("/api/haberes/core/curso/geograficasinfiltro/{facultadId}/{geograficaId}", 2, 3))
                .andExpect(status().isOk())
                .andExpect(content().json(cursoListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByFacultadIdAndGeograficaIdAndAnhoAndMes_returnsOkWithListOfCursos() throws Exception {
        when(service.findAllByFacultadIdAndGeograficaIdAndAnhoAndMes(2, 3, 2024, 6, cursoCargoService))
                .thenReturn(List.of(sampleCurso()));

        mockMvc.perform(get("/api/haberes/core/curso/facultad/{facultadId}/geografica/{geograficaId}/periodo/{anho}/{mes}",
                        2, 3, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(cursoListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByCursoId_returnsOkWithCursoBody() throws Exception {
        when(service.findByCursoId(1L)).thenReturn(sampleCurso());

        mockMvc.perform(get("/api/haberes/core/curso/{cursoId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(cursoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void add_returnsOkWithCursoBody() throws Exception {
        when(service.add(any(Curso.class))).thenReturn(sampleCurso());

        mockMvc.perform(post("/api/haberes/core/curso/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCurso())))
                .andExpect(status().isOk())
                .andExpect(content().json(cursoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithCursoBody() throws Exception {
        when(service.update(any(Curso.class), eq(1L))).thenReturn(sampleCurso());

        mockMvc.perform(put("/api/haberes/core/curso/{cursoId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCurso())))
                .andExpect(status().isOk())
                .andExpect(content().json(cursoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void deleteByCursoId_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/curso/{cursoId}", 1L))
                .andExpect(status().isNoContent());
    }
}
