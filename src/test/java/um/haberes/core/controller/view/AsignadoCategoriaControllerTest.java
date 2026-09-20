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
import um.haberes.core.kotlin.model.view.AsignadoCategoria;
import um.haberes.core.service.view.AsignadoCategoriaService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AsignadoCategoriaControllerTest {

    @Mock
    private AsignadoCategoriaService service;

    @InjectMocks
    private AsignadoCategoriaController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private AsignadoCategoria sampleAsignadoCategoria() {
        AsignadoCategoria asignadoCategoria = new AsignadoCategoria();
        asignadoCategoria.setUniqueId("100-20-30");
        asignadoCategoria.setLegajoId(100L);
        asignadoCategoria.setDependenciaId(20);
        asignadoCategoria.setCategoriaId(30);
        asignadoCategoria.setPeriodoDesde(202001L);
        asignadoCategoria.setPeriodoHasta(202406L);
        asignadoCategoria.setBasicoDesde(new BigDecimal("10.25"));
        asignadoCategoria.setBasicoHasta(new BigDecimal("20.50"));
        return asignadoCategoria;
    }

    @Test
    void findAllAsignados_returnsOkWithAsignadoCategoriaListBody() throws Exception {
        when(service.findAllAsignados(20, 30)).thenReturn(List.of(sampleAsignadoCategoria()));

        mockMvc.perform(get("/api/haberes/core/asignadocategoria/asignado/{dependenciaId}/{categoriaId}", 20, 30))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          {
                            "uniqueId": "100-20-30",
                            "legajoId": 100,
                            "dependenciaId": 20,
                            "categoriaId": 30,
                            "periodoDesde": 202001,
                            "periodoHasta": 202406,
                            "basicoDesde": 10.25,
                            "basicoHasta": 20.50
                          }
                        ]
                        """, JsonCompareMode.STRICT));
    }
}
