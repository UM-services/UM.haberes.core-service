package um.haberes.core.hexagonal.liquidaciones.individual.application.usecases;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.crossslice.antiguedad.AntiguedadCrossSlice;
import um.haberes.core.hexagonal.crossslice.antiguedad.model.AntiguedadView;
import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.CargoLiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.model.CargoLiquidacionView;
import um.haberes.core.hexagonal.crossslice.cargos_clase.CargoClaseCrossSlice;
import um.haberes.core.hexagonal.crossslice.cargos_clase.model.CargoConClaseView;
import um.haberes.core.hexagonal.crossslice.control.ControlCrossSlice;
import um.haberes.core.hexagonal.crossslice.control.model.ControlView;
import um.haberes.core.hexagonal.crossslice.item.ItemCrossSlice;
import um.haberes.core.hexagonal.crossslice.item.model.ItemView;
import um.haberes.core.hexagonal.crossslice.liquidacion.LiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;
import um.haberes.core.hexagonal.crossslice.novedad.NovedadCrossSlice;
import um.haberes.core.hexagonal.crossslice.novedad.model.NovedadView;
import um.haberes.core.hexagonal.crossslice.persona.PersonaCrossSlice;
import um.haberes.core.hexagonal.crossslice.persona.model.PersonaView;
import um.haberes.core.hexagonal.liquidaciones.individual.application.service.LiquidacionIndividualReglas;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.AccionesLiquidacionIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.FichaLiquidacionIndividual;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ObtenerFichaLiquidacionIndividualUseCaseImplTest {

    private static final Long LEGAJO = 1001L;

    private static final Integer ANHO = 2026;

    private static final Integer MES = 11;

    @Mock
    private PersonaCrossSlice personaCrossSlice;

    @Mock
    private ControlCrossSlice controlCrossSlice;

    @Mock
    private LiquidacionCrossSlice liquidacionCrossSlice;

    @Mock
    private AntiguedadCrossSlice antiguedadCrossSlice;

    @Mock
    private CargoLiquidacionCrossSlice cargoLiquidacionCrossSlice;

    @Mock
    private CargoClaseCrossSlice cargoClaseCrossSlice;

    @Mock
    private NovedadCrossSlice novedadCrossSlice;

    @Mock
    private ItemCrossSlice itemCrossSlice;

    @Mock
    private LiquidacionIndividualReglas reglas;

    @InjectMocks
    private ObtenerFichaLiquidacionIndividualUseCaseImpl useCase;

    @Test
    void legajoInexistenteDevuelveVacio() {
        when(personaCrossSlice.porLegajo(LEGAJO)).thenReturn(Optional.empty());

        assertThat(useCase.obtener(LEGAJO, ANHO, MES)).isEmpty();

        verify(controlCrossSlice, never()).porPeriodo(ANHO, MES);
        verify(liquidacionCrossSlice, never()).porClaveUnica(LEGAJO, ANHO, MES);
    }

    @Test
    void fichaSeparaCargosDocentesYNoDocentesYComponeBloques() {
        when(personaCrossSlice.porLegajo(LEGAJO)).thenReturn(Optional.of(persona()));
        when(controlCrossSlice.porPeriodo(ANHO, MES)).thenReturn(Optional.of(
                new ControlView(88L, ANHO, MES, 1)));
        when(liquidacionCrossSlice.porClaveUnica(LEGAJO, ANHO, MES)).thenReturn(Optional.empty());
        when(antiguedadCrossSlice.porLegajo(LEGAJO, ANHO, MES))
                .thenReturn(new AntiguedadView(192, 0, new BigDecimal("1.85"), BigDecimal.ONE));
        when(cargoLiquidacionCrossSlice.porLegajo(LEGAJO, ANHO, MES))
                .thenReturn(List.of(cargo(true), cargo(false)));
        when(cargoClaseCrossSlice.porLegajo(LEGAJO, ANHO, MES))
                .thenReturn(List.of(new CargoConClaseView(5L, ANHO, MES, "ANALISIS I - MATEMATICA", "FING",
                        "CENTRAL", 202608L, 202612L, 6, new BigDecimal("15000.00"))));
        when(novedadCrossSlice.porLegajo(LEGAJO, ANHO, MES))
                .thenReturn(List.of(new NovedadView(77L, 16, "PRESENTISMO", BigDecimal.ZERO, "N", null, null)));
        when(itemCrossSlice.porLegajo(LEGAJO, ANHO, MES))
                .thenReturn(List.of(new ItemView(1L, 1, "BASICO", new BigDecimal("900000.00"))));
        when(reglas.acciones(Optional.empty(), true))
                .thenReturn(new AccionesLiquidacionIndividual(true, true, false, true, false, null));

        FichaLiquidacionIndividual ficha = useCase.obtener(LEGAJO, ANHO, MES).orElseThrow();

        assertThat(ficha.cargosDocentes()).hasSize(1);
        assertThat(ficha.cargosNoDocentes()).hasSize(1);
        assertThat(ficha.cargosConClase()).hasSize(1);
        assertThat(ficha.novedades()).hasSize(1);
        assertThat(ficha.items()).hasSize(1);
        assertThat(ficha.control()).isNotNull();
        assertThat(ficha.liquidacion()).isNull();
        assertThat(ficha.acciones().puedeLiquidar()).isTrue();
    }

    private PersonaView persona() {
        return new PersonaView(LEGAJO, BigDecimal.TEN, "PEREZ", "ANA", 1, "S", 7, "B");
    }

    private CargoLiquidacionView cargo(boolean docente) {
        return new CargoLiquidacionView(1L, 7, "FING", OffsetDateTime.now(), OffsetDateTime.now(), 1,
                "PROFESOR", BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.TEN, 1, 0, "A", docente);
    }
}
