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
import um.haberes.core.hexagonal.liquidaciones.cargo.application.service.CargoService;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;
import um.haberes.core.hexagonal.liquidaciones.cargo.infrastructure.web.controller.CargoController;
import um.haberes.core.hexagonal.liquidaciones.cargo.infrastructure.web.mapper.CargoDtoMapper;
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
        controller = new CargoController(service, new CargoDtoMapper());
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
    void delete_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/cargo/{cargoId}", 1))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
}
