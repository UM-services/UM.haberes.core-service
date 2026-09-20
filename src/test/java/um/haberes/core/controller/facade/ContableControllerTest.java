package um.haberes.core.controller.facade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import um.haberes.core.kotlin.model.extern.CuentaMovimientoDto;
import um.haberes.core.service.facade.ContableService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ContableControllerTest {

    @Mock
    private ContableService service;

    private ContableController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new ContableController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private CuentaMovimientoDto sampleCuentaMovimientoDto() {
        return new CuentaMovimientoDto(
                9L,
                null,
                3,
                1,
                new BigDecimal("450.75"),
                (byte) 1,
                77,
                "Concepto de prueba",
                new BigDecimal("1234.56"),
                42,
                0,
                2,
                8L,
                11L,
                (byte) 0,
                123L,
                null
        );
    }

    @Test
    void generateByLegajo_returnsNoContent() throws Exception {
        mockMvc.perform(get("/api/haberes/core/contable/generatelegajo/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(service).generateByLegajo(100L, 2024, 6);
    }

    @Test
    void deleteAllByLegajo_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/contable/deletelegajo/{legajoId}/{anho}/{mes}", 100, 2024, 6))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(service).deleteAllByLegajo(100L, 2024, 6);
    }

    @Test
    void deleteAllByPeriodo_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/contable/deleteperiodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(service).deleteAllByPeriodo(2024, 6);
    }

    @Test
    void findAllByAsiento_returnsOkWithListOfCuentaMovimientoDto() throws Exception {
        OffsetDateTime fechaContable = OffsetDateTime.parse("2024-06-15T13:15:30Z");
        when(service.findAllByAsiento(fechaContable, 3)).thenReturn(List.of(sampleCuentaMovimientoDto()));

        mockMvc.perform(get("/api/haberes/core/contable/asiento/{fechaContable}/{ordenContable}",
                        "2024-06-15T13:15:30Z", 3))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          {
                            "cuentaMovimientoId": 9,
                            "fechaContable": null,
                            "ordenContable": 3,
                            "item": 1,
                            "numeroCuenta": 450.75,
                            "debita": 1,
                            "comprobanteId": 77,
                            "concepto": "Concepto de prueba",
                            "importe": 1234.56,
                            "proveedorId": 42,
                            "numeroAnulado": 0,
                            "version": 2,
                            "proveedorMovimientoId": 8,
                            "proveedorMovimientoIdOrdenPago": 11,
                            "apertura": 0,
                            "trackId": 123,
                            "cuentaDto": null
                          }
                        ]
                        """, JsonCompareMode.STRICT));
    }
}
