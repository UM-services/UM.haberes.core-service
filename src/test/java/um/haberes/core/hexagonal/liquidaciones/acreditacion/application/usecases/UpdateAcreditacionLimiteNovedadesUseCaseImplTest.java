package um.haberes.core.hexagonal.liquidaciones.acreditacion.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.ports.out.AcreditacionRepository;

@ExtendWith(MockitoExtension.class)
class UpdateAcreditacionLimiteNovedadesUseCaseImplTest {

    @Mock
    private AcreditacionRepository acreditacionRepository;

    @InjectMocks
    private UpdateAcreditacionLimiteNovedadesUseCaseImpl useCase;

    private static final OffsetDateTime LIMITE = OffsetDateTime.of(2026, 11, 30, 23, 59, 59, 0,
            ZoneOffset.ofHours(-3));

    @Test
    void devuelveElRegistroActualizadoCuandoExiste() {
        Acreditacion actualizada = Acreditacion.builder()
                .acreditacionId(145L)
                .anho(2026)
                .mes(11)
                .acreditado((byte) 0)
                .limiteNovedades(LIMITE)
                .build();
        when(acreditacionRepository.updateLimiteNovedades(145L, LIMITE))
                .thenReturn(Optional.of(actualizada));

        Optional<Acreditacion> resultado = useCase.updateLimiteNovedades(145L, LIMITE);

        assertTrue(resultado.isPresent());
        assertEquals(LIMITE, resultado.get().getLimiteNovedades());
        verify(acreditacionRepository).updateLimiteNovedades(145L, LIMITE);
        verifyNoMoreInteractions(acreditacionRepository);
    }

    @Test
    void devuelveVacioCuandoElRegistroNoExiste() {
        when(acreditacionRepository.updateLimiteNovedades(99L, LIMITE))
                .thenReturn(Optional.empty());

        Optional<Acreditacion> resultado = useCase.updateLimiteNovedades(99L, LIMITE);

        assertTrue(resultado.isEmpty());
        verify(acreditacionRepository).updateLimiteNovedades(99L, LIMITE);
        verifyNoMoreInteractions(acreditacionRepository);
    }
}
