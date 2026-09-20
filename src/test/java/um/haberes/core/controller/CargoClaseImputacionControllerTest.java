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
import um.haberes.core.kotlin.model.CargoClaseImputacion;
import um.haberes.core.service.CargoClaseImputacionService;

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

    private static final String IMPUTACION_JSON = """
            {
              "cargoClaseImputacionId": 1,
              "dependenciaId": 2,
              "facultadId": 3,
              "geograficaId": 4,
              "cargoClaseId": 5,
              "cuentaSueldos": 150.00,
              "cuentaAportes": 50.00,
              "created": null,
              "updated": null
            }
            """;

    @Test
    void findAll_returnsOkWithListOfImputacion() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleImputacion()));

        mockMvc.perform(get("/api/haberes/core/cargoclaseimputacion/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + IMPUTACION_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByCargoclaseimputacionId_returnsOkWithImputacionBody() throws Exception {
        when(service.findByCargoclaseimputacionId(1L)).thenReturn(sampleImputacion());

        mockMvc.perform(get("/api/haberes/core/cargoclaseimputacion/{cargoclaseimputacionId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(IMPUTACION_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_returnsOkWithImputacionBody() throws Exception {
        when(service.findByUnique(2, 3, 4, 5L)).thenReturn(sampleImputacion());

        mockMvc.perform(get("/api/haberes/core/cargoclaseimputacion/unique/{dependenciaId}/{facultadId}/{geograficaId}/{cargoclaseId}",
                        2, 3, 4, 5))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(IMPUTACION_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void add_returnsOkWithImputacionBody() throws Exception {
        when(service.add(any(CargoClaseImputacion.class))).thenReturn(sampleImputacion());

        mockMvc.perform(post("/api/haberes/core/cargoclaseimputacion/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new CargoClaseImputacion())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(IMPUTACION_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithImputacionBody() throws Exception {
        when(service.update(any(CargoClaseImputacion.class), anyLong())).thenReturn(sampleImputacion());

        mockMvc.perform(put("/api/haberes/core/cargoclaseimputacion/{cargoclaseimputacionId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new CargoClaseImputacion())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(IMPUTACION_JSON, JsonCompareMode.STRICT));
    }
}
