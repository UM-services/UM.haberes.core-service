package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.generation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.etec.hora_escala.domain.model.EscalaHoraEtec;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.exception.CargoLiquidacionException;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ContextoGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.GeneracionCargosDatosPort;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;

@ExtendWith(MockitoExtension.class)
class GeneradorCargosEtecHorasTest {

    private static final Long LEGAJO = 2559L;

    private static final Integer DEPENDENCIA_SEDE_1 = 46;

    private static final Integer DEPENDENCIA_SEDE_2 = 47;

    @Mock
    private GeneracionCargosDatosPort datos;

    @InjectMocks
    private GeneradorCargosEtecHoras generador;

    private ContextoGeneracionCargos contexto(Long legajoId) {
        return new ContextoGeneracionCargos(legajoId, 2026, 11, 2, new BigDecimal("311.3633"),
                new BigDecimal("0.00"), Map.of(
                        "15.1", Dependencia.builder().dependenciaId(DEPENDENCIA_SEDE_1)
                                .facultadId(15).geograficaId(1).build(),
                        "15.2", Dependencia.builder().dependenciaId(DEPENDENCIA_SEDE_2)
                                .facultadId(15).geograficaId(2).build()));
    }

    private EscalaHoraEtec tramo(String desde, String hasta, String montoFijo, String valorHora) {
        return EscalaHoraEtec.builder()
                .controlEtecId(50L)
                .horasDesde(new BigDecimal(desde))
                .horasHasta(new BigDecimal(hasta))
                .montoFijo(new BigDecimal(montoFijo))
                .valorHora(new BigDecimal(valorHora))
                .build();
    }

    private Categoria categoriaHoras() {
        return Categoria.builder()
                .categoriaId(2002)
                .nombre("DOCENTE ETEC HORAS")
                .basico(BigDecimal.ZERO)
                .docente((byte) 1)
                .puntos(BigDecimal.ZERO)
                .build();
    }

    private CursoCargo cursoCargo(Long legajoId, Integer facultadId, Integer geograficaId,
                                  String horas) {
        return CursoCargo.builder()
                .cursoCargoId(1L)
                .legajoId(legajoId)
                .horasSemanales(new BigDecimal(horas))
                .curso(Curso.builder()
                        .cursoId(10L)
                        .facultadId(facultadId)
                        .geograficaId(geograficaId)
                        .build())
                .build();
    }

    @Test
    void sumaLasHorasDeLaMismaSedeYAplicaElTramo() {
        when(datos.escalasHoraEtec(2026, 11)).thenReturn(List.of(tramo("0", "12", "50000", "5000")));
        when(datos.cursosCargo(LEGAJO, 2026, 11)).thenReturn(List.of(
                cursoCargo(LEGAJO, 15, 1, "6"),
                cursoCargo(LEGAJO, 15, 1, "6")));
        when(datos.categoria(2002)).thenReturn(categoriaHoras());

        var cargos = generador.cargos(contexto(LEGAJO));

        assertThat(cargos).hasSize(1);
        var cargo = cargos.getFirst();
        assertThat(cargo.getCategoriaId()).isEqualTo(2002);
        assertThat(cargo.getCategoriaBasico()).isEqualByComparingTo("110000.00");
        assertThat(cargo.getDependenciaId()).isEqualTo(DEPENDENCIA_SEDE_1);
        assertThat(cargo.getJornada()).isEqualTo(1);
        assertThat(cargo.getHorasJornada()).isZero();
        assertThat(cargo.getPresentismo()).isZero();
        assertThat(cargo.getSituacion()).isEqualTo("A");
        assertThat(cargo.getFechaDesde())
                .isEqualTo(OffsetDateTime.of(2026, 11, 1, 0, 0, 0, 0, ZoneOffset.UTC));
        assertThat(cargo.getFechaHasta())
                .isEqualTo(OffsetDateTime.of(2026, 11, 30, 0, 0, 0, 0, ZoneOffset.UTC));
    }

