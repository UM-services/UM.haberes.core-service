package um.haberes.core.controller.facade;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import um.haberes.core.kotlin.model.internal.OrdenPagoRequest;
import um.haberes.core.service.facade.OrdenPagoService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrdenPagoControllerTest {

    @Mock
    private OrdenPagoService service;

    @InjectMocks
    private OrdenPagoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private OrdenPagoRequest sampleRequest() {
        OrdenPagoRequest request = new OrdenPagoRequest();
        request.setAnho(2024);
        request.setMes(6);
        request.setFechaPago(null);
        request.setTotalSantander(new BigDecimal("1000.50"));
        request.setTotalOtrosBancos(new BigDecimal("200.25"));
        request.setIndividual(true);
        return request;
    }

    @Test
    void generateOrdenPago_returnsOkWithTrue() throws Exception {
        when(service.generateOrdenPago(any(OrdenPagoRequest.class))).thenReturn(Boolean.TRUE);

        mockMvc.perform(post("/api/haberes/core/ordenPago/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleRequest())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));
    }

    @Test
    void generateOrdenPago_whenServiceReturnsFalse_returnsOkWithFalse() throws Exception {
        when(service.generateOrdenPago(any(OrdenPagoRequest.class))).thenReturn(Boolean.FALSE);

        mockMvc.perform(post("/api/haberes/core/ordenPago/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleRequest())))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void generateOrdenPago_deserializesRequestBodyIntoRequestModel() throws Exception {
        when(service.generateOrdenPago(any(OrdenPagoRequest.class))).thenReturn(Boolean.TRUE);
        ArgumentCaptor<OrdenPagoRequest> captor = ArgumentCaptor.forClass(OrdenPagoRequest.class);

        mockMvc.perform(post("/api/haberes/core/ordenPago/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleRequest())))
                .andExpect(status().isOk());

        verify(service).generateOrdenPago(captor.capture());
        OrdenPagoRequest received = captor.getValue();
        assertNotNull(received);
        assertEquals(2024, received.getAnho());
        assertEquals(6, received.getMes());
        assertEquals(new BigDecimal("1000.50"), received.getTotalSantander());
        assertEquals(new BigDecimal("200.25"), received.getTotalOtrosBancos());
        assertEquals(true, received.getIndividual());
        assertNull(received.getFechaPago());
    }
}
