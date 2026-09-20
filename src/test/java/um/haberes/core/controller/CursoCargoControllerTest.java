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
import um.haberes.core.hexagonal.cursos.curso_cargo.application.exception.CursoCargoException;
import um.haberes.core.hexagonal.cursos.curso_cargo.application.service.CursoCargoService;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.web.controller.CursoCargoController;
import um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.web.dto.CursoCargoResponse;
import um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.web.mapper.CursoCargoDtoMapper;

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
class CursoCargoControllerTest {

    @Mock
    private CursoCargoService service;

    @Mock
    private CursoCargoDtoMapper cursoCargoDtoMapper;

    @InjectMocks
    private CursoCargoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private CursoCargo sampleCursoCargo() {
        CursoCargo cursoCargo = new CursoCargo();
        cursoCargo.setCursoCargoId(1L);
        cursoCargo.setCursoId(2L);
        cursoCargo.setAnho(2024);
        cursoCargo.setMes(6);
        cursoCargo.setCargoTipoId(3);
        cursoCargo.setLegajoId(4L);
        cursoCargo.setHorasSemanales(new BigDecimal("10.5"));
        cursoCargo.setHorasTotales(new BigDecimal("105"));
        cursoCargo.setDesignacionTipoId(5);
        cursoCargo.setCategoriaId(6);
        cursoCargo.setDesarraigo((byte) 0);
        cursoCargo.setCursoCargoNovedadId(7L);
        return cursoCargo;
    }

    private CursoCargoResponse sampleResponse() {
        return CursoCargoResponse.builder()
                .cursoCargoId(1L)
                .cursoId(2L)
                .anho(2024)
                .mes(6)
                .cargoTipoId(3)
                .legajoId(4L)
                .horasSemanales(new BigDecimal("10.5"))
                .horasTotales(new BigDecimal("105"))
                .designacionTipoId(5)
                .categoriaId(6)
                .desarraigo((byte) 0)
                .cursoCargoNovedadId(7L)
                .build();
    }

    private String cursoCargoJson() {
        return """
                {
                  "cursoCargoId": 1,
                  "cursoId": 2,
                  "anho": 2024,
                  "mes": 6,
                  "cargoTipoId": 3,
                  "legajoId": 4,
                  "horasSemanales": 10.5,
                  "horasTotales": 105,
                  "designacionTipoId": 5,
                  "categoriaId": 6,
                  "desarraigo": 0,
                  "cursoCargoNovedadId": 7,
                  "curso": null,
                  "cargoTipo": null,
                  "persona": null,
                  "designacionTipo": null,
                  "categoria": null
                }
                """;
    }

    private String cursoCargoListJson() {
        return "[" + cursoCargoJson() + "]";
    }

    private String cursoCargoRequestJson() {
        return """
                {
                  "cursoId": 2,
                  "anho": 2024,
                  "mes": 6,
                  "cargoTipoId": 3,
                  "legajoId": 4,
                  "horasSemanales": 10.5,
                  "horasTotales": 105,
                  "designacionTipoId": 5,
                  "categoriaId": 6,
                  "desarraigo": 0,
                  "cursoCargoNovedadId": 7
                }
                """;
    }

