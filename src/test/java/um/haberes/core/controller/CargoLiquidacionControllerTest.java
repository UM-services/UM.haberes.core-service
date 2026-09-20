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
import um.haberes.core.exception.CargoLiquidacionException;
import um.haberes.core.kotlin.model.CargoLiquidacion;
import um.haberes.core.kotlin.model.view.CargoLiquidacionPeriodo;
import um.haberes.core.service.CargoLiquidacionService;
import um.haberes.core.service.view.CargoLiquidacionPeriodoService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
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
class CargoLiquidacionControllerTest {

    @Mock
    private CargoLiquidacionService service;

    @Mock
    private CargoLiquidacionPeriodoService cargoLiquidacionPeriodoService;

    @InjectMocks
    private CargoLiquidacionController controller;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private CargoLiquidacion sampleCargoLiquidacion() {
        CargoLiquidacion cargoLiquidacion = new CargoLiquidacion();
        cargoLiquidacion.setCargoLiquidacionId(1L);
        cargoLiquidacion.setLegajoId(100L);
        cargoLiquidacion.setAnho(2024);
        cargoLiquidacion.setMes(6);
        cargoLiquidacion.setDependenciaId(3);
        cargoLiquidacion.setCategoriaId(801);
        cargoLiquidacion.setCategoriaNombre("DOCENTE TITULAR");
        cargoLiquidacion.setCategoriaBasico(new BigDecimal("12.34"));
        cargoLiquidacion.setEstadoDocente(new BigDecimal("1.25"));
        cargoLiquidacion.setHorasJornada(new BigDecimal("4.00"));
        cargoLiquidacion.setJornada(1);
        cargoLiquidacion.setPresentismo(100);
        cargoLiquidacion.setSituacion("A");
        return cargoLiquidacion;
    }

    private String cargoLiquidacionJson() {
        return """
                {
                  "cargoLiquidacionId": 1,
                  "legajoId": 100,
                  "anho": 2024,
                  "mes": 6,
                  "dependenciaId": 3,
                  "fechaDesde": null,
                  "fechaHasta": null,
                  "categoriaId": 801,
                  "categoriaNombre": "DOCENTE TITULAR",
                  "categoriaBasico": 12.34,
                  "estadoDocente": 1.25,
                  "horasJornada": 4.00,
                  "jornada": 1,
                  "presentismo": 100,
                  "situacion": "A",
                  "persona": null,
                  "dependencia": null,
                  "categoria": null,
                  "created": null,
                  "updated": null
                }
                """;
    }

    private CargoLiquidacionPeriodo sampleCargoLiquidacionPeriodo() {
        CargoLiquidacionPeriodo periodo = new CargoLiquidacionPeriodo();
        periodo.setCargoLiquidacionId(1L);
        periodo.setLegajoId(100L);
        periodo.setAnho(2024);
        periodo.setMes(6);
        periodo.setDependenciaId(3);
        periodo.setCategoriaId(801);
        periodo.setCategoriaNombre("AUXILIAR");
        periodo.setCategoriaBasico(new BigDecimal("9.50"));
        periodo.setJornada(1);
        periodo.setPresentismo(100);
        periodo.setAsignacionEspecialPermanente(new BigDecimal("2.00"));
        periodo.setSituacion("A");
        periodo.setPeriodo(202406L);
        return periodo;
    }

