package um.haberes.core.hexagonal.liquidaciones.bono.application.usecases;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.liquidaciones.bono.application.exception.BonoException;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.AuditoriaBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.BonoImpresion;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.FaltanteBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.ResumenPreparacionBonos;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.PrintPrepareBonoUseCase;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.LiquidacionQueryPort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrepararBonosDependenciaUseCaseImplTest {

    @Mock
    private LiquidacionQueryPort liquidacionQueryPort;

    @Mock
    private PrintPrepareBonoUseCase printPrepareBonoUseCase;

    @InjectMocks
    private PrepararBonosDependenciaUseCaseImpl useCase;

    @Test
    void preparaYAuditaCadaLegajoDeLaDependencia() {
        when(liquidacionQueryPort.findLegajoIdsByDependencia(7, 2026, 9, "A"))
                .thenReturn(List.of(1001L, 1002L));
        when(printPrepareBonoUseCase.printPrepare(any(AuditoriaBono.class)))
                .thenReturn(BonoImpresion.builder().build());

        ResumenPreparacionBonos resumen = useCase.preparar(7, 2026, 9, "A", 999L, "10.0.0.5");

        assertThat(resumen.total()).isEqualTo(2);
        assertThat(resumen.preparados()).isEqualTo(2);
        assertThat(resumen.fallidos()).isEmpty();

        ArgumentCaptor<AuditoriaBono> captor = ArgumentCaptor.forClass(AuditoriaBono.class);
        verify(printPrepareBonoUseCase, times(2)).printPrepare(captor.capture());
        assertThat(captor.getAllValues())
                .extracting(AuditoriaBono::getLegajoId)
                .containsExactly(1001L, 1002L);
        assertThat(captor.getAllValues().get(0).getIpAddress()).isEqualTo("10.0.0.5");
        assertThat(captor.getAllValues().get(0).getLegajoIdSolicitud()).isEqualTo(999L);
    }

    @Test
    void unLegajoQueFallaNoCortaElLoteYQuedaEnElResumen() {
        when(liquidacionQueryPort.findLegajoIdsByDependencia(7, 2026, 9, "A"))
                .thenReturn(List.of(1001L, 1002L, 1003L));
        when(printPrepareBonoUseCase.printPrepare(any(AuditoriaBono.class)))
                .thenAnswer(invocation -> {
                    AuditoriaBono auditoria = invocation.getArgument(0);
                    if (auditoria.getLegajoId().equals(1002L)) {
                        throw new BonoException("integridad fallida",
                                List.of(FaltanteBono.ITEM));
                    }
                    return BonoImpresion.builder().build();
                });

        ResumenPreparacionBonos resumen = useCase.preparar(7, 2026, 9, "A", null, "10.0.0.5");

        assertThat(resumen.total()).isEqualTo(3);
        assertThat(resumen.preparados()).isEqualTo(2);
        assertThat(resumen.fallidos()).hasSize(1);
        assertThat(resumen.fallidos().get(0).legajoId()).isEqualTo(1002L);
        assertThat(resumen.fallidos().get(0).motivo()).contains("integridad");
    }

    @Test
    void sinLiquidacionesDevuelveResumenVacio() {
        when(liquidacionQueryPort.findLegajoIdsByDependencia(7, 2026, 9, "D"))
                .thenReturn(List.of());

        ResumenPreparacionBonos resumen = useCase.preparar(7, 2026, 9, "D", null, "");

        assertThat(resumen.total()).isZero();
        assertThat(resumen.preparados()).isZero();
        assertThat(resumen.fallidos()).isEmpty();
    }
}