    @Test
    void findAllByLegajo_returnsOkWithListOfCursoCargos() throws Exception {
        when(service.findAllByLegajo(4L, 2024, 6)).thenReturn(List.of(sampleCursoCargo()));
        when(cursoCargoDtoMapper.toResponse(any(CursoCargo.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/cursoCargo/legajo/{legajoId}/{anho}/{mes}", 4L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(cursoCargoListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByLegajoAndNivel_returnsOkWithListOfCursoCargos() throws Exception {
        when(service.findAllByLegajoAndNivel(4L, 2024, 6, 5)).thenReturn(List.of(sampleCursoCargo()));
        when(cursoCargoDtoMapper.toResponse(any(CursoCargo.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/cursoCargo/legajonivel/{legajoId}/{anho}/{mes}/{nivelId}",
                        4L, 2024, 6, 5))
                .andExpect(status().isOk())
                .andExpect(content().json(cursoCargoListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByLegajoDesarraigo_returnsOkWithListOfCursoCargos() throws Exception {
        when(service.findAllByLegajoDesarraigo(4L, 2024, 6)).thenReturn(List.of(sampleCursoCargo()));
        when(cursoCargoDtoMapper.toResponse(any(CursoCargo.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/cursoCargo/legajodesarraigo/{legajoId}/{anho}/{mes}", 4L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(cursoCargoListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByCurso_returnsOkWithListOfCursoCargos() throws Exception {
        when(service.findAllByCurso(2L, 2024, 6)).thenReturn(List.of(sampleCursoCargo()));
        when(cursoCargoDtoMapper.toResponse(any(CursoCargo.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/cursoCargo/curso/{cursoId}/{anho}/{mes}", 2L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(cursoCargoListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByFacultad_returnsOkWithListOfCursoCargos() throws Exception {
        when(service.findAllByFacultad(4L, 2024, 6, 3)).thenReturn(List.of(sampleCursoCargo()));
        when(cursoCargoDtoMapper.toResponse(any(CursoCargo.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/cursoCargo/facultad/{legajoId}/{anho}/{mes}/{facultadId}",
                        4L, 2024, 6, 3))
                .andExpect(status().isOk())
                .andExpect(content().json(cursoCargoListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByCargoTipo_returnsOkWithListOfCursoCargos() throws Exception {
        when(service.findAllByCargoTipo(4L, 2024, 6, 3, 2, (byte) 1, (byte) 1, (byte) 0, 5))
                .thenReturn(List.of(sampleCursoCargo()));
        when(cursoCargoDtoMapper.toResponse(any(CursoCargo.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/cursoCargo/cargoTipo/{legajoId}/{anho}/{mes}/{facultadId}/"
                        + "{geograficaId}/{anual}/{semestre1}/{semestre2}/{cargoTipoId}",
                        4L, 2024, 6, 3, 2, 1, 1, 0, 5))
                .andExpect(status().isOk())
                .andExpect(content().json(cursoCargoListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByCursoAny_returnsOkWithListOfCursoCargos() throws Exception {
        when(service.findAnyByCursoId(2L)).thenReturn(List.of(sampleCursoCargo()));
        when(cursoCargoDtoMapper.toResponse(any(CursoCargo.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/cursoCargo/cursoany/{cursoId}", 2L))
                .andExpect(status().isOk())
                .andExpect(content().json(cursoCargoListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAnyByPeriodo_returnsOkWithListOfCursoCargos() throws Exception {
        when(service.findAnyByAnhoAndMes(2024, 6)).thenReturn(List.of(sampleCursoCargo()));
        when(cursoCargoDtoMapper.toResponse(any(CursoCargo.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/cursoCargo/periodoany/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(cursoCargoListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByPeriodo_returnsOkWithListOfCursoCargos() throws Exception {
        when(service.findAllByAnhoAndMes(2024, 6)).thenReturn(List.of(sampleCursoCargo()));
        when(cursoCargoDtoMapper.toResponse(any(CursoCargo.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/cursoCargo/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(cursoCargoListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByCursoCargoId_returnsOkWithCursoCargoBody() throws Exception {
        when(service.findByCursoCargoId(1L)).thenReturn(sampleCursoCargo());
        when(cursoCargoDtoMapper.toResponse(any(CursoCargo.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/cursoCargo/{cursoCargoId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(cursoCargoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByCursoCargoId_whenServiceThrowsCursoCargoException_returnsBadRequest() throws Exception {
        when(service.findByCursoCargoId(99L)).thenThrow(new CursoCargoException(99L));

        mockMvc.perform(get("/api/haberes/core/cursoCargo/{cursoCargoId}", 99L))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByUnique_returnsOkWithCursoCargoBody() throws Exception {
        when(service.findByUnique(2L, 2024, 6, 3, 4L)).thenReturn(sampleCursoCargo());
        when(cursoCargoDtoMapper.toResponse(any(CursoCargo.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/cursoCargo/unique/{cursoId}/{anho}/{mes}/{cargoTipoId}/{legajoId}",
                        2L, 2024, 6, 3, 4L))
                .andExpect(status().isOk())
                .andExpect(content().json(cursoCargoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_whenServiceThrowsCursoCargoException_returnsBadRequest() throws Exception {
        when(service.findByUnique(99L, 2024, 6, 3, 4L))
                .thenThrow(new CursoCargoException(99L, 2024, 6, 3, 4L));

        mockMvc.perform(get("/api/haberes/core/cursoCargo/unique/{cursoId}/{anho}/{mes}/{cargoTipoId}/{legajoId}",
                        99L, 2024, 6, 3, 4L))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByLegajoId_returnsOkWithCursoCargoBody() throws Exception {
        when(service.findByLegajo(2L, 2024, 6, 4L)).thenReturn(sampleCursoCargo());
        when(cursoCargoDtoMapper.toResponse(any(CursoCargo.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/cursoCargo/legajo/{cursoId}/{anho}/{mes}/{legajoId}",
                        2L, 2024, 6, 4L))
                .andExpect(status().isOk())
                .andExpect(content().json(cursoCargoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByLegajoId_whenServiceThrowsCursoCargoException_returnsBadRequest() throws Exception {
        when(service.findByLegajo(99L, 2024, 6, 4L)).thenThrow(new CursoCargoException(99L, 2024, 6, 4L));

        mockMvc.perform(get("/api/haberes/core/cursoCargo/legajo/{cursoId}/{anho}/{mes}/{legajoId}",
                        99L, 2024, 6, 4L))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_returnsOkWithCursoCargoBody() throws Exception {
        when(cursoCargoDtoMapper.toDomain(any())).thenReturn(sampleCursoCargo());
        when(service.add(any(CursoCargo.class))).thenReturn(sampleCursoCargo());
        when(cursoCargoDtoMapper.toResponse(any(CursoCargo.class))).thenReturn(sampleResponse());

        mockMvc.perform(post("/api/haberes/core/cursoCargo/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cursoCargoRequestJson()))
                .andExpect(status().isOk())
                .andExpect(content().json(cursoCargoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithCursoCargoBody() throws Exception {
        when(cursoCargoDtoMapper.toDomain(any())).thenReturn(sampleCursoCargo());
        when(service.update(any(CursoCargo.class), eq(1L))).thenReturn(sampleCursoCargo());
        when(cursoCargoDtoMapper.toResponse(any(CursoCargo.class))).thenReturn(sampleResponse());

        mockMvc.perform(put("/api/haberes/core/cursoCargo/{cursoCargoId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cursoCargoRequestJson()))
                .andExpect(status().isOk())
                .andExpect(content().json(cursoCargoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void deleteByCursoCargoId_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/cursoCargo/{cursoCargoId}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteByUnique_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/cursoCargo/unique/{cursoId}/{anho}/{mes}/{cargoTipoId}/{legajoId}",
                        2L, 2024, 6, 3, 4L))
                .andExpect(status().isNoContent());
    }
}
