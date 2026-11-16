package um.haberes.core.hexagonal.liquidaciones.general.application.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.CargoLiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.model.CargoLiquidacionView;
import um.haberes.core.hexagonal.crossslice.legajo_control.LegajoControlCrossSlice;
import um.haberes.core.hexagonal.crossslice.legajo_control.model.LegajoControlView;
import um.haberes.core.hexagonal.crossslice.liquidacion.LiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;
import um.haberes.core.hexagonal.crossslice.novedad.NovedadCrossSlice;
import um.haberes.core.hexagonal.crossslice.persona.PersonaCrossSlice;
import um.haberes.core.hexagonal.crossslice.persona.model.PersonaView;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.PadronLiquidacionGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.ports.out.ProcesoGeneralRepository;
import um.haberes.core.hexagonal.liquidaciones.reglas.ReglasElegibilidadLiquidacion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LiquidacionGeneralServiceTest {

    private static final Integer ANHO = 2026;

    private static final Integer MES = 11;

    private static final Long LEGAJO_PEREZ = 1001L;

    private static final Long LEGAJO_GOMEZ = 1002L;

    @Mock
    private PersonaCrossSlice personaCrossSlice;

    @Mock
    private CargoLiquidacionCrossSlice cargoLiquidacionCrossSlice;

    @Mock
    private LiquidacionCrossSlice liquidacionCrossSlice;

    @Mock
    private NovedadCrossSlice novedadCrossSlice;

    @Mock
    private LegajoControlCrossSlice legajoControlCrossSlice;

    @Mock
    private ReglasElegibilidadLiquidacion reglas;

    @Mock
    private ProcesoGeneralRepository procesoGeneralRepository;

    @Mock
    private ProcesoGeneralRunner procesoGeneralRunner;

    @InjectMocks
    private LiquidacionGeneralService service;

    @Test
    void padronAgregaPersonasCargosNovedadesLiquidacionesYControl() {
        when(personaCrossSlice.liquidables()).thenReturn(List.of(
                persona(LEGAJO_PEREZ, "PEREZ", "JUAN", 1, "S"),
                persona(LEGAJO_GOMEZ, "GOMEZ", "ANA", 9, "N")));
        when(cargoLiquidacionCrossSlice.porPeriodo(ANHO, MES)).thenReturn(List.of(
                cargo(LEGAJO_PEREZ, true), cargo(LEGAJO_PEREZ, false)));
        when(liquidacionCrossSlice.porPeriodo(ANHO, MES)).thenReturn(List.of(
                liquidacion(LEGAJO_PEREZ, (byte) 0, new BigDecimal("100.00")),
                liquidacion(LEGAJO_GOMEZ, (byte) 1, new BigDecimal("-50.00"))));
        when(legajoControlCrossSlice.porPeriodo(ANHO, MES)).thenReturn(List.of(
                new LegajoControlView(1L, LEGAJO_PEREZ, ANHO, MES, (byte) 1, (byte) 0, (byte) 0)));
        when(novedadCrossSlice.legajosConNovedades(ANHO, MES)).thenReturn(Set.of(LEGAJO_PEREZ));
        when(reglas.hayControl(ANHO, MES)).thenReturn(true);

        PadronLiquidacionGeneral padron = service.obtener(ANHO, MES);

        assertThat(padron.filas()).hasSize(2);
        assertThat(padron.filas().get(0).apellido()).isEqualTo("GOMEZ");
        assertThat(padron.filas().get(0).orden()).isEqualTo(1);
        assertThat(padron.filas().get(1).orden()).isEqualTo(2);

        var perez = padron.filas().get(1);
        assertThat(perez.esDocente()).isTrue();
        assertThat(perez.esNoDocente()).isTrue();
        assertThat(perez.tieneNovedades()).isTrue();
        assertThat(perez.liquidado()).isTrue();
        assertThat(perez.bloqueado()).isFalse();

        var gomez = padron.filas().get(0);
        assertThat(gomez.bloqueado()).isTrue();
        assertThat(gomez.liquidado()).isFalse();
        assertThat(gomez.tieneNovedades()).isFalse();

        assertThat(padron.resumen().total()).isEqualTo(2);
        assertThat(padron.resumen().docentes()).isEqualTo(1);
        assertThat(padron.resumen().noDocentes()).isEqualTo(1);
        assertThat(padron.resumen().conNovedades()).isEqualTo(1);
        assertThat(padron.resumen().liquidados()).isEqualTo(1);
        assertThat(padron.resumen().bloqueados()).isEqualTo(1);
        assertThat(padron.resumen().netoNegativo()).isEqualTo(1);
        assertThat(padron.resumen().netoCero()).isZero();
        assertThat(padron.resumen().totalNeto()).isEqualByComparingTo("50.00");

        assertThat(padron.acciones().hayControl()).isTrue();
        assertThat(padron.acciones().puedeLiquidar()).isTrue();
        assertThat(padron.acciones().puedeEliminar()).isTrue();
    }

    @Test
    void resumenSoloCuentaNovedadesYNetoCeroDeLegajosDelPadron() {
        when(personaCrossSlice.liquidables()).thenReturn(List.of(persona(LEGAJO_PEREZ, "PEREZ", "JUAN", 1, "S")));
        when(cargoLiquidacionCrossSlice.porPeriodo(ANHO, MES)).thenReturn(List.of());
        when(liquidacionCrossSlice.porPeriodo(ANHO, MES)).thenReturn(List.of());
        when(legajoControlCrossSlice.porPeriodo(ANHO, MES)).thenReturn(List.of());
        when(novedadCrossSlice.legajosConNovedades(ANHO, MES)).thenReturn(Set.of(LEGAJO_PEREZ, 9999L));
        when(reglas.hayControl(ANHO, MES)).thenReturn(true);

        PadronLiquidacionGeneral padron = service.obtener(ANHO, MES);

        assertThat(padron.resumen().conNovedades()).isEqualTo(1);
        assertThat(padron.resumen().netoCero()).isZero();
        assertThat(padron.filas().get(0).totalNeto()).isEqualByComparingTo("0");
    }

    @Test
    void sinControlNoHabilitaGenerarNiLiquidar() {
        when(personaCrossSlice.liquidables()).thenReturn(List.of(persona(LEGAJO_PEREZ, "PEREZ", "JUAN", 1, "S")));
        when(reglas.hayControl(ANHO, MES)).thenReturn(false);

        PadronLiquidacionGeneral padron = service.obtener(ANHO, MES);

        assertThat(padron.acciones().hayControl()).isFalse();
        assertThat(padron.acciones().puedeGenerarCargos()).isFalse();
        assertThat(padron.acciones().puedeLiquidar()).isFalse();
        assertThat(padron.acciones().puedeEliminar()).isTrue();
    }

    @Test
    void noIniciaProcesoSiElPeriodoNoTieneLegajosLiquidables() {
        when(reglas.hayControl(ANHO, MES)).thenReturn(true);
        when(personaCrossSlice.liquidables()).thenReturn(List.of());

        var inicio = service.iniciar(ANHO, MES, 99L);

        assertThat(inicio.iniciadoProceso()).isFalse();
        assertThat(inicio.motivo()).isEqualTo(LiquidacionGeneralService.SIN_LIQUIDABLES);
    }

    @Test
    void noIniciaProcesoSiYaHayUnoEnCurso() {
        when(procesoGeneralRepository.existsEnCurso(ANHO, MES)).thenReturn(true);

        var inicio = service.eliminar(ANHO, MES, true, 99L);

        assertThat(inicio.iniciadoProceso()).isFalse();
        assertThat(inicio.motivo()).isEqualTo(LiquidacionGeneralService.PROCESO_EN_CURSO);
    }

    private PersonaView persona(Long legajoId, String apellido, String nombre, Integer estado, String liquida) {
        return new PersonaView(legajoId, new BigDecimal("12345678"), apellido, nombre, estado, liquida, 1, "S");
    }

    private CargoLiquidacionView cargo(Long legajoId, boolean docente) {
        return new CargoLiquidacionView(1L, legajoId, 1, "FING", OffsetDateTime.now(), OffsetDateTime.now(),
                1, "PROFESOR", BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.TEN, 1, 0, "A", docente);
    }

    private LiquidacionView liquidacion(Long legajoId, Byte bloqueado, BigDecimal neto) {
        return new LiquidacionView(1L, legajoId, ANHO, MES, bloqueado, 1, "S", neto, BigDecimal.ZERO,
                BigDecimal.ZERO, neto, OffsetDateTime.now(), null, 1, "S");
    }
}