    @Test
    void generaUnCargoPorCadaSede() {
        when(datos.escalasHoraEtec(2026, 11)).thenReturn(List.of(tramo("0", "6", "10000", "1000")));
        when(datos.cursosCargo(LEGAJO, 2026, 11)).thenReturn(List.of(
                cursoCargo(LEGAJO, 15, 1, "6"),
                cursoCargo(LEGAJO, 15, 2, "6")));
        when(datos.categoria(2002)).thenReturn(categoriaHoras());

        var cargos = generador.cargos(contexto(LEGAJO));

        assertThat(cargos).hasSize(2);
        assertThat(cargos).extracting(cargo -> cargo.getDependenciaId())
                .containsExactlyInAnyOrder(DEPENDENCIA_SEDE_1, DEPENDENCIA_SEDE_2);
        assertThat(cargos).allSatisfy(cargo -> assertThat(cargo.getCategoriaBasico())
                .isEqualByComparingTo("16000.00"));
    }

    @Test
    void ignoraLosCursosDeOtrasFacultades() {
        when(datos.escalasHoraEtec(2026, 11)).thenReturn(List.of(tramo("0", "12", "50000", "5000")));
        when(datos.cursosCargo(LEGAJO, 2026, 11))
                .thenReturn(List.of(cursoCargo(LEGAJO, 3, 1, "6")));

        assertThat(generador.cargos(contexto(LEGAJO))).isEmpty();

        verify(datos, never()).categoria(2002);
    }

    @Test
    void sinEscalaConfiguradaNoGeneraNada() {
        when(datos.escalasHoraEtec(2026, 11)).thenReturn(List.of());

        assertThat(generador.cargos(contexto(LEGAJO))).isEmpty();

        verify(datos, never()).cursosCargo(LEGAJO, 2026, 11);
    }

    @Test
    void fallaSiLasHorasNoCaenEnNingunTramo() {
        when(datos.escalasHoraEtec(2026, 11)).thenReturn(List.of(tramo("0", "12", "50000", "5000")));
        when(datos.cursosCargo(LEGAJO, 2026, 11))
                .thenReturn(List.of(cursoCargo(LEGAJO, 15, 1, "20")));
        when(datos.categoria(2002)).thenReturn(categoriaHoras());

        assertThatThrownBy(() -> generador.cargos(contexto(LEGAJO)))
                .isInstanceOf(CargoLiquidacionException.class)
                .hasMessageContaining("no caen en ningún tramo")
                .hasMessageContaining("2026/11");
    }

    @Test
    void noGeneraCargoSiLasHorasSonCero() {
        when(datos.escalasHoraEtec(2026, 11)).thenReturn(List.of(tramo("0", "12", "50000", "5000")));
        when(datos.cursosCargo(LEGAJO, 2026, 11))
                .thenReturn(List.of(cursoCargo(LEGAJO, 15, 1, "0")));
        when(datos.categoria(2002)).thenReturn(categoriaHoras());

        assertThat(generador.cargos(contexto(LEGAJO))).isEmpty();
    }

    @Test
    void enModoPeriodoAgrupaPorLegajoYSede() {
        when(datos.escalasHoraEtec(2026, 11)).thenReturn(List.of(tramo("0", "12", "50000", "5000")));
        when(datos.cursosCargoPeriodo(2026, 11)).thenReturn(List.of(
                cursoCargo(100L, 15, 1, "6"),
                cursoCargo(200L, 15, 1, "6")));
        when(datos.categoria(2002)).thenReturn(categoriaHoras());

        var cargos = generador.cargos(contexto(null));

        assertThat(cargos).hasSize(2);
        assertThat(cargos).extracting(cargo -> cargo.getLegajoId()).containsExactly(100L, 200L);
        assertThat(cargos).allSatisfy(cargo -> assertThat(cargo.getCategoriaBasico())
                .isEqualByComparingTo("80000.00"));
    }
}
