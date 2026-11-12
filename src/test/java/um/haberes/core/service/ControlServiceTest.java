package um.haberes.core.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import um.haberes.core.exception.ControlOrigenException;
import um.haberes.core.exception.ControlYaExisteException;
import um.haberes.core.model.ControlEntity;
import um.haberes.core.repository.JpaControlRepository;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ControlServiceTest {

    @Mock
    private JpaControlRepository repository;

    @InjectMocks
    private ControlService service;

    @Test
    void findByPeriodoOpcional_returnsControlWhenPeriodoExists() {
        ControlEntity control = origenCompleto();
        when(repository.findByAnhoAndMes(2026, 11)).thenReturn(Optional.of(control));

        assertThat(service.findByPeriodoOpcional(2026, 11)).contains(control);
    }

    @Test
    void findByPeriodoOpcional_returnsEmptyWhenPeriodoIsNew() {
        when(repository.findByAnhoAndMes(2026, 11)).thenReturn(Optional.empty());

        assertThat(service.findByPeriodoOpcional(2026, 11)).isEmpty();
    }

    @Test
    void copiarPeriodo_copiesEveryFieldAndSetsTargetPeriod() {
        ControlEntity origen = origenCompleto();
        when(repository.findByAnhoAndMes(2026, 11)).thenReturn(Optional.empty());
        when(repository.findByAnhoAndMes(2026, 10)).thenReturn(Optional.of(origen));
        when(repository.save(any(ControlEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ControlEntity copia = service.copiarPeriodo(2026, 11);

        assertThat(copia.getControlId()).isNull();
        assertThat(copia.getAnho()).isEqualTo(2026);
        assertThat(copia.getMes()).isEqualTo(11);
        assertThat(copia.getModoLiquidacion()).isNull();
        assertThatEveryFieldWasCopied(origen, copia);
        verify(repository).save(any(ControlEntity.class));
    }

    @Test
    void copiarPeriodo_whenTargetPeriodExists_throwsExceptionWithoutSaving() {
        when(repository.findByAnhoAndMes(2026, 11)).thenReturn(Optional.of(new ControlEntity()));

        assertThatThrownBy(() -> service.copiarPeriodo(2026, 11))
                .isInstanceOf(ControlYaExisteException.class)
                .hasMessageContaining("2026/11");

        verify(repository, never()).save(any(ControlEntity.class));
    }

    @Test
    void copiarPeriodo_whenPreviousPeriodHasNoData_throwsExceptionWithoutSaving() {
        when(repository.findByAnhoAndMes(2026, 11)).thenReturn(Optional.empty());
        when(repository.findByAnhoAndMes(2026, 10)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.copiarPeriodo(2026, 11))
                .isInstanceOf(ControlOrigenException.class)
                .hasMessageContaining("2026/10");

        verify(repository, never()).save(any(ControlEntity.class));
    }

    @Test
    void copiarPeriodo_inJanuary_readsPreviousDecember() {
        ControlEntity origen = origenCompleto();
        when(repository.findByAnhoAndMes(2027, 1)).thenReturn(Optional.empty());
        when(repository.findByAnhoAndMes(2026, 12)).thenReturn(Optional.of(origen));
        when(repository.save(any(ControlEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ControlEntity copia = service.copiarPeriodo(2027, 1);

        assertThat(copia.getAnho()).isEqualTo(2027);
        assertThat(copia.getMes()).isEqualTo(1);
        verify(repository).findByAnhoAndMes(2026, 12);
    }

    /**
     * Origen con las cuatro fechas, los dos textos y un valor distinto en cada
     * BigDecimal, para que un campo olvidado o pisado no pase inadvertido.
     */
    private static ControlEntity origenCompleto() {
        ControlEntity control = new ControlEntity();
        control.setFechaDesde(OffsetDateTime.of(2026, 10, 1, 0, 0, 0, 0, ZoneOffset.UTC));
        control.setFechaHasta(OffsetDateTime.of(2026, 10, 31, 0, 0, 0, 0, ZoneOffset.UTC));
        control.setFechaPago(OffsetDateTime.of(2026, 10, 30, 0, 0, 0, 0, ZoneOffset.UTC));
        control.setFechaDeposito(OffsetDateTime.of(2026, 10, 29, 0, 0, 0, 0, ZoneOffset.UTC));
        control.setAporteJubilatorio("11");
        control.setDepositoBanco("BROU");
        control.setModoLiquidacionId(2);

        int indice = 1;
        for (Field field : ControlEntity.class.getDeclaredFields()) {
            if (field.getType() != BigDecimal.class) continue;
            try {
                field.setAccessible(true);
                field.set(control, BigDecimal.valueOf(indice++));
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }
        return control;
    }

    /**
     * Compara todos los campos copiables de la entidad por reflexión: si se agrega
     * un campo nuevo y no se copia, este test falla.
     */
    private static void assertThatEveryFieldWasCopied(ControlEntity origen, ControlEntity copia) {
        for (Field field : ControlEntity.class.getDeclaredFields()) {
            String nombre = field.getName();
            if (nombre.equals("controlId") || nombre.equals("modoLiquidacion")
                    || nombre.equals("anho") || nombre.equals("mes")) {
                continue;
            }
            try {
                field.setAccessible(true);
                assertThat(field.get(copia)).as(nombre).isEqualTo(field.get(origen));
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }
    }

}
