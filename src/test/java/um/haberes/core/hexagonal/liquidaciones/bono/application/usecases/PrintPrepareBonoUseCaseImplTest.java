package um.haberes.core.hexagonal.liquidaciones.bono.application.usecases;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.liquidaciones.bono.application.exception.BonoException;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.Actividad;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.AuditoriaBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.BonoImpresion;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.FaltanteBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.IntegridadBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.PeriodoBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.PrepararBonoUseCase;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.RegistrarAuditoriaBonoUseCase;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.VerificarIntegridadBonoUseCase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrintPrepareBonoUseCaseImplTest {

    @Mock
    private VerificarIntegridadBonoUseCase verificarIntegridadBonoUseCase;

    @Mock
    private PrepararBonoUseCase prepararBonoUseCase;

    @Mock
    private RegistrarAuditoriaBonoUseCase registrarAuditoriaBonoUseCase;

    @InjectMocks
    private PrintPrepareBonoUseCaseImpl useCase;

    private AuditoriaBono auditoria() {
        return AuditoriaBono.builder()
                .legajoId(12345L).anho(2026).mes(8).legajoIdSolicitud(999L).ipAddress("10.0.0.1")
                .build();
    }

    @Test
    void printPrepareRechazaConFaltantes() {
        when(verificarIntegridadBonoUseCase.verificarIntegridad(any(PeriodoBono.class)))
                .thenReturn(IntegridadBono.builder()
                        .legajoId(12345L).anho(2026).mes(8).ok(false)
                        .faltantes(List.of(FaltanteBono.ITEM, FaltanteBono.LEGAJO_CONTROL))
                        .build());

        assertThatThrownBy(() -> useCase.printPrepare(auditoria()))
                .isInstanceOfSatisfying(BonoException.class, e -> {
                    assertThat(e.getFaltantes())
                            .containsExactly(FaltanteBono.ITEM, FaltanteBono.LEGAJO_CONTROL);
                });
        verify(prepararBonoUseCase, never()).preparar(any());
        verify(registrarAuditoriaBonoUseCase, never()).registrarAuditoria(any());
    }

    @Test
    void printPrepareComponeIntegridadPreparacionYAuditoria() {
        when(verificarIntegridadBonoUseCase.verificarIntegridad(any(PeriodoBono.class)))
                .thenReturn(IntegridadBono.builder()
                        .legajoId(12345L).anho(2026).mes(8).ok(true).faltantes(List.of())
                        .build());
        when(prepararBonoUseCase.preparar(any(PeriodoBono.class))).thenReturn(Actividad.builder().actividadId(1L).build());
        BonoImpresion impresion = BonoImpresion.builder().bonoImpresionId(77L).build();
        when(registrarAuditoriaBonoUseCase.registrarAuditoria(any(AuditoriaBono.class))).thenReturn(impresion);

        BonoImpresion result = useCase.printPrepare(auditoria());

        assertThat(result.getBonoImpresionId()).isEqualTo(77L);
        verify(prepararBonoUseCase).preparar(any(PeriodoBono.class));
        verify(registrarAuditoriaBonoUseCase).registrarAuditoria(any(AuditoriaBono.class));
    }
}
