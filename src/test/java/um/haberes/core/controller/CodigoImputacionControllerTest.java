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
import um.haberes.core.kotlin.model.CodigoImputacion;
import um.haberes.core.service.CodigoImputacionService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CodigoImputacionControllerTest {

    @Mock
    private CodigoImputacionService service;

    @InjectMocks
    private CodigoImputacionController controller;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private CodigoImputacion sampleCodigoImputacion() {
        CodigoImputacion codigoImputacion = new CodigoImputacion();
        codigoImputacion.setCodigoImputacionId(1L);
        codigoImputacion.setDependenciaId(2);
        codigoImputacion.setFacultadId(3);
        codigoImputacion.setGeograficaId(4);
        codigoImputacion.setCodigoId(5);
        codigoImputacion.setCuentaSueldosDocente(new BigDecimal("1100.10"));
        codigoImputacion.setCuentaAportesDocente(new BigDecimal("1200.20"));
        codigoImputacion.setCuentaSueldosNoDocente(new BigDecimal("1300.30"));
        codigoImputacion.setCuentaAportesNoDocente(new BigDecimal("1400.40"));
        return codigoImputacion;
    }

    private String codigoImputacionJson() {
        return """
                {
                  "codigoImputacionId": 1,
                  "dependenciaId": 2,
                  "facultadId": 3,
                  "geograficaId": 4,
                  "codigoId": 5,
                  "cuentaSueldosDocente": 1100.10,
                  "cuentaAportesDocente": 1200.20,
                  "cuentaSueldosNoDocente": 1300.30,
                  "cuentaAportesNoDocente": 1400.40,
                  "created": null,
                  "updated": null
                }
                """;
    }

    @Test
    void findAll_returnsOkWithListOfCodigoImputacion() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleCodigoImputacion()));

        mockMvc.perform(get("/api/haberes/core/codigoimputacion/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [ {
                          "codigoImputacionId": 1,
                          "dependenciaId": 2,
                          "facultadId": 3,
                          "geograficaId": 4,
                          "codigoId": 5,
                          "cuentaSueldosDocente": 1100.10,
                          "cuentaAportesDocente": 1200.20,
                          "cuentaSueldosNoDocente": 1300.30,
                          "cuentaAportesNoDocente": 1400.40,
                          "created": null,
                          "updated": null
                        } ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findByCodigoimputacionId_returnsOkWithCodigoImputacion() throws Exception {
        when(service.findByCodigoimputacionId(1L)).thenReturn(sampleCodigoImputacion());

        mockMvc.perform(get("/api/haberes/core/codigoimputacion/{codigoimputacionId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(codigoImputacionJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_returnsOkWithCodigoImputacion() throws Exception {
        when(service.findByUnique(2, 3, 4, 5)).thenReturn(sampleCodigoImputacion());

        mockMvc.perform(get("/api/haberes/core/codigoimputacion/unique/{dependenciaId}/{facultadId}/{geograficaId}/{codigoId}",
                        2, 3, 4, 5))
                .andExpect(status().isOk())
                .andExpect(content().json(codigoImputacionJson(), JsonCompareMode.STRICT));
    }

    @Test
    void add_returnsOkWithCodigoImputacion() throws Exception {
        when(service.add(any(CodigoImputacion.class))).thenReturn(sampleCodigoImputacion());

        mockMvc.perform(post("/api/haberes/core/codigoimputacion/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCodigoImputacion())))
                .andExpect(status().isOk())
                .andExpect(content().json(codigoImputacionJson(), JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithCodigoImputacion() throws Exception {
        when(service.update(any(CodigoImputacion.class), anyLong())).thenReturn(sampleCodigoImputacion());

        mockMvc.perform(put("/api/haberes/core/codigoimputacion/{codigoimputacionId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCodigoImputacion())))
                .andExpect(status().isOk())
                .andExpect(content().json(codigoImputacionJson(), JsonCompareMode.STRICT));
    }
}
