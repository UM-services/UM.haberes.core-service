package um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.exception.LegajoBancoException;
import um.haberes.core.hexagonal.liquidaciones.item.application.service.ItemService;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.application.service.LiquidacionService;
import um.haberes.core.hexagonal.liquidaciones.novedad.application.service.NovedadService;
import um.haberes.core.model.LegajoBancoEntity;
import um.haberes.core.service.LegajoBancoService;

@ExtendWith(MockitoExtension.class)
class PrepararAcreditacionesUseCaseImplTest {

    private static final Long LEGAJO_ID = 77L;

    @Mock
    private LegajoBancoService legajoBancoService;

    @Mock
    private LiquidacionService liquidacionService;

    @Mock
    private ItemService itemService;

    @Mock
    private NovedadService novedadService;

    @InjectMocks
    private PrepararAcreditacionesUseCaseImpl prepararAcreditacionesUseCase;

    @Test
    void copiaLosCbusDelUltimoPeriodoCuandoNoHayFilas() {
        LegajoBancoEntity ultimo = fila(1L, 2026, 10, "0720000000000000000001", new BigDecimal("500"), null,
                (byte) 1);
        when(legajoBancoService.findAllByLegajoPeriodo(LEGAJO_ID, 2026, 11)).thenReturn(List.of());
        when(legajoBancoService.findLastByLegajoId(LEGAJO_ID)).thenReturn(ultimo);
        when(legajoBancoService.findAllByLegajoPeriodo(LEGAJO_ID, 2026, 10)).thenReturn(List.of(ultimo));
        LegajoBancoEntity copia = fila(2L, 2026, 11, "0720000000000000000001", new BigDecimal("500"), null,
                (byte) 1);
        when(legajoBancoService.findAllByLegajoPeriodo(LEGAJO_ID, 2026, 11)).thenReturn(List.of(), List.of(copia));

        List<LegajoBancoEntity> filas = prepararAcreditacionesUseCase.verificarCbu(LEGAJO_ID, 2026, 11);

        assertThat(filas).hasSize(1);
        verify(legajoBancoService).add(any(LegajoBancoEntity.class));
    }

    @Test
    void noCopiaNadaSiElLegajoNoTieneHistorial() {
        when(legajoBancoService.findAllByLegajoPeriodo(LEGAJO_ID, 2026, 11)).thenReturn(List.of());
        when(legajoBancoService.findLastByLegajoId(LEGAJO_ID)).thenThrow(new LegajoBancoException(LEGAJO_ID));

        assertThat(prepararAcreditacionesUseCase.verificarCbu(LEGAJO_ID, 2026, 11)).isEmpty();
        verify(legajoBancoService, never()).add(any(LegajoBancoEntity.class));
    }

    @Test
    void reparteFijoPorcentajeYResto() {
        LegajoBancoEntity fijo = fila(1L, 2026, 11, "0720000000000000000001", new BigDecimal("100"), null,
                (byte) 0);
        LegajoBancoEntity porcentaje = fila(2L, 2026, 11, "0720000000000000000002", null, new BigDecimal("50"),
                (byte) 0);
        LegajoBancoEntity resto = fila(3L, 2026, 11, "0720000000000000000003", null, null, (byte) 1);

        prepararAcreditacionesUseCase.calcularYGuardar(List.of(fijo, porcentaje, resto), new BigDecimal("1000.00"));

        assertThat(fijo.getAcreditado()).isEqualByComparingTo("100.00");
        assertThat(porcentaje.getAcreditado()).isEqualByComparingTo("500.00");
        assertThat(resto.getAcreditado()).isEqualByComparingTo("400.00");
        verify(legajoBancoService, times(3)).update(any(LegajoBancoEntity.class), any(Long.class));
    }

    @Test
    void elFijoSoloTomaLoQueQuedaDelImporte() {
        LegajoBancoEntity fijo = fila(1L, 2026, 11, "0720000000000000000001", new BigDecimal("1500"), null,
                (byte) 1);

        prepararAcreditacionesUseCase.calcularYGuardar(List.of(fijo), new BigDecimal("1000.00"));

        assertThat(fijo.getAcreditado()).isEqualByComparingTo("1000.00");
        verify(legajoBancoService).update(eq(fijo), eq(1L));
    }

    private static LegajoBancoEntity fila(Long id, int anho, int mes, String cbu, BigDecimal fijo,
            BigDecimal porcentaje, Byte resto) {
        return new LegajoBancoEntity(id, LEGAJO_ID, anho, mes, cbu, fijo, porcentaje, resto, BigDecimal.ZERO, null,
                null);
    }
}
