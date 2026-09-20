package um.haberes.core.controller.facade;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import um.haberes.core.kotlin.model.CargoClasePeriodo;
import um.haberes.core.service.facade.CargoClaseToolService;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CargoClaseToolControllerTest {

    @Mock
    private CargoClaseToolService service;

    private CargoClaseToolController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new CargoClaseToolController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private CargoClasePeriodo sampleCargoClasePeriodo() {
        return new CargoClasePeriodo(
                7L,
                100L,
                2L,
                46,
                3,
                12,
                202401L,
                202406L,
                20,
                new BigDecimal("45.50"),
                (byte) 1,
                "Ayudante de primera",
                null,
                null,
                null,
                null,
                null
        );
    }

    @Test
    void addCargo_returnsOkWithMessage() throws Exception {
        when(service.addCargo(any(CargoClasePeriodo.class))).thenReturn("Ok");

        mockMvc.perform(post("/api/haberes/core/cargoclasetool/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleCargoClasePeriodo())))
                .andExpect(status().isOk())
                .andExpect(content().string("Ok"));
    }

    @Test
    void deleteCargo_returnsOkWithMessage() throws Exception {
        when(service.deleteCargo(5L)).thenReturn("Ok");

        mockMvc.perform(delete("/api/haberes/core/cargoclasetool/{cargoClasePeriodoId}", 5))
                .andExpect(status().isOk())
                .andExpect(content().string("Ok"));
    }

    @Test
    void changeHasta_returnsOkWithMessage() throws Exception {
        when(service.changeHasta(5L, 2024, 12)).thenReturn("Ok");

        mockMvc.perform(put("/api/haberes/core/cargoclasetool/{cargoClasePeriodoId}/{anhoHasta}/{mesHasta}", 5, 2024, 12))
                .andExpect(status().isOk())
                .andExpect(content().string("Ok"));
    }

    @Test
    void updateValorHora_returnsNoContent() throws Exception {
        mockMvc.perform(get("/api/haberes/core/cargoclasetool/update/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(service).updateValorHora(2024, 6);
    }
}
