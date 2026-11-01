package um.haberes.core.hexagonal.facultad.infrastructure.web.controller;

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
import um.haberes.core.hexagonal.facultad.application.exception.FacultadException;
import um.haberes.core.hexagonal.facultad.application.service.FacultadService;
import um.haberes.core.hexagonal.facultad.domain.model.Facultad;
import um.haberes.core.hexagonal.facultad.infrastructure.web.dto.FacultadResponse;
import um.haberes.core.hexagonal.facultad.infrastructure.web.mapper.FacultadDtoMapper;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FacultadControllerTest {

    @Mock
    private FacultadService facultadService;

    @Mock
    private FacultadDtoMapper facultadDtoMapper;

    @InjectMocks
    private FacultadController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private Facultad sampleFacultad() {
        return Facultad.builder()
                .facultadId(1)
                .nombre("Facultad de Ingeniería")
                .reducido("FING")
                .server("srv")
                .backendServer("be")
                .backendPort(8080)
                .dbName("db")
                .dsn("dsn")
                .build();
    }

    private FacultadResponse sampleResponse() {
        return FacultadResponse.builder()
                .facultadId(1)
                .nombre("Facultad de Ingeniería")
                .reducido("FING")
                .server("srv")
                .backendServer("be")
                .backendPort(8080)
                .dbName("db")
                .dsn("dsn")
                .build();
    }

    @Test
    void getFacultadById_returnsOkWithMappedResponse() throws Exception {
        Facultad facultad = sampleFacultad();
        when(facultadService.getFacultadById(1)).thenReturn(facultad);
        when(facultadDtoMapper.toResponse(facultad)).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/facultad/{facultadId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "facultadId": 1,
                          "nombre": "Facultad de Ingeniería",
                          "reducido": "FING",
                          "server": "srv",
                          "backendServer": "be",
                          "backendPort": 8080,
                          "dbName": "db",
                          "dsn": "dsn"
                        }
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void getFacultadById_isAlsoExposedUnderShortAliasPath() throws Exception {
        Facultad facultad = sampleFacultad();
        when(facultadService.getFacultadById(1)).thenReturn(facultad);
        when(facultadDtoMapper.toResponse(facultad)).thenReturn(sampleResponse());

        mockMvc.perform(get("/facultad/{facultadId}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void getFacultadById_whenNotFound_returnsNotFound() throws Exception {
        when(facultadService.getFacultadById(99)).thenThrow(new FacultadException(99));

        mockMvc.perform(get("/api/haberes/core/facultad/{facultadId}", 99))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllFacultades_returnsOkWithListOfMappedResponses() throws Exception {
        Facultad facultad = sampleFacultad();
        when(facultadService.getAllFacultades()).thenReturn(List.of(facultad));
        when(facultadDtoMapper.toResponse(facultad)).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/facultad/"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                          {
                            "facultadId": 1,
                            "nombre": "Facultad de Ingeniería",
                            "reducido": "FING",
                            "server": "srv",
                            "backendServer": "be",
                            "backendPort": 8080,
                            "dbName": "db",
                            "dsn": "dsn"
                          }
                        ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void getFacultadesEstaticas_returnsOkWithFilteredList() throws Exception {
        Facultad facultad = sampleFacultad();
        when(facultadService.getFacultadesEstaticas()).thenReturn(List.of(facultad));
        when(facultadDtoMapper.toResponse(facultad)).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/facultad/facultades"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                          {
                            "facultadId": 1,
                            "nombre": "Facultad de Ingeniería",
                            "reducido": "FING",
                            "server": "srv",
                            "backendServer": "be",
                            "backendPort": 8080,
                            "dbName": "db",
                            "dsn": "dsn"
                          }
                        ]
                        """, JsonCompareMode.STRICT));
    }
}
