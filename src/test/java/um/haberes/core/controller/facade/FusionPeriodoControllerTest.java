package um.haberes.core.controller.facade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import um.haberes.core.service.facade.FusionPeriodoService;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FusionPeriodoControllerTest {

    @Mock
    private FusionPeriodoService service;

    @InjectMocks
    private FusionPeriodoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void fusionar_procesaElLoteYDevuelveElResumen() throws Exception {
        when(service.procesarFusion(List.of(1L, 2L), 2024, 6))
                .thenReturn(new FusionPeriodoService.ResumenLote(2, List.of()));

        mockMvc.perform(post("/api/haberes/core/designaciontool/periodo/fusionar/{anho}/{mes}", 2024, 6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"legajos\":[1,2]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.procesados").value(2))
                .andExpect(jsonPath("$.fallidos.length()").value(0));

        verify(service).procesarFusion(List.of(1L, 2L), 2024, 6);
    }

    @Test
    void desarraigo_procesaElLote() throws Exception {
        when(service.procesarDesarraigo(List.of(3L), 2024, 6))
                .thenReturn(new FusionPeriodoService.ResumenLote(1, List.of()));

        mockMvc.perform(post("/api/haberes/core/designaciontool/periodo/desarraigo/{anho}/{mes}", 2024, 6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"legajos\":[3]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.procesados").value(1));

        verify(service).procesarDesarraigo(List.of(3L), 2024, 6);
    }

    @Test
    void duplicar_procesaElLote() throws Exception {
        when(service.procesarDuplicado(List.of(4L), 2024, 6))
                .thenReturn(new FusionPeriodoService.ResumenLote(1, List.of()));

        mockMvc.perform(post("/api/haberes/core/designaciontool/periodo/duplicar/{anho}/{mes}", 2024, 6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"legajos\":[4]}"))
                .andExpect(status().isOk());

        verify(service).procesarDuplicado(List.of(4L), 2024, 6);
    }

    @Test
    void fusionar_sinLegajos_noProcesaNada() throws Exception {
        when(service.procesarFusion(List.of(), 2024, 6))
                .thenReturn(new FusionPeriodoService.ResumenLote(0, List.of()));

        mockMvc.perform(post("/api/haberes/core/designaciontool/periodo/fusionar/{anho}/{mes}", 2024, 6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.procesados").value(0));

        verify(service).procesarFusion(List.of(), 2024, 6);
    }

    @Test
    void depurar_returnsNoContent() throws Exception {
        mockMvc.perform(post("/api/haberes/core/designaciontool/periodo/depurar/{anho}/{mes}", 2024, 6))
                .andExpect(status().isNoContent());

        verify(service).depurarZombies(2024, 6);
    }

}
