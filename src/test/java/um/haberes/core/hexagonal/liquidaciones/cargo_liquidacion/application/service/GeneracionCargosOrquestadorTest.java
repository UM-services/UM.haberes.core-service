package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.audit.AuditBulkScope;
import um.haberes.core.audit.AuditBulkService;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ContextoGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ControlGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.UniversoCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.SaveAllCargoLiquidacionesUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.GeneracionCargosDatosPort;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.GeneradorCargosLiquidacion;

@ExtendWith(MockitoExtension.class)
class GeneracionCargosOrquestadorTest {

    private static final Long LEGAJO = 2559L;

    @Mock
    private GeneradorCargosLiquidacion generador;

    @Mock
    private CatalogoUniversosCargos catalogo;

    @Mock
    private GeneracionCargosDatosPort datos;

    @Mock
    private CargoLiquidacionRepository cargoLiquidacionRepository;

    @Mock
    private SaveAllCargoLiquidacionesUseCase saveAllCargoLiquidacionesUseCase;

    @Mock
    private AuditBulkService auditBulk;

    @Mock
    private AuditBulkScope auditScope;

    private GeneracionCargosOrquestador orquestador;

    @BeforeEach
    void setUp() {
        orquestador = new GeneracionCargosOrquestador(List.of(generador), catalogo, datos,
                cargoLiquidacionRepository, saveAllCargoLiquidacionesUseCase, auditBulk);
    }

    private CargoLiquidacion cargo() {
        return CargoLiquidacion.builder()
                .legajoId(LEGAJO)
                .anho(2026)
                .mes(11)
                .categoriaId(2001)
                .categoriaBasico(new BigDecimal("3000000.00"))
                .jornada(1)
                .situacion("A")
                .build();
    }

    private void contextoComun() {
        when(datos.control(2026, 11)).thenReturn(new ControlGeneracionCargos(2, new BigDecimal("1000.00"), BigDecimal.ZERO));
        when(datos.dependenciasPorSede()).thenReturn(Map.of());
    }

    @Test
    void borraElAlcanceDeLosUniversosYPersisteTodoEnUnSoloSaveAll() {
        contextoComun();
        when(auditBulk.begin(any())).thenReturn(auditScope);
        CargoLiquidacion cargo = cargo();
        when(generador.universo()).thenReturn(UniversoCargos.DOCENTE);
        when(generador.aplica(any(ContextoGeneracionCargos.class))).thenReturn(true);
        when(generador.cargos(any(ContextoGeneracionCargos.class))).thenReturn(List.of(cargo));
        when(catalogo.categoriaIds(anySet(), anyList(), eq(2026), eq(11))).thenReturn(List.of(1, 2));
        when(saveAllCargoLiquidacionesUseCase.saveAllCargoLiquidaciones(List.of(cargo), null))
                .thenReturn(List.of(cargo));

        var resultado = orquestador.generar(Set.of(UniversoCargos.DOCENTE), LEGAJO, 2026, 11);

        verify(cargoLiquidacionRepository).deleteAllByLegajoIdAndAnhoAndMesAndSituacionAndCategoriaIdIn(
                LEGAJO, 2026, 11, "A", List.of(1, 2));
        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<CargoLiquidacion>> captor = ArgumentCaptor.forClass(List.class);
        verify(saveAllCargoLiquidacionesUseCase).saveAllCargoLiquidaciones(captor.capture(), isNull());
        assertThat(captor.getValue()).containsExactly(cargo);
        assertThat(resultado.total()).isEqualTo(1);
        assertThat(resultado.conteo(UniversoCargos.DOCENTE)).isEqualTo(1);
    }

    @Test
    void enModoPeriodoBorraPorPeriodoYUsaElAlcanceDeEtec() {
        contextoComun();
        when(auditBulk.begin(any())).thenReturn(auditScope);
        CargoLiquidacion cargo = cargo();
        when(generador.universo()).thenReturn(UniversoCargos.ETEC);
        when(generador.aplica(any(ContextoGeneracionCargos.class))).thenReturn(true);
        when(generador.cargos(any(ContextoGeneracionCargos.class))).thenReturn(List.of(cargo));
        when(datos.categoriasEtecAsignadas()).thenReturn(List.of(2001));
        when(catalogo.categoriaIds(anySet(), anyList(), eq(2026), eq(11))).thenReturn(List.of(2001));
        when(saveAllCargoLiquidacionesUseCase.saveAllCargoLiquidaciones(List.of(cargo), null))
                .thenReturn(List.of(cargo));

        orquestador.generar(Set.of(UniversoCargos.ETEC), null, 2026, 11);

        verify(cargoLiquidacionRepository).deleteAllByAnhoAndMesAndCategoriaIdIn(2026, 11, List.of(2001));
    }

    @Test
    void sinGeneradoresAplicablesNoBorraYGuardaListaVacia() {
        contextoComun();
        when(generador.universo()).thenReturn(UniversoCargos.DOCENTE);
        when(generador.aplica(any(ContextoGeneracionCargos.class))).thenReturn(false);
        when(catalogo.categoriaIds(anySet(), anyList(), eq(2026), eq(11))).thenReturn(List.of());
        when(saveAllCargoLiquidacionesUseCase.saveAllCargoLiquidaciones(List.of(), null))
                .thenReturn(List.of());

        var resultado = orquestador.generar(Set.of(UniversoCargos.DOCENTE), LEGAJO, 2026, 11);

        verifyNoInteractions(cargoLiquidacionRepository);
        verify(saveAllCargoLiquidacionesUseCase).saveAllCargoLiquidaciones(List.of(), null);
        assertThat(resultado.total()).isZero();
    }

    @Test
    void previsualizarNoEscribeEnLaBase() {
        when(datos.control(2026, 11)).thenReturn(new ControlGeneracionCargos(2, new BigDecimal("1000.00"), BigDecimal.ZERO));
        when(datos.dependenciasPorSede()).thenReturn(Map.of());
        CargoLiquidacion cargo = cargo();
        when(generador.universo()).thenReturn(UniversoCargos.ETEC);
        when(generador.aplica(any(ContextoGeneracionCargos.class))).thenReturn(true);
        when(generador.cargos(any(ContextoGeneracionCargos.class))).thenReturn(List.of(cargo));

        var resultado = orquestador.previsualizar(Set.of(UniversoCargos.ETEC), LEGAJO, 2026, 11);

        assertThat(resultado.cargos()).containsExactly(cargo);
        verifyNoInteractions(cargoLiquidacionRepository);
        verifyNoInteractions(saveAllCargoLiquidacionesUseCase);
    }
}
