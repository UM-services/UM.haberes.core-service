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
import um.haberes.core.exception.ControlException;
import um.haberes.core.model.ControlEntity;
import um.haberes.core.service.ControlService;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ControlControllerTest {

    @Mock
    private ControlService service;

    @InjectMocks
    private ControlController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private ControlEntity sampleControl() {
        ControlEntity control = new ControlEntity();
        control.setControlId(1L);
        control.setAnho(2024);
        control.setMes(6);
        control.setAporteJubilatorio("11");
        control.setDepositoBanco("BROU");
        control.setDoctorado(new BigDecimal("500.50"));
        control.setMaestria(new BigDecimal("400.25"));
        control.setModoLiquidacionId(3);
        return control;
    }

    private String controlJson() {
        return """
                {
                  "controlId": 1,
                  "anho": 2024,
                  "mes": 6,
                  "fechaDesde": null,
                  "fechaHasta": null,
                  "fechaPago": null,
                  "aporteJubilatorio": "11",
                  "depositoBanco": "BROU",
                  "fechaDeposito": null,
                  "doctorado": 500.50,
                  "maestria": 400.25,
                  "especializacion": 0,
                  "familiaNumerosa": 0,
                  "escuelaPrimaria": 0,
                  "escuelaSecundaria": 0,
                  "escuelaPrimariaNumerosa": 0,
                  "escuelaSecundariaNumerosa": 0,
                  "prenatal": 0,
                  "libre": 0,
                  "ayudaEscolar": 0,
                  "matrimonio": 0,
                  "nacimiento": 0,
                  "funcionDireccion": 0,
                  "mayorResponsabilidadPatrimonial": 0,
                  "polimedb": 0,
                  "polimedo": 0,
                  "montoeci": 0,
                  "valampo": 0,
                  "jubilaem": 0,
                  "inssjpem": 0,
                  "osociaem": 0,
                  "jubilpat": 0,
                  "inssjpat": 0,
                  "osocipat": 0,
                  "ansalpat": 0,
                  "salfapat": 0,
                  "minimoAporte": 0,
                  "maximoAporte": 0,
                  "mincontr": 0,
                  "maximo1sijp": 0,
                  "maximo2sijp": 0,
                  "maximo3sijp": 0,
                  "maximo4sijp": 0,
                  "maximo5sijp": 0,
                  "estadoDocenteTitular": 0,
                  "estadoDocenteAdjunto": 0,
                  "estadoDocenteAuxiliar": 0,
                  "adicionalHoraCargoClase": 0,
                  "horaReferenciaEtec": 0,
                  "modoLiquidacionId": 3,
                  "modoLiquidacion": null,
                  "created": null,
                  "updated": null
                }
                """;
    }

    @Test
    void findByPeriodo_returnsOkWithControlBody() throws Exception {
        when(service.findByPeriodo(2024, 6)).thenReturn(sampleControl());

        mockMvc.perform(get("/api/haberes/core/control/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(controlJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByPeriodo_whenServiceThrowsControlException_returnsBadRequest() throws Exception {
        when(service.findByPeriodo(2024, 1)).thenThrow(new ControlException(2024, 1));

        mockMvc.perform(get("/api/haberes/core/control/periodo/{anho}/{mes}", 2024, 1))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_returnsOkWithControlBody() throws Exception {
        when(service.add(any(ControlEntity.class))).thenReturn(sampleControl());

        mockMvc.perform(post("/api/haberes/core/control/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleControl())))
                .andExpect(status().isOk())
                .andExpect(content().json(controlJson(), JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithControlBody() throws Exception {
        when(service.update(any(ControlEntity.class), eq(1L))).thenReturn(sampleControl());

        mockMvc.perform(put("/api/haberes/core/control/{controlId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleControl())))
                .andExpect(status().isOk())
                .andExpect(content().json(controlJson(), JsonCompareMode.STRICT));
    }
}
