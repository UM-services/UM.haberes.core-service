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
import um.haberes.core.exception.CursoCargoNovedadException;
import um.haberes.core.kotlin.model.CursoCargoNovedad;
import um.haberes.core.service.CursoCargoNovedadService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CursoCargoNovedadControllerTest {

    @Mock
    private CursoCargoNovedadService service;

    @InjectMocks
    private CursoCargoNovedadController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private CursoCargoNovedad sample() {
        CursoCargoNovedad novedad = new CursoCargoNovedad();
        novedad.setCursoCargoNovedadId(1L);
        novedad.setCursoId(2L);
        novedad.setAnho(2024);
        novedad.setMes(6);
        novedad.setCargoTipoId(3);
        novedad.setLegajoId(4L);
        novedad.setHorasSemanales(new BigDecimal("10.5"));
        novedad.setHorasTotales(new BigDecimal("105"));
        novedad.setDesarraigo((byte) 0);
        novedad.setAlta((byte) 1);
        novedad.setBaja((byte) 0);
        novedad.setCambio((byte) 0);
        novedad.setSolicitud("solicitud de alta");
        novedad.setAutorizado((byte) 0);
        novedad.setRechazado((byte) 0);
        novedad.setRespuesta("respuesta pendiente");
        novedad.setTransferido((byte) 0);
        return novedad;
    }

    private String novedadJson() {
        return """
                {
                  "cursoCargoNovedadId": 1,
                  "cursoId": 2,
                  "anho": 2024,
                  "mes": 6,
                  "cargoTipoId": 3,
                  "legajoId": 4,
                  "horasSemanales": 10.5,
                  "horasTotales": 105,
                  "desarraigo": 0,
                  "alta": 1,
                  "baja": 0,
                  "cambio": 0,
                  "solicitud": "solicitud de alta",
                  "autorizado": 0,
                  "rechazado": 0,
                  "respuesta": "respuesta pendiente",
                  "transferido": 0,
                  "curso": null,
                  "cargoTipo": null,
                  "persona": null,
                  "created": null,
                  "updated": null
                }
                """;
    }

    private String novedadListJson() {
        return """
                [
                  {
                    "cursoCargoNovedadId": 1,
                    "cursoId": 2,
                    "anho": 2024,
                    "mes": 6,
                    "cargoTipoId": 3,
                    "legajoId": 4,
                    "horasSemanales": 10.5,
                    "horasTotales": 105,
                    "desarraigo": 0,
                    "alta": 1,
                    "baja": 0,
                    "cambio": 0,
                    "solicitud": "solicitud de alta",
                    "autorizado": 0,
                    "rechazado": 0,
                    "respuesta": "respuesta pendiente",
                    "transferido": 0,
                    "curso": null,
                    "cargoTipo": null,
                    "persona": null,
                    "created": null,
                    "updated": null
                  }
                ]
                """;
    }

    @Test
    void findAllPendientes_returnsOkWithList() throws Exception {
        when(service.findAllPendientes(2024, 6)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/pendiente/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(novedadListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllPendientesAlta_returnsOkWithList() throws Exception {
        when(service.findAllPendientesAlta(2024, 6)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/pendientealta/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(novedadListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllCursoPendientesAlta_returnsOkWithList() throws Exception {
        when(service.findAllCursoPendientesAlta(2L, 2024, 6)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/cursopendientealta/{cursoId}/{anho}/{mes}",
                        2L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(novedadListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllAutorizadosAlta_returnsOkWithList() throws Exception {
        when(service.findAllAutorizadosAlta(2024, 6)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/autorizadoalta/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(novedadListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllRechazadosAlta_returnsOkWithList() throws Exception {
        when(service.findAllRechazadosAlta(2024, 6)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/rechazadoalta/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(novedadListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllPendientesBaja_returnsOkWithList() throws Exception {
        when(service.findAllPendientesBaja(2024, 6)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/pendientebaja/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(novedadListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllCursoPendientesBaja_returnsOkWithList() throws Exception {
        when(service.findAllCursoPendientesBaja(2L, 2024, 6)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/cursopendientebaja/{cursoId}/{anho}/{mes}",
                        2L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(novedadListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllAutorizadosBaja_returnsOkWithList() throws Exception {
        when(service.findAllAutorizadosBaja(2024, 6)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/autorizadobaja/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(novedadListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllRechazadosBaja_returnsOkWithList() throws Exception {
        when(service.findAllRechazadosBaja(2024, 6)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/rechazadobaja/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(novedadListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllAutorizadosLegajo_returnsOkWithList() throws Exception {
        when(service.findAllAutorizadosLegajo(4L, 2L, 2024, 6)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/autorizadolegajo/{legajoId}/{cursoId}/{anho}/{mes}",
                        4L, 2L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(novedadListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllRechazadosLegajo_returnsOkWithList() throws Exception {
        when(service.findAllRechazadosLegajo(4L, 2L, 2024, 6)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/rechazadolegajo/{legajoId}/{cursoId}/{anho}/{mes}",
                        4L, 2L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(novedadListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllPendientesLegajo_returnsOkWithList() throws Exception {
        when(service.findAllPendientesLegajo(4L, 2L, 2024, 6)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/pendientelegajo/{legajoId}/{cursoId}/{anho}/{mes}",
                        4L, 2L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(novedadListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByFacultad_returnsOkWithList() throws Exception {
        when(service.findAllByFacultad(3, 2024, 6)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/facultad/{facultadId}/{anho}/{mes}", 3, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(novedadListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByFacultadAndGeograficaAndAlta_returnsOkWithList() throws Exception {
        when(service.findAllByFacultadAndGeograficaAndAlta(3, 2, 2024, 6)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/facultad/{facultadId}/geografica/{geograficaId}/"
                        + "periodo/{anho}/{mes}/alta", 3, 2, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(novedadListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByFacultadAndGeograficaAndCambio_returnsOkWithList() throws Exception {
        when(service.findAllByFacultadAndGeograficaAndCambio(3, 2, 2024, 6)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/facultad/{facultadId}/geografica/{geograficaId}/"
                        + "periodo/{anho}/{mes}/cambio", 3, 2, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(novedadListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByFacultadAndGeograficaAndBaja_returnsOkWithList() throws Exception {
        when(service.findAllByFacultadAndGeograficaAndBaja(3, 2, 2024, 6)).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/facultad/{facultadId}/geografica/{geograficaId}/"
                        + "periodo/{anho}/{mes}/baja", 3, 2, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(novedadListJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByCursoCargoNovedadId_returnsOkWithBody() throws Exception {
        when(service.findByCursoCargoNovedadId(1L)).thenReturn(sample());

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/{cursoCargoNovedadId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(novedadJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByCursoCargoNovedadId_whenServiceThrowsException_returnsBadRequest() throws Exception {
        when(service.findByCursoCargoNovedadId(99L)).thenThrow(new CursoCargoNovedadException(99L));

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/{cursoCargoNovedadId}", 99L))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByLegajo_returnsOkWithBody() throws Exception {
        when(service.findByLegajo(4L, 2L, 2024, 6)).thenReturn(sample());

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/legajo/{legajoId}/{cursoId}/{anho}/{mes}",
                        4L, 2L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().json(novedadJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByLegajo_whenServiceThrowsException_returnsBadRequest() throws Exception {
        when(service.findByLegajo(4L, 99L, 2024, 6)).thenThrow(new CursoCargoNovedadException(4L, 99L, 2024, 6));

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/legajo/{legajoId}/{cursoId}/{anho}/{mes}",
                        4L, 99L, 2024, 6))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByUnique_returnsOkWithBody() throws Exception {
        when(service.findByUnique(2L, 2024, 6, 3, 4L)).thenReturn(sample());

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/unique/{cursoId}/{anho}/{mes}/{cargoTipoId}/{legajoId}",
                        2L, 2024, 6, 3, 4L))
                .andExpect(status().isOk())
                .andExpect(content().json(novedadJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_whenServiceThrowsException_returnsBadRequest() throws Exception {
        when(service.findByUnique(99L, 2024, 6, 3, 4L))
                .thenThrow(new CursoCargoNovedadException(99L, 2024, 6, 3, 4L));

        mockMvc.perform(get("/api/haberes/core/cursocargonovedad/unique/{cursoId}/{anho}/{mes}/{cargoTipoId}/{legajoId}",
                        99L, 2024, 6, 3, 4L))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_returnsOkWithBody() throws Exception {
        when(service.add(any(CursoCargoNovedad.class))).thenReturn(sample());

        mockMvc.perform(post("/api/haberes/core/cursocargonovedad/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sample())))
                .andExpect(status().isOk())
                .andExpect(content().json(novedadJson(), JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithBody() throws Exception {
        when(service.update(any(CursoCargoNovedad.class), eq(1L))).thenReturn(sample());

        mockMvc.perform(put("/api/haberes/core/cursocargonovedad/{cursoCargoNovedadId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sample())))
                .andExpect(status().isOk())
                .andExpect(content().json(novedadJson(), JsonCompareMode.STRICT));
    }

    @Test
    void deleteAllByLegajoPendiente_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/cursocargonovedad/legajoPendiente/{legajoId}/{cursoId}/{anho}/{mes}",
                        4L, 2L, 2024, 6))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/cursocargonovedad/{cursoCargoNovedadId}", 1L))
                .andExpect(status().isNoContent());
    }
}
