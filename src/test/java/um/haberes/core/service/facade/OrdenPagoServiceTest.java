package um.haberes.core.service.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.application.exception.AcreditacionPagoException;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.application.service.AcreditacionPagoService;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.model.AcreditacionPago;
import um.haberes.core.model.extern.EjercicioDto;
import um.haberes.core.model.extern.ProveedorMovimientoDto;
import um.haberes.core.model.internal.OrdenPagoRequest;
import um.haberes.core.service.extern.EjercicioService;
import um.haberes.core.service.extern.ProveedorMovimientoService;

@ExtendWith(MockitoExtension.class)
class OrdenPagoServiceTest {

    private static final OffsetDateTime FECHA = OffsetDateTime.of(2026, 11, 30, 0, 0, 0, 0, ZoneOffset.UTC);

    @Mock
    private EjercicioService ejercicioService;

    @Mock
    private AcreditacionPagoService acreditacionPagoService;

    @Mock
    private ProveedorMovimientoService proveedorMovimientoService;

    @InjectMocks
    private OrdenPagoService ordenPagoService;

    private OrdenPagoRequest request;

    @BeforeEach
    void setUp() {
        request = new OrdenPagoRequest(2026, 11, FECHA, new BigDecimal("1000.00"), BigDecimal.ZERO, false);
    }

    @Test
    void creaElComprobanteYLaAcreditacionDePagoCuandoNoExisten() {
        when(ejercicioService.findByFecha(FECHA)).thenReturn(new EjercicioDto(2026, "2026", null, null,
                (byte) 0, null, null));
        when(acreditacionPagoService.findByUnique(2026, 11, FECHA))
                .thenThrow(new AcreditacionPagoException(2026, 11, FECHA));
        when(proveedorMovimientoService.findLastOrdenPago(2026))
                .thenReturn(new ProveedorMovimientoDto(null, null, "", 6, null, null, 2026, 5L, null, null, null,
                        null, null, null, null, null, null, null, null, null, null));
        when(proveedorMovimientoService.add(any())).thenAnswer(invocacion -> {
            ProveedorMovimientoDto dto = invocacion.getArgument(0);
            dto.setProveedorMovimientoId(99L);
            dto.setComprobanteId(6);
            dto.setPrefijo(2026);
            dto.setNumeroComprobante(6L);
            return dto;
        });
        when(acreditacionPagoService.add(any())).thenAnswer(invocacion -> invocacion.getArgument(0));

        assertThat(ordenPagoService.generateOrdenPago(request)).isTrue();

        ArgumentCaptor<ProveedorMovimientoDto> movimiento = ArgumentCaptor.forClass(ProveedorMovimientoDto.class);
        verify(proveedorMovimientoService).add(movimiento.capture());
        assertThat(movimiento.getValue().getNumeroComprobante()).isEqualTo(6L);
        assertThat(movimiento.getValue().getComprobanteId()).isEqualTo(6);
        assertThat(movimiento.getValue().getConcepto()).isEqualTo("Sueldos mes 11/2026");
        assertThat(movimiento.getValue().getNombreBeneficiario()).isEqualTo("Personal Administrativo y Docente");

        ArgumentCaptor<AcreditacionPago> pago = ArgumentCaptor.forClass(AcreditacionPago.class);
        verify(acreditacionPagoService).add(pago.capture());
        assertThat(pago.getValue().getTotalSantander()).isEqualByComparingTo("1000.00");
        assertThat(pago.getValue().getTotalOtrosBancos()).isEqualByComparingTo("0");
        assertThat(pago.getValue().getNumeroComprobantePago()).isEqualTo(6L);
        verify(acreditacionPagoService, never()).update(any(), any());
    }

    @Test
    void reutilizaLaOrdenExistenteYAcumulaLosTotales() {
        AcreditacionPago existente = AcreditacionPago.builder()
                .acreditacionPagoId(7L)
                .anho(2026)
                .mes(11)
                .fechaPago(FECHA)
                .totalSantander(new BigDecimal("1000.00"))
                .totalOtrosBancos(BigDecimal.ZERO)
                .comprobanteIdPago(6)
                .puntoVentaPago(2026)
                .numeroComprobantePago(3L)
                .build();
        when(ejercicioService.findByFecha(FECHA)).thenReturn(new EjercicioDto(2026, "2026", null, null,
                (byte) 0, null, null));
        when(acreditacionPagoService.findByUnique(2026, 11, FECHA)).thenReturn(existente);
        when(proveedorMovimientoService.findByOrdenPago(2026, 3L))
                .thenReturn(new ProveedorMovimientoDto(88L, null, "", 6, null, null, 2026, 3L, null, null, null,
                        null, null, null, null, null, null, null, null, null, null));
        request.setTotalSantander(BigDecimal.ZERO);
        request.setTotalOtrosBancos(new BigDecimal("250.50"));

        assertThat(ordenPagoService.generateOrdenPago(request)).isTrue();

        ArgumentCaptor<AcreditacionPago> pago = ArgumentCaptor.forClass(AcreditacionPago.class);
        verify(acreditacionPagoService).update(pago.capture(), eq(7L));
        assertThat(pago.getValue().getTotalSantander()).isEqualByComparingTo("1000.00");
        assertThat(pago.getValue().getTotalOtrosBancos()).isEqualByComparingTo("250.50");
        assertThat(pago.getValue().getNumeroComprobantePago()).isEqualTo(3L);
        verify(acreditacionPagoService, never()).add(any());
        verify(proveedorMovimientoService, never()).add(any());
    }

    @Test
    void elIndividualNoGeneraOrdenDePago() {
        request.setIndividual(true);

        assertThat(ordenPagoService.generateOrdenPago(request)).isTrue();

        verify(ejercicioService, never()).findByFecha(any());
    }

    @Test
    void propagaElErrorSiTesereriaNoResponde() {
        when(ejercicioService.findByFecha(FECHA)).thenReturn(new EjercicioDto(2026, "2026", null, null,
                (byte) 0, null, null));
        when(acreditacionPagoService.findByUnique(2026, 11, FECHA))
                .thenThrow(new AcreditacionPagoException(2026, 11, FECHA));
        when(proveedorMovimientoService.findLastOrdenPago(2026)).thenReturn(null);
        when(proveedorMovimientoService.add(any())).thenThrow(new IllegalStateException("tesorería caída"));

        assertThatThrownBy(() -> ordenPagoService.generateOrdenPago(request))
                .isInstanceOf(IllegalStateException.class);
    }
}
