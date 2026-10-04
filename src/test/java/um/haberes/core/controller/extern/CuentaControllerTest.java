package um.haberes.core.controller.extern;

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
import um.haberes.core.exception.extern.CuentaException;
import um.haberes.core.model.extern.CuentaDto;
import um.haberes.core.service.extern.CuentaService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CuentaControllerTest {

    @Mock
    private CuentaService service;

    @InjectMocks
    private CuentaController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private CuentaDto sampleDto() {
        CuentaDto dto = new CuentaDto();
        dto.setNumeroCuenta(new BigDecimal("4101001"));
        dto.setNombre("Sueldos y Salarios");
        dto.setIntegradora((byte) 0);
        dto.setGrado(2);
        dto.setGrado1(new BigDecimal("41"));
        dto.setGeograficaId(7);
        dto.setVisible((byte) 1);
        dto.setCuentaContableId(42L);
        return dto;
    }

    private final String expectedDtoJson = """
            {
              "numeroCuenta": 4101001,
              "nombre": "Sueldos y Salarios",
              "integradora": 0,
              "grado": 2,
              "grado1": 41,
              "grado2": null,
              "grado3": null,
              "grado4": null,
              "geograficaId": 7,
              "fechaBloqueo": null,
              "visible": 1,
              "cuentaContableId": 42
            }
            """;

    @Test
    void findAll_returnsOkWithListOfCuentaDto() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleDto()));

        mockMvc.perform(get("/api/haberes/core/cuenta/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedDtoJson + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByStrings_returnsOkWithListOfCuentaDto() throws Exception {
        List<String> conditions = List.of("Sueldos", "4101");
        when(service.findByStrings(conditions, true)).thenReturn(List.of(sampleDto()));

        mockMvc.perform(post("/api/haberes/core/cuenta/search/{visible}", true)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(conditions)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedDtoJson + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByNumeroCuenta_returnsOkWithCuentaDto() throws Exception {
        when(service.findByNumeroCuenta(new BigDecimal("4101001"))).thenReturn(sampleDto());

        mockMvc.perform(get("/api/haberes/core/cuenta/{numeroCuenta}", "4101001"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedDtoJson, JsonCompareMode.STRICT));
    }

    @Test
    void findByNumeroCuenta_whenServiceThrowsCuentaException_returnsBadRequest() throws Exception {
        when(service.findByNumeroCuenta(new BigDecimal("9999999")))
                .thenThrow(new CuentaException(new BigDecimal("9999999")));

        mockMvc.perform(get("/api/haberes/core/cuenta/{numeroCuenta}", "9999999"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByCuentaContableId_returnsOkWithCuentaDto() throws Exception {
        when(service.findByCuentaContableId(42L)).thenReturn(sampleDto());

        mockMvc.perform(get("/api/haberes/core/cuenta/id/{cuentaContableId}", 42L))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedDtoJson, JsonCompareMode.STRICT));
    }

    @Test
    void findByCuentaContableId_whenServiceThrowsCuentaException_returnsBadRequest() throws Exception {
        when(service.findByCuentaContableId(99L)).thenThrow(new CuentaException(99L));

        mockMvc.perform(get("/api/haberes/core/cuenta/id/{cuentaContableId}", 99L))
                .andExpect(status().isBadRequest());
    }
}
