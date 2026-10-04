package um.haberes.core.controller;

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
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.application.service.LegajoContabilidadService;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.model.LegajoContabilidad;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.web.controller.LegajoContabilidadController;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.web.dto.LegajoContabilidadResponse;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.web.mapper.LegajoContabilidadDtoMapper;

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
class LegajoContabilidadControllerTest {

    @Mock
    private LegajoContabilidadService service;

    @Mock
    private LegajoContabilidadDtoMapper mapper;

    @InjectMocks
    private LegajoContabilidadController controller;

    private MockMvc mockMvc;

    private static final String LEGAJO_CONTABILIDAD_JSON = """
            {
              "legajoContabilidadId": 1,
              "legajoId": 100,
              "anho": 2024,
              "mes": 6,
              "diferencia": 1,
              "remunerativo": 100.50,
              "noRemunerativo": 20.25
            }
            """;

    private static final String LEGAJO_CONTABILIDAD_REQUEST_JSON = """
            {
              "legajoId": 100,
              "anho": 2024,
              "mes": 6,
              "diferencia": 1,
              "remunerativo": 100.50,
              "noRemunerativo": 20.25
            }
            """;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private LegajoContabilidad sampleLegajoContabilidad() {
        LegajoContabilidad legajoContabilidad = new LegajoContabilidad();
        legajoContabilidad.setLegajoContabilidadId(1L);
        legajoContabilidad.setLegajoId(100L);
        legajoContabilidad.setAnho(2024);
        legajoContabilidad.setMes(6);
        legajoContabilidad.setDiferencia((byte) 1);
        legajoContabilidad.setRemunerativo(new BigDecimal("100.50"));
        legajoContabilidad.setNoRemunerativo(new BigDecimal("20.25"));
        return legajoContabilidad;
    }

    private LegajoContabilidadResponse sampleResponse() {
        return LegajoContabilidadResponse.builder()
                .legajoContabilidadId(1L)
                .legajoId(100L)
                .anho(2024)
                .mes(6)
                .diferencia((byte) 1)
                .remunerativo(new BigDecimal("100.50"))
                .noRemunerativo(new BigDecimal("20.25"))
                .build();
    }

    @Test
    void findAllDiferenciaByPeriodo_returnsOkWithListOfDiferencias() throws Exception {
        when(service.findAllDiferenciaByPeriodo(2024, 6)).thenReturn(List.of(sampleLegajoContabilidad()));
        when(mapper.toResponse(any(LegajoContabilidad.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/legajocontabilidad/diferencia/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[ " + LEGAJO_CONTABILIDAD_JSON + " ]", JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_returnsOkWithLegajoContabilidad() throws Exception {
        when(service.findByUnique(100L, 2024, 6)).thenReturn(sampleLegajoContabilidad());
        when(mapper.toResponse(any(LegajoContabilidad.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/legajocontabilidad/unique/{legajoId}/{anho}/{mes}", 100L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(LEGAJO_CONTABILIDAD_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void delete_returnsNoContentWithEmptyBody() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/legajocontabilidad/{legajocontabilidadId}", 1L))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    void add_returnsCreatedWithSavedLegajoContabilidad() throws Exception {
        when(mapper.toDomain(any())).thenReturn(sampleLegajoContabilidad());
        when(service.save(any(LegajoContabilidad.class))).thenReturn(sampleLegajoContabilidad());
        when(mapper.toResponse(any(LegajoContabilidad.class))).thenReturn(sampleResponse());

        mockMvc.perform(post("/api/haberes/core/legajocontabilidad/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(LEGAJO_CONTABILIDAD_REQUEST_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(LEGAJO_CONTABILIDAD_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithUpdatedLegajoContabilidad() throws Exception {
        when(mapper.toDomain(any())).thenReturn(sampleLegajoContabilidad());
        when(service.updateLegajoContabilidad(anyLong(), any(LegajoContabilidad.class))).thenReturn(sampleLegajoContabilidad());
        when(mapper.toResponse(any(LegajoContabilidad.class))).thenReturn(sampleResponse());

        mockMvc.perform(put("/api/haberes/core/legajocontabilidad/{legajocontabilidadId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(LEGAJO_CONTABILIDAD_REQUEST_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(LEGAJO_CONTABILIDAD_JSON, JsonCompareMode.STRICT));
    }
}
