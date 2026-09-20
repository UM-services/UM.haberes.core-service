package um.haberes.core.controller.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import um.haberes.core.model.view.DocenteDesignacion;
import um.haberes.core.service.view.DocenteDesignacionService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DocenteDesignacionControllerTest {

    @Mock
    private DocenteDesignacionService service;

    private DocenteDesignacionController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new DocenteDesignacionController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private DocenteDesignacion sampleDocenteDesignacion() {
        DocenteDesignacion docenteDesignacion = new DocenteDesignacion();
        docenteDesignacion.setLegajoId(100L);
        docenteDesignacion.setAnho(2024);
        docenteDesignacion.setMes(6);
        docenteDesignacion.setFacultadId(1);
        docenteDesignacion.setGeograficaId(2);
        docenteDesignacion.setEspacio("Informatica");
        docenteDesignacion.setHorasSemanales(new BigDecimal("8.50"));
        docenteDesignacion.setCargo("Titular");
        docenteDesignacion.setDesignacion("Regular");
        docenteDesignacion.setHorasDesignacion(new BigDecimal("4.25"));
        docenteDesignacion.setAnual((byte) 1);
        docenteDesignacion.setSemestre1((byte) 0);
        docenteDesignacion.setSemestre2((byte) 1);
        return docenteDesignacion;
    }

    @Test
    void findAllByPeriodo_returnsOkWithDocenteDesignacionListBody() throws Exception {
        when(service.findAllByPeriodo(2024, 6)).thenReturn(List.of(sampleDocenteDesignacion()));

        mockMvc.perform(get("/api/haberes/core/docenteDesignacion/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          {
                            "legajoId": 100,
                            "anho": 2024,
                            "mes": 6,
                            "facultadId": 1,
                            "geograficaId": 2,
                            "espacio": "Informatica",
                            "horasSemanales": 8.50,
                            "cargo": "Titular",
                            "designacion": "Regular",
                            "horasDesignacion": 4.25,
                            "anual": 1,
                            "semestre1": 0,
                            "semestre2": 1
                          }
                        ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findAllByLegajo_returnsOkWithDocenteDesignacionListBody() throws Exception {
        when(service.findAllByLegajo(100L, 2024, 6)).thenReturn(List.of(sampleDocenteDesignacion()));

        mockMvc.perform(get("/api/haberes/core/docenteDesignacion/legajo/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          {
                            "legajoId": 100,
                            "anho": 2024,
                            "mes": 6,
                            "facultadId": 1,
                            "geograficaId": 2,
                            "espacio": "Informatica",
                            "horasSemanales": 8.50,
                            "cargo": "Titular",
                            "designacion": "Regular",
                            "horasDesignacion": 4.25,
                            "anual": 1,
                            "semestre1": 0,
                            "semestre2": 1
                          }
                        ]
                        """, JsonCompareMode.STRICT));
    }
}
