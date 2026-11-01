package um.haberes.core.controller.extern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import um.haberes.core.model.extern.CursoCargoContratadoDto;
import um.haberes.core.service.extern.CursoCargoContratadoService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CursoCargoContratadoControllerTest {

    @Mock
    private CursoCargoContratadoService service;

    private CursoCargoContratadoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new CursoCargoContratadoController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private CursoCargoContratadoDto sampleDto() {
        CursoCargoContratadoDto dto = new CursoCargoContratadoDto();
        dto.setCursoCargoContratadoId(9L);
        dto.setCursoId(7L);
        dto.setAnho(2024);
        dto.setMes(6);
        dto.setContratoId(11L);
        dto.setPersonaId(new BigDecimal("100"));
        dto.setDocumentoId(30123456);
        dto.setCargoTipoId(1);
        dto.setHorasSemanales(new BigDecimal("20.00"));
        dto.setHorasTotales(new BigDecimal("240.00"));
        dto.setDesignacionTipoId(2);
        dto.setCategoriaId(1);
        dto.setCursoCargoNovedadId(13L);
        dto.setAcreditado((byte) 0);
        return dto;
    }

    @Test
    void findAllByCurso_returnsOkWithListOfCursoCargoContratadoDto() throws Exception {
        when(service.findAllByCurso(7L, 2024, 6)).thenReturn(List.of(sampleDto()));

        mockMvc.perform(get("/api/haberes/core/cursoCargoContratado/curso/{cursoId}/{anho}/{mes}", 7L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          {
                            "cursoCargoContratadoId": 9,
                            "cursoId": 7,
                            "anho": 2024,
                            "mes": 6,
                            "contratoId": 11,
                            "personaId": 100,
                            "documentoId": 30123456,
                            "cargoTipoId": 1,
                            "horasSemanales": 20.00,
                            "horasTotales": 240.00,
                            "designacionTipoId": 2,
                            "categoriaId": 1,
                            "cursoCargoNovedadId": 13,
                            "acreditado": 0,
                            "contratadoPersona": null
                          }
                        ]
                        """, JsonCompareMode.STRICT));
    }
}
