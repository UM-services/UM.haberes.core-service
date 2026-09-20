package um.haberes.core.controller.view;

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
import um.haberes.core.kotlin.model.view.AsignadoClase;
import um.haberes.core.service.view.AsignadoClaseService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AsignadoClaseControllerTest {

    @Mock
    private AsignadoClaseService service;

    @InjectMocks
    private AsignadoClaseController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private AsignadoClase sampleAsignadoClase() {
        AsignadoClase asignadoClase = new AsignadoClase();
        asignadoClase.setUniqueId("100-20-40");
        asignadoClase.setLegajoId(100L);
        asignadoClase.setDependenciaId(20);
        asignadoClase.setCargoClaseId(40L);
        asignadoClase.setPeriodoDesde(202001L);
        asignadoClase.setPeriodoHasta(202406L);
        asignadoClase.setBasicoDesde(new BigDecimal("10.25"));
        asignadoClase.setBasicoHasta(new BigDecimal("20.50"));
        return asignadoClase;
    }

    @Test
    void findAllAsignados_returnsOkWithAsignadoClaseListBody() throws Exception {
        when(service.findAllAsignados(20, 40L)).thenReturn(List.of(sampleAsignadoClase()));

        mockMvc.perform(get("/api/haberes/core/asignadoclase/asignado/{dependenciaId}/{cargoclaseId}", 20, 40))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          {
                            "uniqueId": "100-20-40",
                            "legajoId": 100,
                            "dependenciaId": 20,
                            "cargoClaseId": 40,
                            "periodoDesde": 202001,
                            "periodoHasta": 202406,
                            "basicoDesde": 10.25,
                            "basicoHasta": 20.50
                          }
                        ]
                        """, JsonCompareMode.STRICT));
    }
}
