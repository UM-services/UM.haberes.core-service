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
import um.haberes.core.exception.extern.EjercicioException;
import um.haberes.core.model.extern.EjercicioDto;
import um.haberes.core.service.extern.EjercicioService;

import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EjercicioControllerTest {

    @Mock
    private EjercicioService service;

    @InjectMocks
    private EjercicioController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private EjercicioDto sampleDto() {
        EjercicioDto dto = new EjercicioDto();
        dto.setEjercicioId(5);
        dto.setNombre("Ejercicio 2024");
        dto.setBloqueado((byte) 0);
        dto.setOrdenContableResultado(1);
        dto.setOrdenContableBienesUso(2);
        return dto;
    }

    private final String expectedDtoJson = """
            {
              "ejercicioId": 5,
              "nombre": "Ejercicio 2024",
              "fechaInicio": null,
              "fechaFinal": null,
              "bloqueado": 0,
              "ordenContableResultado": 1,
              "ordenContableBienesUso": 2
            }
            """;

    @Test
    void findByPeriodo_returnsOkWithEjercicioDto() throws Exception {
        when(service.findByPeriodo(2024, 6)).thenReturn(sampleDto());

        mockMvc.perform(get("/api/haberes/core/ejercicio/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedDtoJson, JsonCompareMode.STRICT));
    }

    @Test
    void findByPeriodo_whenServiceThrowsEjercicioException_returnsBadRequest() throws Exception {
        when(service.findByPeriodo(2024, 1))
                .thenThrow(new EjercicioException(OffsetDateTime.parse("2024-01-31T23:59:59Z")));

        mockMvc.perform(get("/api/haberes/core/ejercicio/periodo/{anho}/{mes}", 2024, 1))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByFecha_returnsOkWithEjercicioDto() throws Exception {
        when(service.findByFecha(any(OffsetDateTime.class))).thenReturn(sampleDto());

        mockMvc.perform(get("/api/haberes/core/ejercicio/fecha/{fecha}", "2024-06-30T23:00:00Z"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedDtoJson, JsonCompareMode.STRICT));
    }

    @Test
    void findByFecha_whenServiceThrowsEjercicioException_returnsBadRequest() throws Exception {
        when(service.findByFecha(any(OffsetDateTime.class)))
                .thenThrow(new EjercicioException(OffsetDateTime.parse("2024-06-30T23:00:00Z")));

        mockMvc.perform(get("/api/haberes/core/ejercicio/fecha/{fecha}", "2024-06-30T23:00:00Z"))
                .andExpect(status().isBadRequest());
    }
}
