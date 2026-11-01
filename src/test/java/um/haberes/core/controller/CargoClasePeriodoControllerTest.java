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
import um.haberes.core.model.CargoClasePeriodoEntity;
import um.haberes.core.service.CargoClasePeriodoService;

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
class CargoClasePeriodoControllerTest {

    @Mock
    private CargoClasePeriodoService service;

    @InjectMocks
    private CargoClasePeriodoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private CargoClasePeriodoEntity samplePeriodo() {
        CargoClasePeriodoEntity periodo = new CargoClasePeriodoEntity();
        periodo.setCargoClasePeriodoId(1L);
        periodo.setLegajoId(100L);
        periodo.setCargoClaseId(2L);
        periodo.setDependenciaId(3);
        periodo.setFacultadId(4);
        periodo.setGeograficaId(5);
        periodo.setPeriodoDesde(202401L);
        periodo.setPeriodoHasta(202412L);
        periodo.setHoras(20);
        periodo.setValorHora(new BigDecimal("150.50"));
        periodo.setAplicaAdicional((byte) 1);
        periodo.setDescripcion("Ayudante 1er cuatrimestre");
        return periodo;
    }

    private static final String PERIODO_JSON = """
            {
              "cargoClasePeriodoId": 1,
              "legajoId": 100,
              "cargoClaseId": 2,
              "dependenciaId": 3,
              "facultadId": 4,
              "geograficaId": 5,
              "periodoDesde": 202401,
              "periodoHasta": 202412,
              "horas": 20,
              "valorHora": 150.50,
              "aplicaAdicional": 1,
              "descripcion": "Ayudante 1er cuatrimestre",
              "persona": null,
              "cargoClase": null,
              "dependencia": null,
              "facultad": null,
              "geografica": null,
              "created": null,
              "updated": null
            }
            """;

    @Test
    void findAllByFacultad_returnsOkWithListOfPeriodo() throws Exception {
        when(service.findAllByFacultad(4)).thenReturn(List.of(samplePeriodo()));

        mockMvc.perform(get("/api/haberes/core/cargoclaseperiodo/facultad/{facultadId}", 4))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + PERIODO_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByLegajo_returnsOkWithListOfPeriodo() throws Exception {
        when(service.findAllByLegajo(100L)).thenReturn(List.of(samplePeriodo()));

        mockMvc.perform(get("/api/haberes/core/cargoclaseperiodo/legajo/{legajoId}", 100))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + PERIODO_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByCargoclaseperiodoId_returnsOkWithPeriodoBody() throws Exception {
        when(service.findByCargoClasePeriodoId(1L)).thenReturn(samplePeriodo());

        mockMvc.perform(get("/api/haberes/core/cargoclaseperiodo/{cargoClasePeriodoId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(PERIODO_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void add_returnsOkWithPeriodoBody() throws Exception {
        when(service.add(any(CargoClasePeriodoEntity.class))).thenReturn(samplePeriodo());

        mockMvc.perform(post("/api/haberes/core/cargoclaseperiodo/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new CargoClasePeriodoEntity())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(PERIODO_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithPeriodoBody() throws Exception {
        when(service.update(any(CargoClasePeriodoEntity.class), anyLong())).thenReturn(samplePeriodo());

        mockMvc.perform(put("/api/haberes/core/cargoclaseperiodo/{cargoClasePeriodoId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new CargoClasePeriodoEntity())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(PERIODO_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void delete_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/cargoclaseperiodo/{cargoClasePeriodoId}", 1))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
}
