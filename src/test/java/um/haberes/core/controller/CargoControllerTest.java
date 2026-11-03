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
import um.haberes.core.hexagonal.facultad.infrastructure.web.mapper.FacultadDtoMapper;
import um.haberes.core.hexagonal.geografica.infrastructure.web.mapper.GeograficaDtoMapper;
import um.haberes.core.hexagonal.liquidaciones.cargo.application.service.CargoService;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;
import um.haberes.core.hexagonal.liquidaciones.cargo.infrastructure.web.controller.CargoController;
import um.haberes.core.hexagonal.liquidaciones.cargo.infrastructure.web.mapper.CargoDtoMapper;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.mapper.CategoriaDtoMapper;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.web.mapper.DependenciaDtoMapper;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.mapper.PersonaDtoMapper;
import um.haberes.core.hexagonal.liquidaciones.cargo.application.exception.CargoException;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CargoControllerTest {

    @Mock
    private CargoService service;

    @InjectMocks
    private CargoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new CargoController(service, new CargoDtoMapper(
                new PersonaDtoMapper(new DependenciaDtoMapper(new FacultadDtoMapper(), new GeograficaDtoMapper())),
                new DependenciaDtoMapper(new FacultadDtoMapper(), new GeograficaDtoMapper()), new CategoriaDtoMapper()));
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private Cargo sampleCargo() {
        Cargo cargo = new Cargo();
        cargo.setCargoId(1L);
        cargo.setLegajoId(100L);
        cargo.setDependenciaId(2);
        cargo.setCategoriaId(3);
        cargo.setJornada(1);
        cargo.setPresentismo(2);
        cargo.setHorasJornada(new BigDecimal("8.50"));
        return cargo;
    }

    private static final String CARGO_JSON = """
            {
              "cargoId": 1,
              "legajoId": 100,
              "fechaAlta": null,
              "fechaBaja": null,
              "dependenciaId": 2,
              "categoriaId": 3,
              "jornada": 1,
              "presentismo": 2,
              "horasJornada": 8.50,
              "persona": null,
              "dependencia": null,
              "categoria": null,
              "dependenciaNombre": null,
              "categoriaNombre": null,
              "personaApellido": null,
              "personaNombre": null
                                        }
            """;

    @Test
    void findAllByLegajoId_returnsOkWithListOfCargo() throws Exception {
        when(service.findAllByLegajoId(100L)).thenReturn(List.of(sampleCargo()));

        mockMvc.perform(get("/api/haberes/core/cargo/legajo/{legajoId}", 100))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + CARGO_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByCargoId_returnsOkWithCargoBody() throws Exception {
        when(service.findByCargoId(1L)).thenReturn(sampleCargo());

        mockMvc.perform(get("/api/haberes/core/cargo/{cargoId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(CARGO_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void findByCargoId_whenServiceThrowsCargoException_returnsBadRequest() throws Exception {
        when(service.findByCargoId(99L)).thenThrow(new CargoException(99L));

        mockMvc.perform(get("/api/haberes/core/cargo/{cargoId}", 99))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_returnsOkWithCargoBody() throws Exception {
        when(service.add(any(Cargo.class))).thenReturn(sampleCargo());

        mockMvc.perform(post("/api/haberes/core/cargo/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "legajoId": 100,
                          "fechaAlta": "2024-01-15T10:00:00Z",
                          "categoriaId": 3,
                          "jornada": 1,
                          "presentismo": 2,
                          "horasJornada": 8.50
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(CARGO_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithCargoBody() throws Exception {
        when(service.update(any(Cargo.class), anyLong())).thenReturn(sampleCargo());

        mockMvc.perform(put("/api/haberes/core/cargo/{cargoId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "legajoId": 100,
                          "fechaAlta": "2024-01-15T10:00:00Z",
                          "categoriaId": 3,
                          "jornada": 1,
                          "presentismo": 2,
                          "horasJornada": 8.50
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(CARGO_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void findByCargoId_includesNestedCategoriaDependenciaAndPersona() throws Exception {
        Cargo cargo = sampleCargo();
        cargo.setCategoria(Categoria.builder()
                .categoriaId(3)
                .nombre("ADMINISTRATIVO CLASE 5")
                .basico(new BigDecimal("178219.00"))
                .noDocente((byte) 1)
                .liquidaPorHora((byte) 1)
                .build());
        cargo.setDependencia(Dependencia.builder()
                .dependenciaId(2)
                .nombre("Sede San Rafael (Rectorado)")
                .acronimo("SEDE-SR")
                .build());
        cargo.setPersona(Persona.builder()
                .legajoId(100L)
                .apellido("CASTILLO")
                .nombre("MARICEL CARINA")
                .build());

        when(service.findByCargoId(1L)).thenReturn(cargo);

        mockMvc.perform(get("/api/haberes/core/cargo/{cargoId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoria.categoriaId").value(3))
                .andExpect(jsonPath("$.categoria.nombre").value("ADMINISTRATIVO CLASE 5"))
                .andExpect(jsonPath("$.categoria.basico").value(178219.00))
                .andExpect(jsonPath("$.dependencia.acronimo").value("SEDE-SR"))
                .andExpect(jsonPath("$.persona.legajoId").value(100));
    }

    @Test
    void delete_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/cargo/{cargoId}", 1))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
}
