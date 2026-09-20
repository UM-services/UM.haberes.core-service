package um.haberes.core.controller;

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
import um.haberes.core.hexagonal.cursos.curso_fusion.application.service.CursoFusionService;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.model.CursoFusion;
import um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.web.controller.CursoFusionController;
import um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.web.dto.CursoFusionResponse;
import um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.web.mapper.CursoFusionDtoMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CursoFusionControllerTest {

    @Mock
    private CursoFusionService service;

    @Mock
    private CursoFusionDtoMapper cursoFusionDtoMapper;

    @InjectMocks
    private CursoFusionController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private CursoFusion sample() {
        CursoFusion fusion = new CursoFusion();
        fusion.setCursoFusionId(1L);
        fusion.setLegajoId(2L);
        fusion.setAnho(2024);
        fusion.setMes(6);
        fusion.setFacultadId(3);
        fusion.setGeograficaId(4);
        fusion.setCargoTipoId(5);
        fusion.setDesignacionTipoId(6);
        fusion.setAnual((byte) 1);
        fusion.setCategoriaId(7);
        return fusion;
    }

    private CursoFusionResponse sampleResponse() {
        return CursoFusionResponse.builder()
                .cursoFusionId(1L)
                .legajoId(2L)
                .anho(2024)
                .mes(6)
                .facultadId(3)
                .geograficaId(4)
                .cargoTipoId(5)
                .designacionTipoId(6)
                .anual((byte) 1)
                .categoriaId(7)
                .build();
    }

    private String fusionJson() {
        return """
                {
                  "cursoFusionId": 1,
                  "legajoId": 2,
                  "anho": 2024,
                  "mes": 6,
                  "facultadId": 3,
                  "geograficaId": 4,
                  "cargoTipoId": 5,
                  "designacionTipoId": 6,
                  "anual": 1,
                  "categoriaId": 7
                }
                """;
    }

    private String fusionListJson() {
        return "[" + fusionJson() + "]";
    }

    private String fusionRequestJson() {
        return """
                {
                  "legajoId": 2,
                  "anho": 2024,
                  "mes": 6,
                  "facultadId": 3,
                  "geograficaId": 4,
                  "cargoTipoId": 5,
                  "designacionTipoId": 6,
                  "anual": 1,
                  "categoriaId": 7
                }
                """;
    }

    @Test
    void findAllByLegajoId_returnsOkWithListOfCursoFusions() throws Exception {
        when(service.findAllByLegajoId(2L, 2024, 6)).thenReturn(List.of(sample()));
        when(cursoFusionDtoMapper.toResponse(any(CursoFusion.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/cursofusion/legajo/{legajoId}/{anho}/{mes}", 2L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(fusionListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByLegajoIdAndFacultadId_returnsOkWithListOfCursoFusions() throws Exception {
        when(service.findAllByLegajoIdAndFacultadId(2L, 2024, 6, 3)).thenReturn(List.of(sample()));
        when(cursoFusionDtoMapper.toResponse(any(CursoFusion.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/cursofusion/legajofacultad/{legajoId}/{anho}/{mes}/{facultadId}",
                        2L, 2024, 6, 3))
                .andExpect(status().isOk())
                .andExpect(content().json(fusionListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void add_returnsOkWithCursoFusionBody() throws Exception {
        when(cursoFusionDtoMapper.toDomain(any())).thenReturn(sample());
        when(service.add(any(CursoFusion.class))).thenReturn(sample());
        when(cursoFusionDtoMapper.toResponse(any(CursoFusion.class))).thenReturn(sampleResponse());

        mockMvc.perform(post("/api/haberes/core/cursofusion/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fusionRequestJson()))
                .andExpect(status().isOk())
                .andExpect(content().json(fusionJson(), JsonCompareMode.STRICT));
    }

    @Test
    void deleteByCursofusionId_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/cursofusion/{cursoFusionId}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteByFacultadId_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/cursofusion/facultad/{legajoId}/{anho}/{mes}/{facultadId}/{geograficaId}",
                        2L, 2024, 6, 3, 4))
                .andExpect(status().isNoContent());
    }
}