    @Test
    void findAllByLegajo_returnsOkWithListOfCargoLiquidacion() throws Exception {
        when(service.findAllByLegajo(100L, 2024, 6)).thenReturn(List.of(sampleCargoLiquidacion()));

        mockMvc.perform(get("/api/haberes/core/cargoliquidacion/legajo/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [ {
                          "cargoLiquidacionId": 1,
                          "legajoId": 100,
                          "anho": 2024,
                          "mes": 6,
                          "dependenciaId": 3,
                          "fechaDesde": null,
                          "fechaHasta": null,
                          "categoriaId": 801,
                          "categoriaNombre": "DOCENTE TITULAR",
                          "categoriaBasico": 12.34,
                          "estadoDocente": 1.25,
                          "horasJornada": 4.00,
                          "jornada": 1,
                          "presentismo": 100,
                          "situacion": "A",
                          "persona": null,
                          "dependencia": null,
                          "categoria": null,
                          "created": null,
                          "updated": null
                        } ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findAllDocenteByLegajo_returnsOkWithListOfCargoLiquidacion() throws Exception {
        when(service.findAllDocenteByLegajo(100L, 2024, 6)).thenReturn(List.of(sampleCargoLiquidacion()));

        mockMvc.perform(get("/api/haberes/core/cargoliquidacion/legajodocente/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [ {
                          "cargoLiquidacionId": 1,
                          "legajoId": 100,
                          "anho": 2024,
                          "mes": 6,
                          "dependenciaId": 3,
                          "fechaDesde": null,
                          "fechaHasta": null,
                          "categoriaId": 801,
                          "categoriaNombre": "DOCENTE TITULAR",
                          "categoriaBasico": 12.34,
                          "estadoDocente": 1.25,
                          "horasJornada": 4.00,
                          "jornada": 1,
                          "presentismo": 100,
                          "situacion": "A",
                          "persona": null,
                          "dependencia": null,
                          "categoria": null,
                          "created": null,
                          "updated": null
                        } ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findAllDocenteByLegajoAndFacultad_returnsOkWithListOfCargoLiquidacion() throws Exception {
        when(service.findAllDocenteByLegajoAndFacultad(100L, 2024, 6, 4)).thenReturn(List.of(sampleCargoLiquidacion()));

        mockMvc.perform(get("/api/haberes/core/cargoliquidacion/legajodocente/{legajoId}/{anho}/{mes}/facultad/{facultadId}",
                        100, 2024, 6, 4))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [ {
                          "cargoLiquidacionId": 1,
                          "legajoId": 100,
                          "anho": 2024,
                          "mes": 6,
                          "dependenciaId": 3,
                          "fechaDesde": null,
                          "fechaHasta": null,
                          "categoriaId": 801,
                          "categoriaNombre": "DOCENTE TITULAR",
                          "categoriaBasico": 12.34,
                          "estadoDocente": 1.25,
                          "horasJornada": 4.00,
                          "jornada": 1,
                          "presentismo": 100,
                          "situacion": "A",
                          "persona": null,
                          "dependencia": null,
                          "categoria": null,
                          "created": null,
                          "updated": null
                        } ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findAllNoDocenteByLegajo_returnsOkWithListOfCargoLiquidacion() throws Exception {
        when(service.findAllNoDocenteByLegajo(100L, 2024, 6)).thenReturn(List.of(sampleCargoLiquidacion()));

        mockMvc.perform(get("/api/haberes/core/cargoliquidacion/legajonodocente/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [ {
                          "cargoLiquidacionId": 1,
                          "legajoId": 100,
                          "anho": 2024,
                          "mes": 6,
                          "dependenciaId": 3,
                          "fechaDesde": null,
                          "fechaHasta": null,
                          "categoriaId": 801,
                          "categoriaNombre": "DOCENTE TITULAR",
                          "categoriaBasico": 12.34,
                          "estadoDocente": 1.25,
                          "horasJornada": 4.00,
                          "jornada": 1,
                          "presentismo": 100,
                          "situacion": "A",
                          "persona": null,
                          "dependencia": null,
                          "categoria": null,
                          "created": null,
                          "updated": null
                        } ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findAllNoDocenteByLegajoAndFacultad_returnsOkWithListOfCargoLiquidacion() throws Exception {
        when(service.findAllNoDocenteByLegajoAndFacultad(100L, 2024, 6, 4)).thenReturn(List.of(sampleCargoLiquidacion()));

        mockMvc.perform(get("/api/haberes/core/cargoliquidacion/legajonodocente/{legajoId}/{anho}/{mes}/facultad/{facultadId}",
                        100, 2024, 6, 4))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [ {
                          "cargoLiquidacionId": 1,
                          "legajoId": 100,
                          "anho": 2024,
                          "mes": 6,
                          "dependenciaId": 3,
                          "fechaDesde": null,
                          "fechaHasta": null,
                          "categoriaId": 801,
                          "categoriaNombre": "DOCENTE TITULAR",
                          "categoriaBasico": 12.34,
                          "estadoDocente": 1.25,
                          "horasJornada": 4.00,
                          "jornada": 1,
                          "presentismo": 100,
                          "situacion": "A",
                          "persona": null,
                          "dependencia": null,
                          "categoria": null,
                          "created": null,
                          "updated": null
                        } ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findAllNoDocenteHistByLegajo_returnsOkWithListOfCargoLiquidacion() throws Exception {
        when(service.findAllNoDocenteHistByLegajo(100L)).thenReturn(List.of(sampleCargoLiquidacion()));

        mockMvc.perform(get("/api/haberes/core/cargoliquidacion/legajonodocentehist/{legajoId}", 100))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [ {
                          "cargoLiquidacionId": 1,
                          "legajoId": 100,
                          "anho": 2024,
                          "mes": 6,
                          "dependenciaId": 3,
                          "fechaDesde": null,
                          "fechaHasta": null,
                          "categoriaId": 801,
                          "categoriaNombre": "DOCENTE TITULAR",
                          "categoriaBasico": 12.34,
                          "estadoDocente": 1.25,
                          "horasJornada": 4.00,
                          "jornada": 1,
                          "presentismo": 100,
                          "situacion": "A",
                          "persona": null,
                          "dependencia": null,
                          "categoria": null,
                          "created": null,
                          "updated": null
                        } ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findAllAdicionalHCSByLegajo_returnsOkWithListOfCargoLiquidacion() throws Exception {
        when(service.findAllAdicionalHCSByLegajo(100L, 2024, 6)).thenReturn(List.of(sampleCargoLiquidacion()));

        mockMvc.perform(get("/api/haberes/core/cargoliquidacion/legajoadicionalhcs/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [ {
                          "cargoLiquidacionId": 1,
                          "legajoId": 100,
                          "anho": 2024,
                          "mes": 6,
                          "dependenciaId": 3,
                          "fechaDesde": null,
                          "fechaHasta": null,
                          "categoriaId": 801,
                          "categoriaNombre": "DOCENTE TITULAR",
                          "categoriaBasico": 12.34,
                          "estadoDocente": 1.25,
                          "horasJornada": 4.00,
                          "jornada": 1,
                          "presentismo": 100,
                          "situacion": "A",
                          "persona": null,
                          "dependencia": null,
                          "categoria": null,
                          "created": null,
                          "updated": null
                        } ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findAllRestoByLegajo_returnsOkWithListOfCargoLiquidacionPeriodo() throws Exception {
        when(cargoLiquidacionPeriodoService.findAllRestoByLegajo(100L, 2024, 6, 801))
                .thenReturn(List.of(sampleCargoLiquidacionPeriodo()));

        mockMvc.perform(get("/api/haberes/core/cargoliquidacion/legajoresto/{legajoId}/{anho}/{mes}/{categoriaId}",
                        100, 2024, 6, 801))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [ {
                          "cargoLiquidacionId": 1,
                          "legajoId": 100,
                          "anho": 2024,
                          "mes": 6,
                          "dependenciaId": 3,
                          "fechaDesde": null,
                          "fechaHasta": null,
                          "categoriaId": 801,
                          "categoriaNombre": "AUXILIAR",
                          "categoriaBasico": 9.50,
                          "jornada": 1,
                          "presentismo": 100,
                          "asignacionEspecialPermanente": 2.00,
                          "situacion": "A",
                          "periodo": 202406,
                          "persona": null,
                          "dependencia": null,
                          "categoria": null,
                          "created": null,
                          "updated": null
                        } ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findByCategoriaNoDocente_returnsOkWithCargoLiquidacion() throws Exception {
        when(service.findByCategoriaNoDocente(100L, 2024, 6, 801)).thenReturn(sampleCargoLiquidacion());

        mockMvc.perform(get("/api/haberes/core/cargoliquidacion/categorianodocente/{legajoId}/{anho}/{mes}/{categoriaId}",
                        100, 2024, 6, 801))
                .andExpect(status().isOk())
                .andExpect(content().json(cargoLiquidacionJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByCargoId_returnsOkWithCargoLiquidacion() throws Exception {
        when(service.findByCargoId(1L)).thenReturn(sampleCargoLiquidacion());

        mockMvc.perform(get("/api/haberes/core/cargoliquidacion/{cargoId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(cargoLiquidacionJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByCargoId_whenServiceThrowsCargoLiquidacionException_returnsBadRequest() throws Exception {
        when(service.findByCargoId(99L)).thenThrow(new CargoLiquidacionException(99L));

        mockMvc.perform(get("/api/haberes/core/cargoliquidacion/{cargoId}", 99))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_returnsOkWithCargoLiquidacion() throws Exception {
        when(service.add(any(CargoLiquidacion.class))).thenReturn(sampleCargoLiquidacion());

        mockMvc.perform(post("/api/haberes/core/cargoliquidacion/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCargoLiquidacion())))
                .andExpect(status().isOk())
                .andExpect(content().json(cargoLiquidacionJson(), JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithCargoLiquidacion() throws Exception {
        when(service.update(any(CargoLiquidacion.class), anyLong())).thenReturn(sampleCargoLiquidacion());

        mockMvc.perform(put("/api/haberes/core/cargoliquidacion/{cargoLiquidacionId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCargoLiquidacion())))
                .andExpect(status().isOk())
                .andExpect(content().json(cargoLiquidacionJson(), JsonCompareMode.STRICT));
    }

    @Test
    void saveall_returnsOkWithListOfCargoLiquidacion() throws Exception {
        when(service.saveAll(anyList(), anyInt(), anyBoolean())).thenReturn(List.of(sampleCargoLiquidacion()));

        mockMvc.perform(put("/api/haberes/core/cargoliquidacion/saveall/{version}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(sampleCargoLiquidacion()))))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [ {
                          "cargoLiquidacionId": 1,
                          "legajoId": 100,
                          "anho": 2024,
                          "mes": 6,
                          "dependenciaId": 3,
                          "fechaDesde": null,
                          "fechaHasta": null,
                          "categoriaId": 801,
                          "categoriaNombre": "DOCENTE TITULAR",
                          "categoriaBasico": 12.34,
                          "estadoDocente": 1.25,
                          "horasJornada": 4.00,
                          "jornada": 1,
                          "presentismo": 100,
                          "situacion": "A",
                          "persona": null,
                          "dependencia": null,
                          "categoria": null,
                          "created": null,
                          "updated": null
                        } ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void deleteByPeriodo_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/cargoliquidacion/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isNoContent());
    }
}
