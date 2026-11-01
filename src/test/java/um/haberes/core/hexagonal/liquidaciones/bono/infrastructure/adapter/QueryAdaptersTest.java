package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.adapter;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.liquidaciones.liquidacion.application.exception.LiquidacionException;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.application.service.LiquidacionService;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.personas.persona.application.exception.PersonaException;
import um.haberes.core.hexagonal.personas.persona.application.service.PersonaService;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryAdaptersTest {

    @Mock
    private PersonaService personaService;

    @Mock
    private LiquidacionService liquidacionService;

    @InjectMocks
    private PersonaServiceDependenciaAdapter personaAdapter;

    @InjectMocks
    private LiquidacionServiceQueryAdapter liquidacionAdapter;

    @Test
    void personaConDependencia() {
        when(personaService.findByLegajoId(12345L)).thenReturn(Persona.builder().legajoId(12345L).dependenciaId(7)
                .build());
        assertThat(personaAdapter.findDependenciaIdByLegajoId(12345L)).isEqualTo(Optional.of(7));
    }

    @Test
    void personaInexistenteEsDependenciaFaltante() {
        when(personaService.findByLegajoId(12345L)).thenThrow(new PersonaException(12345L));
        assertThat(personaAdapter.findDependenciaIdByLegajoId(12345L)).isEmpty();
    }

    @Test
    void liquidacionExiste() {
        when(liquidacionService.getLiquidacionByUniqueKey(12345L, 2026, 8)).thenReturn(Liquidacion.builder().build());
        assertThat(liquidacionAdapter.existsByLegajoIdAndAnhoAndMes(12345L, 2026, 8)).isTrue();
    }

    @Test
    void liquidacionInexistente() {
        when(liquidacionService.getLiquidacionByUniqueKey(12345L, 2026, 8))
                .thenThrow(new LiquidacionException(12345L, 2026, 8));
        assertThat(liquidacionAdapter.existsByLegajoIdAndAnhoAndMes(12345L, 2026, 8)).isFalse();
    }
}
