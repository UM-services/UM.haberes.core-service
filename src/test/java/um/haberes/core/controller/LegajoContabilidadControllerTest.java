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
import um.haberes.core.kotlin.model.LegajoContabilidad;
import um.haberes.core.service.LegajoContabilidadService;

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

    @InjectMocks
    private LegajoContabilidadController controller;

    private MockMvc mockMvc;

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

    private String legajoContabilidadJson() throws Exception {
        return new ObjectMapper().writeValueAsString(sampleLegajoContabilidad());
    }

    @Test
    void findAllDiferenciaByPeriodo_returnsOkWithListOfDiferencias() throws Exception {
        when(service.findAllDiferenciaByPeriodo(2024, 6)).thenReturn(List.of(sampleLegajoContabilidad()));

        mockMvc.perform(get("/api/haberes/core/legajocontabilidad/diferencia/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          {
                            "legajoContabilidadId": 1,
                            "legajoId": 100,
                            "anho": 2024,
                            "mes": 6,
                            "diferencia": 1,
                            "remunerativo": 100.50,
                            "noRemunerativo": 20.25,
                            "created": null,
                            "updated": null
                          }
                        ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_returnsOkWithLegajoContabilidad() throws Exception {
        when(service.findByUnique(100L, 2024, 6)).thenReturn(sampleLegajoContabilidad());

        mockMvc.perform(get("/api/haberes/core/legajocontabilidad/unique/{legajoId}/{anho}/{mes}", 100L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "legajoContabilidadId": 1,
                          "legajoId": 100,
                          "anho": 2024,
                          "mes": 6,
                          "diferencia": 1,
                          "remunerativo": 100.50,
                          "noRemunerativo": 20.25,
                          "created": null,
                          "updated": null
                        }
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void delete_returnsNoContentWithEmptyBody() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/legajocontabilidad/{legajocontabilidadId}", 1L))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    void add_returnsOkWithSavedLegajoContabilidad() throws Exception {
        when(service.add(any(LegajoContabilidad.class))).thenReturn(sampleLegajoContabilidad());

        mockMvc.perform(post("/api/haberes/core/legajocontabilidad/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(legajoContabilidadJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "legajoContabilidadId": 1,
                          "legajoId": 100,
                          "anho": 2024,
                          "mes": 6,
                          "diferencia": 1,
                          "remunerativo": 100.50,
                          "noRemunerativo": 20.25,
                          "created": null,
                          "updated": null
                        }
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithUpdatedLegajoContabilidad() throws Exception {
        when(service.update(any(LegajoContabilidad.class), anyLong())).thenReturn(sampleLegajoContabilidad());

        mockMvc.perform(put("/api/haberes/core/legajocontabilidad/{legajocontabilidadId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(legajoContabilidadJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "legajoContabilidadId": 1,
                          "legajoId": 100,
                          "anho": 2024,
                          "mes": 6,
                          "diferencia": 1,
                          "remunerativo": 100.50,
                          "noRemunerativo": 20.25,
                          "created": null,
                          "updated": null
                        }
                        """, JsonCompareMode.STRICT));
    }
}
