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
import um.haberes.core.hexagonal.facultad.domain.model.Facultad;
import um.haberes.core.hexagonal.facultad.infrastructure.web.mapper.FacultadDtoMapper;
import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import um.haberes.core.hexagonal.geografica.infrastructure.web.mapper.GeograficaDtoMapper;
import um.haberes.core.hexagonal.personas.dependencia.application.service.DependenciaService;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.web.controller.DependenciaController;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.web.dto.DependenciaResponse;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.web.mapper.DependenciaDtoMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        controller = new DependenciaController(service,
                new DependenciaDtoMapper(new FacultadDtoMapper(), new GeograficaDtoMapper()));
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
                  "sedeKey": "2.3"
                }
                """;
    }

    @Test
    void toResponse_includesNestedFacultadAndGeograficaForLegacyClients() {
        Dependencia dependencia = sampleDependencia();
        dependencia.setFacultad(Facultad.builder().facultadId(2).nombre("Rectorado").reducido("REC").build());
        dependencia.setGeografica(Geografica.builder().geograficaId(3).nombre("San Rafael").reducido("SR").build());

        DependenciaDtoMapper mapper = new DependenciaDtoMapper(new FacultadDtoMapper(), new GeograficaDtoMapper());
        DependenciaResponse response = mapper.toResponse(dependencia);

        assertNotNull(response.getFacultad());
        assertEquals("Rectorado", response.getFacultad().getNombre());
        assertNotNull(response.getGeografica());
        assertEquals("San Rafael", response.getGeografica().getNombre());
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
