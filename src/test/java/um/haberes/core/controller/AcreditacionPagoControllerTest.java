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
import um.haberes.core.kotlin.model.AcreditacionPago;
import um.haberes.core.service.AcreditacionPagoService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AcreditacionPagoControllerTest {

    @Mock
    private AcreditacionPagoService service;

    @InjectMocks
    private AcreditacionPagoController controller;

    private MockMvc mockMvc;

    private static final String FECHA_PAGO = "2024-06-15T10:30:00Z";

    private static final String ACREDITACION_PAGO_JSON = """
            {
              "acreditacionPagoId": 1,
              "anho": 2024,
              "mes": 6,
              "fechaPago": null,
              "totalSantander": 5000.25,
              "totalOtrosBancos": 2500.75,
              "comprobanteIdPago": 11,
              "puntoVentaPago": 22,
              "numeroComprobantePago": 33,
              "created": null,
              "updated": null
            }
            """;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private AcreditacionPago sampleAcreditacionPago() {
        AcreditacionPago acreditacionPago = new AcreditacionPago();
        acreditacionPago.setAcreditacionPagoId(1L);
        acreditacionPago.setAnho(2024);
        acreditacionPago.setMes(6);
        acreditacionPago.setTotalSantander(new BigDecimal("5000.25"));
        acreditacionPago.setTotalOtrosBancos(new BigDecimal("2500.75"));
        acreditacionPago.setComprobanteIdPago(11);
        acreditacionPago.setPuntoVentaPago(22);
        acreditacionPago.setNumeroComprobantePago(33L);
        return acreditacionPago;
    }

    @Test
    void findByUnique_returnsOkWithAcreditacionPagoBody() throws Exception {
        when(service.findByUnique(2024, 6, OffsetDateTime.parse(FECHA_PAGO))).thenReturn(sampleAcreditacionPago());

        mockMvc.perform(get("/api/haberes/core/acreditacionpago/unique/{anho}/{mes}/{fechapago}", 2024, 6, FECHA_PAGO))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ACREDITACION_PAGO_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void add_returnsOkWithSavedAcreditacionPago() throws Exception {
        String requestJson = new ObjectMapper().writeValueAsString(sampleAcreditacionPago());
        when(service.add(any(AcreditacionPago.class))).thenReturn(sampleAcreditacionPago());

        mockMvc.perform(post("/api/haberes/core/acreditacionpago/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ACREDITACION_PAGO_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithUpdatedAcreditacionPago() throws Exception {
        String requestJson = new ObjectMapper().writeValueAsString(sampleAcreditacionPago());
        when(service.update(any(AcreditacionPago.class), eq(1L))).thenReturn(sampleAcreditacionPago());

        mockMvc.perform(put("/api/haberes/core/acreditacionpago/{acreditacionpagoId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ACREDITACION_PAGO_JSON, JsonCompareMode.STRICT));
    }
}
