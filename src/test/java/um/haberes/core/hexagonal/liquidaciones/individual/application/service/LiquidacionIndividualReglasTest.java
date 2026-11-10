package um.haberes.core.hexagonal.liquidaciones.individual.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.CargoLiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.cargos_clase.CargoClaseCrossSlice;
import um.haberes.core.hexagonal.crossslice.control.ControlCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.LiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;
import um.haberes.core.hexagonal.crossslice.novedad.NovedadCrossSlice;
import um.haberes.core.hexagonal.crossslice.novedad.model.NovedadView;
import um.haberes.core.hexagonal.crossslice.persona.PersonaCrossSlice;
import um.haberes.core.hexagonal.crossslice.persona.model.PersonaView;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.AccionesLiquidacionIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.EstadoProcesoIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResultadoProcesoIndividual;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LiquidacionIndividualReglasTest {

    private static final Long LEGAJO = 1001L;

    private static final Integer ANHO = 2026;

    private static final Integer MES = 11;

    @Mock
    private LiquidacionCrossSlice liquidacionCrossSlice;

    @Mock
    private ControlCrossSlice controlCrossSlice;

    @Mock
    private PersonaCrossSlice personaCrossSlice;

    @Mock
    private CargoLiquidacionCrossSlice cargoLiquidacionCrossSlice;

    @Mock
    private CargoClaseCrossSlice cargoClaseCrossSlice;

    @Mock
    private NovedadCrossSlice novedadCrossSlice;

    @InjectMocks
    private LiquidacionIndividualReglas reglas;

    @Test
    void accionesSinLiquidacionNiControlNoHabilitanProcesos() {
        AccionesLiquidacionIndividual acciones = reglas.acciones(Optional.empty(), false);

        assertThat(acciones.puedeGenerarCargos()).isFalse();
        assertThat(acciones.puedeLiquidar()).isFalse();
        assertThat(acciones.puedeEliminarLiquidacion()).isFalse();
        assertThat(acciones.puedeEditarNovedades()).isTrue();
        assertThat(acciones.bloqueado()).isFalse();
        assertThat(acciones.motivoBloqueo()).isNull();
    }

    @Test
    void accionesConControlHabilitanGenerarYLiquidar() {
        AccionesLiquidacionIndividual acciones = reglas.acciones(Optional.empty(), true);

        assertThat(acciones.puedeGenerarCargos()).isTrue();
        assertThat(acciones.puedeLiquidar()).isTrue();
        assertThat(acciones.puedeEliminarLiquidacion()).isFalse();
    }

    @Test
    void accionesConLiquidacionBloqueadaInformanMotivo() {
        AccionesLiquidacionIndividual acciones = reglas.acciones(Optional.of(liquidacion((byte) 1, 1, "S")), true);

        assertThat(acciones.bloqueado()).isTrue();
        assertThat(acciones.puedeGenerarCargos()).isFalse();
        assertThat(acciones.motivoBloqueo()).contains("bloqueada");
    }

    @Test
    void bloqueoRechazaSinForzarYAceptaConForzar() {
        Optional<LiquidacionView> bloqueada = Optional.of(liquidacion((byte) 1, 1, "S"));

        ResultadoProcesoIndividual sinForzar = reglas.bloquearSiCorresponde(bloqueada, false).orElseThrow();
        assertThat(sinForzar.resultado()).isEqualTo(EstadoProcesoIndividual.BLOQUEADA);
        assertThat(reglas.bloquearSiCorresponde(bloqueada, true)).isEmpty();
        assertThat(reglas.bloquearSiCorresponde(Optional.of(liquidacion((byte) 0, 1, "S")), false)).isEmpty();
    }

    @Test
    void personaInexistenteNoEsLiquidable() {
        when(personaCrossSlice.porLegajo(LEGAJO)).thenReturn(Optional.empty());

        ResultadoProcesoIndividual resultado = reglas.validarPersona(LEGAJO, ANHO, MES).orElseThrow();

        assertThat(resultado.resultado()).isEqualTo(EstadoProcesoIndividual.PERSONA_INEXISTENTE);
        verify(liquidacionCrossSlice, never()).porPeriodoAnterior(LEGAJO, ANHO, MES);
    }

    @Test
    void personaActivaYLiquidaEsLiquidable() {
        when(personaCrossSlice.porLegajo(LEGAJO)).thenReturn(Optional.of(persona(1, "S")));

        assertThat(reglas.validarPersona(LEGAJO, ANHO, MES)).isEmpty();
    }

    @Test
    void personaEnBajaConLiquidacionAnteriorActivaNoEsLiquidable() {
        when(personaCrossSlice.porLegajo(LEGAJO)).thenReturn(Optional.of(persona(9, "N")));
        when(liquidacionCrossSlice.porPeriodoAnterior(LEGAJO, ANHO, MES))
                .thenReturn(Optional.of(liquidacion((byte) 0, 1, "S")));

        ResultadoProcesoIndividual resultado = reglas.validarPersona(LEGAJO, ANHO, MES).orElseThrow();

        assertThat(resultado.resultado()).isEqualTo(EstadoProcesoIndividual.SIN_LIQUIDACION_ANTERIOR);
    }

    @Test
    void personaNoLiquidableConAnteriorEnBajaCorrigeElDato() {
        when(personaCrossSlice.porLegajo(LEGAJO)).thenReturn(Optional.of(persona(9, "S")));
        when(liquidacionCrossSlice.porPeriodoAnterior(LEGAJO, ANHO, MES))
                .thenReturn(Optional.of(liquidacion((byte) 0, 9, "S")));

        ResultadoProcesoIndividual resultado = reglas.validarPersona(LEGAJO, ANHO, MES).orElseThrow();

        assertThat(resultado.resultado()).isEqualTo(EstadoProcesoIndividual.PERSONA_NO_LIQUIDABLE);
        verify(personaCrossSlice).marcarNoLiquida(LEGAJO);
    }

    @Test
    void periodoConNovedadEspecialEsLiquidableSinConsultarCargos() {
        boolean liquidable = reglas.periodoLiquidable(LEGAJO, ANHO, MES,
                List.of(novedad(3)));

        assertThat(liquidable).isTrue();
        verify(cargoLiquidacionCrossSlice, never()).tieneCargosActivos(LEGAJO, ANHO, MES);
    }

    @Test
    void periodoSinCargosNiNovedadesEspecialesNoEsLiquidable() {
        when(cargoLiquidacionCrossSlice.tieneCargosActivos(LEGAJO, ANHO, MES)).thenReturn(false);
        when(cargoClaseCrossSlice.tieneCargos(LEGAJO, ANHO, MES)).thenReturn(false);

        assertThat(reglas.periodoLiquidable(LEGAJO, ANHO, MES, List.of(novedad(16)))).isFalse();
    }

    @Test
    void periodoConCargosConClaseEsLiquidable() {
        when(cargoLiquidacionCrossSlice.tieneCargosActivos(LEGAJO, ANHO, MES)).thenReturn(false);
        when(cargoClaseCrossSlice.tieneCargos(LEGAJO, ANHO, MES)).thenReturn(true);

        assertThat(reglas.periodoLiquidable(LEGAJO, ANHO, MES, List.of())).isTrue();
    }

    private LiquidacionView liquidacion(byte bloqueado, int estado, String liquida) {
        return new LiquidacionView(1L, ANHO, MES, bloqueado, estado, liquida, BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO, null, null, 7, "B");
    }

    private PersonaView persona(int estado, String liquida) {
        return new PersonaView(LEGAJO, BigDecimal.TEN, "PEREZ", "ANA", estado, liquida, 7, "B");
    }

    private NovedadView novedad(int codigoId) {
        return new NovedadView(1L, codigoId, "CODIGO " + codigoId, BigDecimal.ZERO, "", null, null);
    }
}
