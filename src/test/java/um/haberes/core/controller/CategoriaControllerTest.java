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
import um.haberes.core.hexagonal.liquidaciones.categoria.application.exception.CategoriaException;
import um.haberes.core.hexagonal.liquidaciones.categoria.application.exception.CategoriaException;
import um.haberes.core.hexagonal.liquidaciones.categoria.application.service.CategoriaService;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.controller.CategoriaController;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaSearchResult;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.UploadedFile;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.mapper.CategoriaDtoMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoriaControllerTest {

    @Mock
    private CategoriaService service;

    private CategoriaController controller;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        controller = new CategoriaController(service, new CategoriaDtoMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private Categoria sampleCategoria() {
        Categoria categoria = new Categoria();
        categoria.setCategoriaId(1);
        categoria.setNombre("TITULAR");
        categoria.setBasico(new BigDecimal("10.50"));
        categoria.setDocente((byte) 1);
        categoria.setNoDocente((byte) 0);
        categoria.setLiquidaPorHora((byte) 0);
        categoria.setEstadoDocente(new BigDecimal("1.20"));
        return categoria;
    }

    private String categoriaJson() {
        return """
                {
                  "categoriaId": 1,
                  "nombre": "TITULAR",
                  "basico": 10.50,
                  "docente": 1,
                  "noDocente": 0,
                  "liquidaPorHora": 0,
                  "estadoDocente": 1.20
                                                    }
                """;
    }

    private String categoriaArrayJson() {
        return """
                [ {
                  "categoriaId": 1,
                  "nombre": "TITULAR",
                  "basico": 10.50,
                  "docente": 1,
                  "noDocente": 0,
                  "liquidaPorHora": 0,
                  "estadoDocente": 1.20
                                                    } ]
                """;
    }

    @Test
    void findAll_returnsOkWithListOfCategoria() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleCategoria()));

        mockMvc.perform(get("/api/haberes/core/categoria/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(categoriaArrayJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllNoGrado_returnsOkWithListOfCategoria() throws Exception {
        when(service.findAllNoDocentes()).thenReturn(List.of(sampleCategoria()));

        mockMvc.perform(get("/api/haberes/core/categoria/nogrado"))
                .andExpect(status().isOk())
                .andExpect(content().json(categoriaArrayJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllSearch_returnsOkWithListOfCategoriaSearch() throws Exception {
        CategoriaSearchResult categoriaSearch = new CategoriaSearchResult();
        categoriaSearch.setCategoriaId(5);
        categoriaSearch.setNombre("Auxiliar");
        categoriaSearch.setBasico(new BigDecimal("9.75"));
        categoriaSearch.setSearch("auxiliar 9.75");
        when(service.findAllSearch("aux")).thenReturn(List.of(categoriaSearch));

        mockMvc.perform(get("/api/haberes/core/categoria/search/{chain}", "aux"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [ {
                          "categoriaId": 5,
                          "nombre": "Auxiliar",
                          "basico": 9.75,
                          "search": "auxiliar 9.75"
                        } ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findAllNoDocente_returnsOkWithListOfCategoria() throws Exception {
        when(service.findAllNoDocenteByPeriodo(2024, 6)).thenReturn(List.of(sampleCategoria()));

        mockMvc.perform(get("/api/haberes/core/categoria/nodocente/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(categoriaArrayJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllNoDocentesByLegajoId_returnsOkWithListOfCategoria() throws Exception {
        when(service.findAllNoDocenteByLegajoId(100L, 2024, 6)).thenReturn(List.of(sampleCategoria()));

        mockMvc.perform(get("/api/haberes/core/categoria/nodocentelegajo/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(categoriaArrayJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByCategoriaId_returnsOkWithCategoria() throws Exception {
        when(service.findByCategoriaId(1)).thenReturn(sampleCategoria());

        mockMvc.perform(get("/api/haberes/core/categoria/{categoriaId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(categoriaJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByCategoriaId_whenServiceThrowsCategoriaException_returnsBadRequest() throws Exception {
        when(service.findByCategoriaId(999)).thenThrow(new CategoriaException(999));

        mockMvc.perform(get("/api/haberes/core/categoria/{categoriaId}", 999))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findLast_returnsOkWithCategoria() throws Exception {
        when(service.findLast()).thenReturn(sampleCategoria());

        mockMvc.perform(get("/api/haberes/core/categoria/last"))
                .andExpect(status().isOk())
                .andExpect(content().json(categoriaJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findLast_whenServiceThrowsCategoriaException_returnsBadRequest() throws Exception {
        when(service.findLast()).thenThrow(new CategoriaException());

        mockMvc.perform(get("/api/haberes/core/categoria/last"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void delete_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/categoria/{categoriaId}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void add_returnsOkWithCategoria() throws Exception {
        when(service.add(any(Categoria.class), anyInt(), anyInt())).thenReturn(sampleCategoria());

        mockMvc.perform(post("/api/haberes/core/categoria/{anho}/{mes}", 2024, 6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCategoria())))
                .andExpect(status().isOk())
                .andExpect(content().json(categoriaJson(), JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithCategoria() throws Exception {
        when(service.update(any(Categoria.class), anyInt(), anyInt(), anyInt())).thenReturn(sampleCategoria());

        mockMvc.perform(put("/api/haberes/core/categoria/{categoriaId}/{anho}/{mes}", 1, 2024, 6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCategoria())))
                .andExpect(status().isOk())
                .andExpect(content().json(categoriaJson(), JsonCompareMode.STRICT));
    }

    @Test
    void saveAll_returnsOkWithListOfCategoria() throws Exception {
        when(service.saveAll(anyList(), anyInt(), anyInt())).thenReturn(List.of(sampleCategoria()));

        mockMvc.perform(put("/api/haberes/core/categoria/all/{anho}/{mes}", 2024, 6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(sampleCategoria()))))
                .andExpect(status().isOk())
                .andExpect(content().json(categoriaArrayJson(), JsonCompareMode.STRICT));
    }

    @Test
    void upload_returnsNoContent() throws Exception {
        UploadedFile fileInfo = new UploadedFile("categorias.xlsx", "YWJjZGVm");

        mockMvc.perform(post("/api/haberes/core/categoria/upload/{anho}/{mes}", 2024, 6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fileInfo)))
                .andExpect(status().isNoContent());
    }

    @Test
    void upload_whenServiceThrowsCategoriaException_returnsBadRequest() throws Exception {
        UploadedFile fileInfo = new UploadedFile("categorias.xlsx", "YWJjZGVm");
        doThrow(new CategoriaException("titulo no encontrado"))
                .when(service).upload(any(UploadedFile.class), anyInt(), anyInt());

        mockMvc.perform(post("/api/haberes/core/categoria/upload/{anho}/{mes}", 2024, 6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fileInfo)))
                .andExpect(status().isBadRequest());
    }
}
