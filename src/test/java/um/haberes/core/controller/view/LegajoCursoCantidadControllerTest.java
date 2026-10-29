package um.haberes.core.controller.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import um.haberes.core.model.view.LegajoCursoCantidad;
import um.haberes.core.service.view.LegajoCursoCantidadService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LegajoCursoCantidadControllerTest {

    @Mock
    private LegajoCursoCantidadService service;

    @InjectMocks
    private LegajoCursoCantidadController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void devuelveLaVistaDelPeriodo() throws Exception {
        LegajoCursoCantidad fila = new LegajoCursoCantidad("2026.9.1001.2.3", 2026, 9, 1001L, 2, 3, 1,
                0, 2);
        when(service.findAllByPeriodo(2026, 9)).thenReturn(List.of(fila));

        mockMvc.perform(get("/api/haberes/core/legajocursoCantidad/periodo/2026/9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].legajoId").value(1001))
                .andExpect(jsonPath("$[0].facultadId").value(2))
                .andExpect(jsonPath("$[0].geograficaId").value(3))
                .andExpect(jsonPath("$[0].anuales").value(1))
                .andExpect(jsonPath("$[0].semestre2").value(2));
    }
}
