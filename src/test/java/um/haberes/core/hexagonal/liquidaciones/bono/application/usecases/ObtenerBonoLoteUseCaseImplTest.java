package um.haberes.core.hexagonal.liquidaciones.bono.application.usecases;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.liquidaciones.bono.application.exception.BonoException;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.BonoLote;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.BonoLoteDataPort;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.LiquidacionQueryPort;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.DeleteZeroItemsByLegajoUseCase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ObtenerBonoLoteUseCaseImplTest {

    @Mock
    private LiquidacionQueryPort liquidacionQueryPort;

    @Mock
    private DeleteZeroItemsByLegajoUseCase deleteZeroItemsByLegajoUseCase;

    @Mock
    private BonoLoteDataPort bonoLoteDataPort;

    @InjectMocks
    private ObtenerBonoLoteUseCaseImpl useCase;

    @Test
    void sinLiquidacionesCortaConErrorDeNegocio() {
        when(liquidacionQueryPort.findLegajoIdsByDependencia(7, 2026, 9, "A")).thenReturn(List.of());

        assertThatThrownBy(() -> useCase.obtener(7, 2026, 9, "A"))
                .isInstanceOf(BonoException.class)
                .hasMessageContaining("No hay liquidaciones");
        verifyNoInteractions(bonoLoteDataPort);
    }

    @Test
    void limpiaLosItemsEnCeroDeCadaLegajoAntesDeLeerElLote() {
        BonoLote lote = new BonoLote(2026, 9, 7, "A",
                new BonoLote.Control(null, null, null, null, "11%", null),
                new BonoLote.Catalogo(List.of(), null, List.of(), List.of(), List.of()),
                List.of());
        when(liquidacionQueryPort.findLegajoIdsByDependencia(7, 2026, 9, "A"))
                .thenReturn(List.of(1001L, 1002L));
        when(bonoLoteDataPort.cargar(List.of(1001L, 1002L), 2026, 9, 7, "A")).thenReturn(lote);

        assertThat(useCase.obtener(7, 2026, 9, "A")).isSameAs(lote);
        verify(deleteZeroItemsByLegajoUseCase).deleteZeroItemsByLegajo(1001L, 2026, 9);
        verify(deleteZeroItemsByLegajoUseCase).deleteZeroItemsByLegajo(1002L, 2026, 9);
    }

    @Test
    void sinControlDelPeriodoCortaConErrorDeNegocio() {
        when(liquidacionQueryPort.findLegajoIdsByDependencia(anyInt(), anyInt(), anyInt(), anyString()))
                .thenReturn(List.of(1001L));
        when(bonoLoteDataPort.cargar(any(), any(), any(), any(), any()))
                .thenReturn(new BonoLote(2026, 9, 7, "A", null,
                        new BonoLote.Catalogo(List.of(), null, List.of(), List.of(), List.of()),
                        List.of()));

        assertThatThrownBy(() -> useCase.obtener(7, 2026, 9, "A"))
                .isInstanceOf(BonoException.class)
                .hasMessageContaining("No hay control");
    }
}
