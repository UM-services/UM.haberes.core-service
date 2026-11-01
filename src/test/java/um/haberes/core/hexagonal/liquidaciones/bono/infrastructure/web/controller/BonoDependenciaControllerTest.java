package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import um.haberes.core.hexagonal.liquidaciones.bono.application.service.BonoService;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.ResumenPreparacionBonos;
import um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.mapper.BonoDependenciaDtoMapper;
import um.haberes.core.util.ClientIpResolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BonoDependenciaControllerTest {

    @Mock
    private BonoService bonoService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        BonoDependenciaController controller = new BonoDependenciaController(bonoService,
                new BonoDependenciaDtoMapper(), new ClientIpResolver());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void printPrepareDevuelveElResumenDelLote() throws Exception {
        when(bonoService.prepararDependencia(any(), any(), any(), any(), any(), any()))
                .thenReturn(new ResumenPreparacionBonos(7, 2026, 9, "A", 3, 2,
                        List.of(new ResumenPreparacionBonos.Fallido(1002L, "integridad fallida"))));

        mockMvc.perform(post("/api/haberes/core/bono/dependencia/7/2026/9/A/print-prepare")
                        .header("X-Legajo-Solicitante", "999")
                        .header("X-Forwarded-For", "10.0.0.5, 10.0.0.1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dependenciaId").value(7))
                .andExpect(jsonPath("$.salida").value("A"))
                .andExpect(jsonPath("$.total").value(3))
                .andExpect(jsonPath("$.preparados").value(2))
                .andExpect(jsonPath("$.fallidos[0].legajoId").value(1002))
                .andExpect(jsonPath("$.fallidos[0].motivo").value("integridad fallida"));

        ArgumentCaptor<Long> solicitante = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> ip = ArgumentCaptor.forClass(String.class);
        org.mockito.Mockito.verify(bonoService).prepararDependencia(
                org.mockito.ArgumentMatchers.eq(7), org.mockito.ArgumentMatchers.eq(2026),
                org.mockito.ArgumentMatchers.eq(9), org.mockito.ArgumentMatchers.eq("A"),
                solicitante.capture(), ip.capture());
        assertThat(solicitante.getValue()).isEqualTo(999L);
        assertThat(ip.getValue()).isEqualTo("10.0.0.5");
    }

    @Test
    void printPrepareConHeaderInvalidoDevuelve400() throws Exception {
        mockMvc.perform(post("/api/haberes/core/bono/dependencia/7/2026/9/A/print-prepare")
                        .header("X-Legajo-Solicitante", "no-es-numero"))
                .andExpect(status().isBadRequest());
    }
}
