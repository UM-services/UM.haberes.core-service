package um.haberes.core.controller.extern;

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
import um.haberes.core.kotlin.model.extern.ContratadoPersonaDto;
import um.haberes.core.service.extern.ContratadoPersonaService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ContratadoPersonaControllerTest {

    @Mock
    private ContratadoPersonaService service;

    @InjectMocks
    private ContratadoPersonaController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private ContratadoPersonaDto sampleDto() {
        ContratadoPersonaDto dto = new ContratadoPersonaDto();
        dto.setUniqueId("2024-06-100");
        dto.setPersonaId(new BigDecimal("100"));
        dto.setDocumentoId(30123456);
        dto.setApellido("Perez");
        dto.setNombre("Juan");
        dto.setCuit("20301234567");
        return dto;
    }

    @Test
    void findAll_returnsOkWithListOfContratadoPersonaDto() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleDto()));

        mockMvc.perform(get("/api/haberes/core/contratadoPersona/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          {
                            "uniqueId": "2024-06-100",
                            "personaId": 100,
                            "documentoId": 30123456,
                            "apellido": "Perez",
                            "nombre": "Juan",
                            "cuit": "20301234567"
                          }
                        ]
                        """, JsonCompareMode.STRICT));
    }
}
