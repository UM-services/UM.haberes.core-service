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
import um.haberes.core.kotlin.model.Dependencia;
import um.haberes.core.service.DependenciaService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DependenciaControllerTest {

    @Mock
    private DependenciaService service;

    @InjectMocks
    private DependenciaController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private Dependencia sampleDependencia() {
        Dependencia dependencia = new Dependencia();
        dependencia.setDependenciaId(1);
        dependencia.setNombre("Secretaría Académica");
        dependencia.setAcronimo("SEA");
        dependencia.setFacultadId(2);
        dependencia.setGeograficaId(3);
        return dependencia;
    }

    private String dependenciaJson() {
        return """
                {
                  "dependenciaId": 1,
                  "nombre": "Secretaría Académica",
                  "acronimo": "SEA",
                  "facultadId": 2,
                  "geograficaId": 3,
                  "facultad": null,
                  "geografica": null,
                  "created": null,
                  "updated": null,
                  "sedeKey": "2.3"
                }
                """;
    }

    @Test
    void findAll_returnsOkWithListOfDependencias() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleDependencia()));

        mockMvc.perform(get("/api/haberes/core/dependencia/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          %s
                        ]
                        """.formatted(dependenciaJson()), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByFacultadIdAndGeograficaId_returnsOkWithListOfDependencias() throws Exception {
        when(service.findAllByFacultadIdAndGeograficaId(2, 3)).thenReturn(List.of(sampleDependencia()));

        mockMvc.perform(get("/api/haberes/core/dependencia/context/{facultadId}/{geograficaId}", 2, 3))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          %s
                        ]
                        """.formatted(dependenciaJson()), JsonCompareMode.STRICT));
    }

    @Test
    void findByDependenciaId_returnsOkWithDependencia() throws Exception {
        when(service.findByDependenciaId(1)).thenReturn(sampleDependencia());

        mockMvc.perform(get("/api/haberes/core/dependencia/{dependenciaId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(dependenciaJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findFirstByFacultadIdAndGeograficaId_returnsOkWithDependencia() throws Exception {
        when(service.findFirstByFacultadIdAndGeograficaId(2, 3)).thenReturn(sampleDependencia());

        mockMvc.perform(get("/api/haberes/core/dependencia/facultad/{facultadId}/{geograficaId}", 2, 3))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(dependenciaJson(), JsonCompareMode.STRICT));
    }
}
