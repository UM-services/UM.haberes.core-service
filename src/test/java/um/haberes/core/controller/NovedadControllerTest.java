package um.haberes.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import um.haberes.core.hexagonal.facultad.infrastructure.web.mapper.FacultadDtoMapper;
import um.haberes.core.hexagonal.geografica.infrastructure.web.mapper.GeograficaDtoMapper;
import um.haberes.core.hexagonal.liquidaciones.afip_concepto_sueldo.infrastructure.web.mapper.AfipConceptoSueldoDtoMapper;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.web.mapper.CodigoDtoMapper;
import um.haberes.core.hexagonal.liquidaciones.novedad.application.exception.NovedadException;
import um.haberes.core.hexagonal.liquidaciones.novedad.application.service.NovedadService;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.liquidaciones.novedad.infrastructure.web.mapper.NovedadDtoMapper;
import um.haberes.core.hexagonal.liquidaciones.novedad.infrastructure.web.controller.NovedadController;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.web.mapper.DependenciaDtoMapper;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.mapper.PersonaDtoMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NovedadControllerTest {

    @Mock
    private NovedadService service;

    private NovedadController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        DependenciaDtoMapper dependenciaDtoMapper = new DependenciaDtoMapper(new FacultadDtoMapper(),
                new GeograficaDtoMapper());
        PersonaDtoMapper personaDtoMapper = new PersonaDtoMapper(dependenciaDtoMapper);
        CodigoDtoMapper codigoDtoMapper = new CodigoDtoMapper(new AfipConceptoSueldoDtoMapper());
        controller = new NovedadController(service,
                new NovedadDtoMapper(personaDtoMapper, codigoDtoMapper, dependenciaDtoMapper));
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private Novedad sampleNovedad() {
        Novedad novedad = new Novedad();
        novedad.setNovedadId(1L);
        novedad.setLegajoId(100L);
        novedad.setAnho(2024);
        novedad.setMes(6);
        novedad.setCodigoId(10);
        novedad.setDependenciaId(20);
        novedad.setImporte(new BigDecimal("150.25"));
        novedad.setValue("A");
        novedad.setObservaciones("obs");
        novedad.setImportado((byte) 1);
        novedad.setNovedadUploadId(7L);
        return novedad;
    }

    private String novedadBodyJson() throws Exception {
        return new ObjectMapper().writeValueAsString(sampleNovedad());
    }

    private Novedad sampleNovedadWithRelations() {
        Novedad novedad = sampleNovedad();
        Dependencia dependencia = Dependencia.builder()
                .dependenciaId(20)
                .nombre("Facultad de Arquitectura, Urbanismo y Diseno - Sede Rio Cuarto")
                .acronimo("FAUD-RC")
                .facultadId(2)
                .geograficaId(5)
                .build();
        Codigo codigo = Codigo.builder()
                .codigoId(10)
                .nombre("Sueldo Basico")
                .docente((byte) 1)
                .noDocente((byte) 1)
                .build();
        Persona persona = Persona.builder()
                .legajoId(100L)
                .documento(new BigDecimal("28173651"))
                .apellido("GIGENA CURCHOD")
                .nombre("MARTIN ALEJANDRO")
                .dependencia(dependencia)
                .build();
        novedad.setPersona(persona);
        novedad.setCodigo(codigo);
        novedad.setDependencia(dependencia);
        return novedad;
    }

    private static final String NOVEDAD_JSON = """
            {
              "novedadId": 1,
              "legajoId": 100,
              "anho": 2024,
              "mes": 6,
              "codigoId": 10,
              "dependenciaId": 20,
              "importe": 150.25,
              "value": "A",
              "observaciones": "obs",
              "importado": 1,
              "novedadUploadId": 7,
              "persona": null,
              "codigo": null,
              "dependencia": null
                                                                    }
            """;

    @Test
    void findAllByLegajo_returnsOkWithListOfNovedad() throws Exception {
        when(service.findAllByLegajo(100L, 2024, 6)).thenReturn(List.of(sampleNovedad()));

        mockMvc.perform(get("/api/haberes/core/novedad/legajo/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + NOVEDAD_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByCodigo_returnsOkWithListOfNovedad() throws Exception {
        when(service.findAllByCodigo(10, 2024, 6)).thenReturn(List.of(sampleNovedad()));

        mockMvc.perform(get("/api/haberes/core/novedad/codigo/{codigoId}/{anho}/{mes}", 10, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + NOVEDAD_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByImportado_returnsOkWithListOfNovedad() throws Exception {
        when(service.findAllByImportado((byte) 1, 2024, 6)).thenReturn(List.of(sampleNovedad()));

        mockMvc.perform(get("/api/haberes/core/novedad/importado/{importado}/{anho}/{mes}", 1, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + NOVEDAD_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByNovedadId_returnsOkWithNovedadBody() throws Exception {
        when(service.findByNovedadId(1L)).thenReturn(sampleNovedad());

        mockMvc.perform(get("/api/haberes/core/novedad/{novedadId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(NOVEDAD_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void findByNovedadId_includesNestedRelationsForLegacyClients() throws Exception {
        when(service.findByNovedadId(1L)).thenReturn(sampleNovedadWithRelations());

        mockMvc.perform(get("/api/haberes/core/novedad/{novedadId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo.codigoId").value(10))
                .andExpect(jsonPath("$.codigo.nombre").value("Sueldo Basico"))
                .andExpect(jsonPath("$.persona.legajoId").value(100))
                .andExpect(jsonPath("$.persona.apellido").value("GIGENA CURCHOD"))
                .andExpect(jsonPath("$.persona.nombre").value("MARTIN ALEJANDRO"))
                .andExpect(jsonPath("$.dependencia.dependenciaId").value(20))
                .andExpect(jsonPath("$.dependencia.acronimo").value("FAUD-RC"));
    }

    @Test
    void findByNovedadId_whenServiceThrowsNovedadException_returnsBadRequest() throws Exception {
        when(service.findByNovedadId(99L)).thenThrow(new NovedadException(99L));

        mockMvc.perform(get("/api/haberes/core/novedad/{novedadId}", 99))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByUnique_returnsOkWithNovedadBody() throws Exception {
        when(service.findByUnique(100L, 2024, 6, 10, 20)).thenReturn(sampleNovedad());

        mockMvc.perform(get("/api/haberes/core/novedad/unique/{legajoId}/{anho}/{mes}/{codigoId}/{dependenciaId}",
                100, 2024, 6, 10, 20))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(NOVEDAD_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_whenDependenciaIdIsNullString_passesNullDependencia() throws Exception {
        when(service.findByUnique(100L, 2024, 6, 10, null)).thenReturn(sampleNovedad());

        mockMvc.perform(get("/api/haberes/core/novedad/unique/{legajoId}/{anho}/{mes}/{codigoId}/{dependenciaId}",
                100, 2024, 6, 10, "null"))
                .andExpect(status().isOk())
                .andExpect(content().json(NOVEDAD_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_whenServiceThrowsNovedadException_returnsBadRequest() throws Exception {
        when(service.findByUnique(100L, 2024, 6, 10, 20))
                .thenThrow(new NovedadException(100L, 2024, 6, 10, 20));

        mockMvc.perform(get("/api/haberes/core/novedad/unique/{legajoId}/{anho}/{mes}/{codigoId}/{dependenciaId}",
                100, 2024, 6, 10, 20))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_returnsCreatedWithNovedadBody() throws Exception {
        when(service.add(any(Novedad.class))).thenReturn(sampleNovedad());

        mockMvc.perform(post("/api/haberes/core/novedad/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(novedadBodyJson()))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(NOVEDAD_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithNovedadBody() throws Exception {
        when(service.update(any(Novedad.class), any())).thenReturn(sampleNovedad());

        mockMvc.perform(put("/api/haberes/core/novedad/{novedadId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(novedadBodyJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(NOVEDAD_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void deleteByNovedadId_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/novedad/{novedadId}", 1))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    void deleteAllByPeriodo_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/novedad/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
}
