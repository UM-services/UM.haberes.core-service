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
import um.haberes.core.kotlin.model.Codigo;
import um.haberes.core.kotlin.model.view.CodigoSearch;
import um.haberes.core.service.CodigoService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CodigoControllerTest {

    @Mock
    private CodigoService service;

    @InjectMocks
    private CodigoController controller;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private Codigo sampleCodigo() {
        Codigo codigo = new Codigo();
        codigo.setCodigoId(10);
        codigo.setNombre("SUELDO");
        codigo.setDocente((byte) 1);
        codigo.setNoDocente((byte) 1);
        codigo.setTransferible((byte) 0);
        codigo.setIncluidoEtec((byte) 1);
        codigo.setAfipConceptoSueldoIdPrimerSemestre(11L);
        codigo.setAfipConceptoSueldoIdSegundoSemestre(12L);
        return codigo;
    }

    private String codigoJson() {
        return """
                {
                  "codigoId": 10,
                  "nombre": "SUELDO",
                  "docente": 1,
                  "noDocente": 1,
                  "transferible": 0,
                  "incluidoEtec": 1,
                  "afipConceptoSueldoIdPrimerSemestre": 11,
                  "afipConceptoSueldoIdSegundoSemestre": 12,
                  "afipConceptoSueldoPrimerSemestre": null,
                  "afipConceptoSueldoSegundoSemestre": null,
                  "created": null,
                  "updated": null
                }
                """;
    }

    private String codigoArrayJson() {
        return """
                [ {
                  "codigoId": 10,
                  "nombre": "SUELDO",
                  "docente": 1,
                  "noDocente": 1,
                  "transferible": 0,
                  "incluidoEtec": 1,
                  "afipConceptoSueldoIdPrimerSemestre": 11,
                  "afipConceptoSueldoIdSegundoSemestre": 12,
                  "afipConceptoSueldoPrimerSemestre": null,
                  "afipConceptoSueldoSegundoSemestre": null,
                  "created": null,
                  "updated": null
                } ]
                """;
    }

    @Test
    void findAll_returnsOkWithListOfCodigo() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleCodigo()));

        mockMvc.perform(get("/api/haberes/core/codigo/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(codigoArrayJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByPeriodo_returnsOkWithListOfCodigo() throws Exception {
        when(service.findAllByPeriodo(2024, 6)).thenReturn(List.of(sampleCodigo()));

        mockMvc.perform(get("/api/haberes/core/codigo/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(codigoArrayJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllSearch_returnsOkWithListOfCodigoSearch() throws Exception {
        CodigoSearch codigoSearch = new CodigoSearch();
        codigoSearch.setCodigoId(10);
        codigoSearch.setNombre("SUELDO");
        codigoSearch.setDocente((byte) 1);
        codigoSearch.setNoDocente((byte) 0);
        codigoSearch.setSearch("sueldo");
        when(service.findAllSearch("sue")).thenReturn(List.of(codigoSearch));

        mockMvc.perform(get("/api/haberes/core/codigo/search/{chain}", "sue"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [ {
                          "codigoId": 10,
                          "nombre": "SUELDO",
                          "docente": 1,
                          "noDocente": 0,
                          "search": "sueldo",
                          "created": null,
                          "updated": null
                        } ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findByCodigoId_returnsOkWithCodigo() throws Exception {
        when(service.findByCodigoId(10)).thenReturn(sampleCodigo());

        mockMvc.perform(get("/api/haberes/core/codigo/{codigoId}", 10))
                .andExpect(status().isOk())
                .andExpect(content().json(codigoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findLast_returnsOkWithCodigo() throws Exception {
        when(service.findLast()).thenReturn(sampleCodigo());

        mockMvc.perform(get("/api/haberes/core/codigo/last"))
                .andExpect(status().isOk())
                .andExpect(content().json(codigoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void delete_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/codigo/{codigoId}", 10))
                .andExpect(status().isNoContent());
    }

    @Test
    void add_returnsOkWithCodigo() throws Exception {
        when(service.add(any(Codigo.class))).thenReturn(sampleCodigo());

        mockMvc.perform(post("/api/haberes/core/codigo/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCodigo())))
                .andExpect(status().isOk())
                .andExpect(content().json(codigoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithCodigo() throws Exception {
        when(service.update(any(Codigo.class), anyInt())).thenReturn(sampleCodigo());

        mockMvc.perform(put("/api/haberes/core/codigo/{codigoId}", 10)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCodigo())))
                .andExpect(status().isOk())
                .andExpect(content().json(codigoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void saveAll_returnsOkWithListOfCodigo() throws Exception {
        when(service.saveAll(anyList())).thenReturn(List.of(sampleCodigo()));

        mockMvc.perform(put("/api/haberes/core/codigo/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(sampleCodigo()))))
                .andExpect(status().isOk())
                .andExpect(content().json(codigoArrayJson(), JsonCompareMode.STRICT));
    }
}
