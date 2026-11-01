package um.haberes.core.hexagonal.liquidaciones.bono.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.liquidaciones.bono.application.exception.BonoException;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.AuditoriaBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.BonoImpresion;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.BonoImpresionRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrarAuditoriaBonoUseCaseImplTest {

    @Mock
    private BonoImpresionRepository bonoImpresionRepository;

    @InjectMocks
    private RegistrarAuditoriaBonoUseCaseImpl useCase;

    @Test
    void registrarAuditoriaSinSolicitanteLanzaExcepcion() {
        AuditoriaBono auditoria = AuditoriaBono.builder()
                .legajoId(12345L).anho(2026).mes(8).ipAddress("10.0.0.1")
                .build();

        assertThatThrownBy(() -> useCase.registrarAuditoria(auditoria))
                .isInstanceOf(BonoException.class)
                .hasMessageContaining("legajoIdSolicitud");
        verify(bonoImpresionRepository, never()).save(any());
    }

    @Test
    void registrarAuditoriaPersisteConIpYSolicitante() {
        when(bonoImpresionRepository.save(any(BonoImpresion.class))).thenAnswer(inv -> inv.getArgument(0));

        BonoImpresion result = useCase.registrarAuditoria(AuditoriaBono.builder()
                .legajoId(12345L).anho(2026).mes(8).legajoIdSolicitud(999L).ipAddress("10.0.0.1")
                .build());

        ArgumentCaptor<BonoImpresion> captor = ArgumentCaptor.forClass(BonoImpresion.class);
        verify(bonoImpresionRepository).save(captor.capture());
        BonoImpresion saved = captor.getValue();
        assertThat(saved.getBonoImpresionId()).isNull();
        assertThat(saved.getFecha()).isNull();
        assertThat(saved.getLegajoId()).isEqualTo(12345L);
        assertThat(saved.getAnho()).isEqualTo(2026);
        assertThat(saved.getMes()).isEqualTo(8);
        assertThat(saved.getLegajoIdSolicitud()).isEqualTo(999L);
        assertThat(saved.getIpAddress()).isEqualTo("10.0.0.1");
        assertThat(result).isSameAs(saved);
    }
}
