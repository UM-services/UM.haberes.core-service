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
import um.haberes.core.model.CargoClaseDetalleEntity;
import um.haberes.core.service.CargoClaseDetalleService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CargoClaseDetalleControllerTest {

    @Mock
    private CargoClaseDetalleService service;

    @InjectMocks
    private CargoClaseDetalleController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private CargoClaseDetalleEntity sampleDetalle() {
        CargoClaseDetalleEntity detalle = new CargoClaseDetalleEntity();
        detalle.setCargoClaseDetalleId(1L);
        detalle.setLegajoId(100L);
        detalle.setAnho(2024);
        detalle.setMes(6);
        detalle.setCargoClaseId(2L);
        detalle.setDependenciaId(3);
        detalle.setFacultadId(4);
        detalle.setGeograficaId(5);
        detalle.setHoras(20);
        detalle.setValorHora(new BigDecimal("150.25"));
        detalle.setAplicaAdicional((byte) 1);
        detalle.setCargoClasePeriodoId(6L);
        detalle.setLiquidado((byte) 0);
        return detalle;
    }

    private static final String DETALLE_JSON = """
            {
              "cargoClaseDetalleId": 1,
              "legajoId": 100,
              "anho": 2024,
              "mes": 6,
              "cargoClaseId": 2,
              "dependenciaId": 3,
              "facultadId": 4,
              "geograficaId": 5,
              "horas": 20,
              "valorHora": 150.25,
              "aplicaAdicional": 1,
              "cargoClasePeriodoId": 6,
              "liquidado": 0,
              "persona": null,
              "cargoClase": null,
              "dependencia": null,
              "facultad": null,
              "geografica": null,
              "cargoClasePeriodo": null,
              "created": null,
              "updated": null
            }
            """;

    @Test
    void findAllByLegajo_returnsOkWithListOfDetalle() throws Exception {
        when(service.findAllByLegajo(100L, 2024, 6)).thenReturn(List.of(sampleDetalle()));

        mockMvc.perform(get("/api/haberes/core/cargoclasedetalle/legajo/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + DETALLE_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByLegajoAndFacultad_returnsOkWithListOfDetalle() throws Exception {
        when(service.findAllByLegajoAndFacultad(100L, 2024, 6, 4)).thenReturn(List.of(sampleDetalle()));

        mockMvc.perform(get("/api/haberes/core/cargoclasedetalle/legajo/{legajoId}/{anho}/{mes}/facultad/{facultadId}",
                        100, 2024, 6, 4))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + DETALLE_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByFacultad_returnsOkWithListOfDetalle() throws Exception {
        when(service.findAllByFacultad(4, 2024, 6)).thenReturn(List.of(sampleDetalle()));

        mockMvc.perform(get("/api/haberes/core/cargoclasedetalle/facultad/{facultadId}/{anho}/{mes}", 4, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + DETALLE_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByCargoClasePeriodo_returnsOkWithListOfDetalle() throws Exception {
        when(service.findAllByCargoClasePeriodo(6L)).thenReturn(List.of(sampleDetalle()));

        mockMvc.perform(get("/api/haberes/core/cargoclasedetalle/cargoclaseperiodo/{cargoclaseperiodoId}", 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + DETALLE_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByCargoclase_returnsOkWithListOfDetalle() throws Exception {
        when(service.findAllByCargoClase(2L, 2024, 6)).thenReturn(List.of(sampleDetalle()));

        mockMvc.perform(get("/api/haberes/core/cargoclasedetalle/cargoclase/{cargoclaseId}/{anho}/{mes}", 2, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + DETALLE_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void add_returnsOkWithDetalleBody() throws Exception {
        when(service.add(any(CargoClaseDetalleEntity.class))).thenReturn(sampleDetalle());

        mockMvc.perform(post("/api/haberes/core/cargoclasedetalle/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new CargoClaseDetalleEntity())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(DETALLE_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithDetalleBody() throws Exception {
        when(service.update(any(CargoClaseDetalleEntity.class), anyLong())).thenReturn(sampleDetalle());

        mockMvc.perform(put("/api/haberes/core/cargoclasedetalle/{cargoclasedetalleId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new CargoClaseDetalleEntity())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(DETALLE_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void saveall_returnsOkWithListOfDetalle() throws Exception {
        when(service.saveAll(anyList())).thenReturn(List.of(sampleDetalle()));

        mockMvc.perform(put("/api/haberes/core/cargoclasedetalle/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper()
                                .writeValueAsString(List.of(new CargoClaseDetalleEntity()))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + DETALLE_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void delete_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/cargoclasedetalle/{cargoClaseDetalleId}", 1))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
}
