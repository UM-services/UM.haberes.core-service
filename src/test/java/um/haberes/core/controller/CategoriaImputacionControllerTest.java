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
import um.haberes.core.kotlin.model.CategoriaImputacion;
import um.haberes.core.service.CategoriaImputacionService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoriaImputacionControllerTest {

    @Mock
    private CategoriaImputacionService service;

    @InjectMocks
    private CategoriaImputacionController controller;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private CategoriaImputacion sampleCategoriaImputacion() {
        CategoriaImputacion categoriaImputacion = new CategoriaImputacion();
        categoriaImputacion.setCategoriaImputacionId(1L);
        categoriaImputacion.setDependenciaId(2);
        categoriaImputacion.setFacultadId(3);
        categoriaImputacion.setGeograficaId(4);
        categoriaImputacion.setCategoriaId(5);
        categoriaImputacion.setCuentaSueldos(new BigDecimal("3100.10"));
        categoriaImputacion.setCuentaAportes(new BigDecimal("3200.20"));
        return categoriaImputacion;
    }

    private String categoriaImputacionJson() {
        return """
                {
                  "categoriaImputacionId": 1,
                  "dependenciaId": 2,
                  "facultadId": 3,
                  "geograficaId": 4,
                  "categoriaId": 5,
                  "cuentaSueldos": 3100.10,
                  "cuentaAportes": 3200.20,
                  "created": null,
                  "updated": null
                }
                """;
    }

    @Test
    void findAll_returnsOkWithListOfCategoriaImputacion() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleCategoriaImputacion()));

        mockMvc.perform(get("/api/haberes/core/categoriaimputacion/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [ {
                          "categoriaImputacionId": 1,
                          "dependenciaId": 2,
                          "facultadId": 3,
                          "geograficaId": 4,
                          "categoriaId": 5,
                          "cuentaSueldos": 3100.10,
                          "cuentaAportes": 3200.20,
                          "created": null,
                          "updated": null
                        } ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findByCategoriaimputacionId_returnsOkWithCategoriaImputacion() throws Exception {
        when(service.findByCategoriaimputacionId(1L)).thenReturn(sampleCategoriaImputacion());

        mockMvc.perform(get("/api/haberes/core/categoriaimputacion/{categoriaimputacionId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(categoriaImputacionJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_returnsOkWithCategoriaImputacion() throws Exception {
        when(service.findByUnique(2, 3, 4, 5)).thenReturn(sampleCategoriaImputacion());

        mockMvc.perform(get("/api/haberes/core/categoriaimputacion/unique/{dependenciaId}/{facultadId}/{geograficaId}/{categoriaId}",
                        2, 3, 4, 5))
                .andExpect(status().isOk())
                .andExpect(content().json(categoriaImputacionJson(), JsonCompareMode.STRICT));
    }

    @Test
    void add_returnsOkWithCategoriaImputacion() throws Exception {
        when(service.add(any(CategoriaImputacion.class))).thenReturn(sampleCategoriaImputacion());

        mockMvc.perform(post("/api/haberes/core/categoriaimputacion/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCategoriaImputacion())))
                .andExpect(status().isOk())
                .andExpect(content().json(categoriaImputacionJson(), JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithCategoriaImputacion() throws Exception {
        when(service.update(any(CategoriaImputacion.class), anyLong())).thenReturn(sampleCategoriaImputacion());

        mockMvc.perform(put("/api/haberes/core/categoriaimputacion/{categoriaimputacionId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCategoriaImputacion())))
                .andExpect(status().isOk())
                .andExpect(content().json(categoriaImputacionJson(), JsonCompareMode.STRICT));
    }
}
