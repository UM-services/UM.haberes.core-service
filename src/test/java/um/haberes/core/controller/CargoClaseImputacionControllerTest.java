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
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.application.service.CargoClaseImputacionService;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.model.CargoClaseImputacion;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.infrastructure.web.controller.CargoClaseImputacionController;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.infrastructure.web.dto.CargoClaseImputacionResponse;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.infrastructure.web.mapper.CargoClaseImputacionDtoMapper;

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
class CargoClaseImputacionControllerTest {

    @Mock
    private CargoClaseImputacionService service;

    @Mock
    private CargoClaseImputacionDtoMapper mapper;

    @InjectMocks
    private CargoClaseImputacionController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private CargoClaseImputacion sampleImputacion() {
        CargoClaseImputacion imputacion = new CargoClaseImputacion();
        imputacion.setCargoClaseImputacionId(1L);
        imputacion.setDependenciaId(2);
        imputacion.setFacultadId(3);
        imputacion.setGeograficaId(4);
        imputacion.setCargoClaseId(5L);
        imputacion.setCuentaSueldos(new BigDecimal("150.00"));
        imputacion.setCuentaAportes(new BigDecimal("50.00"));
        return imputacion;
    }

    private CargoClaseImputacionResponse sampleResponse() {
        return CargoClaseImputacionResponse.builder()
                .cargoClaseImputacionId(1L)
                .dependenciaId(2)
                .facultadId(3)
                .geograficaId(4)
                .cargoClaseId(5L)
                .cuentaSueldos(new BigDecimal("150.00"))
                .cuentaAportes(new BigDecimal("50.00"))
                .build();
    }

    private static final String IMPUTACION_JSON = """
            {
              "cargoClaseImputacionId": 1,
              "dependenciaId": 2,
              "facultadId": 3,
              "geograficaId": 4,
              "cargoClaseId": 5,
              "cuentaSueldos": 150.00,
              "cuentaAportes": 50.00
            }
            """;

    @Test
    void findAll_returnsOkWithListOfImputacion() throws Exception {
        when(service.getAllCargoClaseImputaciones()).thenReturn(List.of(sampleImputacion()));
        when(mapper.toResponse(any(CargoClaseImputacion.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/cargoclaseimputacion/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + IMPUTACION_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByCargoclaseimputacionId_returnsOkWithImputacionBody() throws Exception {
        when(service.getCargoClaseImputacionById(1L)).thenReturn(sampleImputacion());
        when(mapper.toResponse(any(CargoClaseImputacion.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/cargoclaseimputacion/{cargoclaseimputacionId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(IMPUTACION_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_returnsOkWithImputacionBody() throws Exception {
        when(service.getCargoClaseImputacionByUnique(2, 3, 4, 5L)).thenReturn(sampleImputacion());
        when(mapper.toResponse(any(CargoClaseImputacion.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/cargoclaseimputacion/unique/{dependenciaId}/{facultadId}/{geograficaId}/{cargoclaseId}",
                        2, 3, 4, 5))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(IMPUTACION_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void add_returnsCreatedWithImputacionBody() throws Exception {
        when(mapper.toDomain(any())).thenReturn(sampleImputacion());
        when(service.createCargoClaseImputacion(any(CargoClaseImputacion.class))).thenReturn(sampleImputacion());
        when(mapper.toResponse(any(CargoClaseImputacion.class))).thenReturn(sampleResponse());

        mockMvc.perform(post("/api/haberes/core/cargoclaseimputacion/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleImputacion())))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(IMPUTACION_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithImputacionBody() throws Exception {
        when(mapper.toDomain(any())).thenReturn(sampleImputacion());
        when(service.updateCargoClaseImputacion(anyLong(), any(CargoClaseImputacion.class))).thenReturn(sampleImputacion());
        when(mapper.toResponse(any(CargoClaseImputacion.class))).thenReturn(sampleResponse());

        mockMvc.perform(put("/api/haberes/core/cargoclaseimputacion/{cargoclaseimputacionId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleImputacion())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(IMPUTACION_JSON, JsonCompareMode.STRICT));
    }
}
