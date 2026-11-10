package um.haberes.core.hexagonal.crossslice.novedad;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.crossslice.novedad.model.NovedadView;
import um.haberes.core.hexagonal.liquidaciones.novedad.application.service.NovedadService;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NovedadCrossSliceAdapterTest {

    @Mock
    private NovedadService novedadService;

    @InjectMocks
    private NovedadCrossSliceAdapter adapter;

    @Test
    void crearPersisteImportadoEnCeroPorqueLaColumnaEsObligatoria() {
        when(novedadService.add(any(Novedad.class))).thenAnswer(invocation -> invocation.getArgument(0));

        NovedadView view = adapter.crear(1001L, 2026, 11, 16, BigDecimal.ZERO, "N", "sin presentismo");

        ArgumentCaptor<Novedad> captor = ArgumentCaptor.forClass(Novedad.class);
        verify(novedadService).add(captor.capture());
        Novedad enviada = captor.getValue();
        assertThat(enviada.getImportado()).isEqualTo((byte) 0);
        assertThat(enviada.getLegajoId()).isEqualTo(1001L);
        assertThat(enviada.getCodigoId()).isEqualTo(16);
        assertThat(enviada.getValue()).isEqualTo("N");
        assertThat(view.value()).isEqualTo("N");
    }

    @Test
    void crearDevuelveLaVistaConElNombreDelCodigo() {
        Novedad guardada = Novedad.builder().novedadId(99L).legajoId(1001L).anho(2026).mes(11)
                .codigoId(16).importe(BigDecimal.ZERO).value("N").importado((byte) 0).build();
        Novedad completa = Novedad.builder().novedadId(99L).legajoId(1001L).anho(2026).mes(11)
                .codigoId(16).importe(BigDecimal.ZERO).value("N").importado((byte) 0)
                .codigo(um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo.builder()
                        .codigoId(16).nombre("PRESENTISMO").build())
                .build();
        when(novedadService.add(any(Novedad.class))).thenReturn(guardada);
        when(novedadService.findByNovedadId(99L)).thenReturn(completa);

        NovedadView view = adapter.crear(1001L, 2026, 11, 16, BigDecimal.ZERO, "N", "sin presentismo");

        assertThat(view.novedadId()).isEqualTo(99L);
        assertThat(view.codigoNombre()).isEqualTo("PRESENTISMO");
    }

    @Test
    void actualizarConservaClaveYOrigenDeLaNovedadExistente() {
        Novedad existente = Novedad.builder()
                .novedadId(77L)
                .legajoId(1001L)
                .anho(2026)
                .mes(11)
                .codigoId(16)
                .importe(BigDecimal.ZERO)
                .value("N")
                .importado((byte) 1)
                .novedadUploadId(9L)
                .build();
        when(novedadService.findByNovedadId(77L)).thenReturn(existente);
        when(novedadService.update(any(Novedad.class), any())).thenAnswer(invocation -> invocation.getArgument(0));

        adapter.actualizar(77L, new BigDecimal("1500.50"), "", "obs");

        ArgumentCaptor<Novedad> captor = ArgumentCaptor.forClass(Novedad.class);
        verify(novedadService).update(captor.capture(), any());
        Novedad enviada = captor.getValue();
        assertThat(enviada.getNovedadId()).isEqualTo(77L);
        assertThat(enviada.getLegajoId()).isEqualTo(1001L);
        assertThat(enviada.getCodigoId()).isEqualTo(16);
        assertThat(enviada.getImporte()).isEqualByComparingTo("1500.50");
        assertThat(enviada.getImportado()).isEqualTo((byte) 1);
        assertThat(enviada.getNovedadUploadId()).isEqualTo(9L);
    }
}
