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
import um.haberes.core.hexagonal.cursos.designacion_tipo.application.exception.DesignacionTipoException;
import um.haberes.core.hexagonal.cursos.designacion_tipo.application.service.DesignacionTipoService;
import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.model.DesignacionTipo;
import um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.web.controller.DesignacionTipoController;
import um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.web.mapper.DesignacionTipoDtoMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DesignacionTipoControllerTest {

    @Mock
    private DesignacionTipoService service;

    @InjectMocks
    private DesignacionTipoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new DesignacionTipoController(service, new DesignacionTipoDtoMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private DesignacionTipo sampleDesignacionTipo() {
        DesignacionTipo designacionTipo = new DesignacionTipo();
        designacionTipo.setDesignacionTipoId(1);
        designacionTipo.setNombre("Dedicación Simple");
        designacionTipo.setHorasSemanales(new BigDecimal("20.00"));
        designacionTipo.setHorasTotales(new BigDecimal("800.00"));
        designacionTipo.setSimples(1);
        return designacionTipo;
    }

    private String designacionTipoJson() {
        return """
                {
                  "designacionTipoId": 1,
                  "nombre": "Dedicación Simple",
                  "horasSemanales": 20.00,
                  "horasTotales": 800.00,
                  "simples": 1
                                                    }
                """;
    }

    @Test
    void findAll_returnsOkWithListOfDesignacionTipos() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleDesignacionTipo()));

        mockMvc.perform(get("/api/haberes/core/designaciontipo/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + designacionTipoJson() + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByDesignacionTipoId_returnsOkWithDesignacionTipo() throws Exception {
        when(service.findByDesignacionTipoId(1)).thenReturn(sampleDesignacionTipo());

        mockMvc.perform(get("/api/haberes/core/designaciontipo/{designacionTipoId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(designacionTipoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByDesignacionTipoId_whenServiceThrowsDesignacionTipoException_returnsBadRequest() throws Exception {
        when(service.findByDesignacionTipoId(99)).thenThrow(new DesignacionTipoException(99));

        mockMvc.perform(get("/api/haberes/core/designaciontipo/{designacionTipoId}", 99))
                .andExpect(status().isBadRequest());
    }
}
