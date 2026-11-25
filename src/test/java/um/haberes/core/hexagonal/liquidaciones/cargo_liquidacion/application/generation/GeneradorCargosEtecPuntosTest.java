package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.generation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.model.EtecAsignacionCargo;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.exception.CargoLiquidacionException;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ContextoGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.GeneracionCargosDatosPort;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;

@ExtendWith(MockitoExtension.class)
class GeneradorCargosEtecPuntosTest {

    private static final Long LEGAJO = 2559L;

    @Mock
    private GeneracionCargosDatosPort datos;

    @InjectMocks
    private GeneradorCargosEtecPuntos generador;

    private ContextoGeneracionCargos contexto(Long legajoId, String valorPunto) {
        return contexto(legajoId, valorPunto, "0.00");
    }

    private ContextoGeneracionCargos contexto(Long legajoId, String valorPunto, String montoFijo) {
        return contexto(legajoId, valorPunto, montoFijo, "0.00");
    }

    private ContextoGeneracionCargos contexto(Long legajoId, String valorPunto, String montoFijo,
                                              String horasReferencia) {
        return new ContextoGeneracionCargos(legajoId, 2026, 11, 2, new BigDecimal(valorPunto),
                new BigDecimal(montoFijo), new BigDecimal(horasReferencia), Map.of());
    }

    private Categoria categoria(Integer id, String puntos) {
        return Categoria.builder()
                .categoriaId(id)
                .nombre("DIRECTORA ETEC")
                .docente((byte) 1)
                .puntos(new BigDecimal(puntos))
                .build();
    }

    private EtecAsignacionCargo asignacion(Long legajoId, Integer categoriaId) {
        return EtecAsignacionCargo.builder()
                .asignacionCargoId(1L)
                .legajoId(legajoId)
                .categoriaId(categoriaId)
                .dependenciaId(46)
                .fechaAlta(OffsetDateTime.of(2026, 11, 1, 0, 0, 0, 0, ZoneOffset.UTC))
                .build();
    }

    @Test
    void calculaElBasicoComoPuntosPorValorDelPunto() {
        when(datos.categoriasConPuntos(2026, 11)).thenReturn(List.of(categoria(2001, "3000.00")));
        when(datos.asignacionesEtec(LEGAJO, 2026, 11)).thenReturn(List.of(asignacion(LEGAJO, 2001)));

        var cargos = generador.cargos(contexto(LEGAJO, "1000.00"));

        assertThat(cargos).hasSize(1);
        var cargo = cargos.getFirst();
        assertThat(cargo.getLegajoId()).isEqualTo(LEGAJO);
        assertThat(cargo.getCategoriaId()).isEqualTo(2001);
        assertThat(cargo.getCategoriaBasico()).isEqualByComparingTo("3000000.00");
        assertThat(cargo.getDependenciaId()).isEqualTo(46);
        assertThat(cargo.getJornada()).isEqualTo(1);
        assertThat(cargo.getPresentismo()).isZero();
        assertThat(cargo.getSituacion()).isEqualTo("A");
        assertThat(cargo.getFechaDesde())
                .isEqualTo(OffsetDateTime.of(2026, 11, 1, 0, 0, 0, 0, ZoneOffset.UTC));
        assertThat(cargo.getFechaHasta())
                .isEqualTo(OffsetDateTime.of(2026, 11, 30, 0, 0, 0, 0, ZoneOffset.UTC));
    }

    @Test
    void prorrateaElBasicoPorLasHorasDelCargoSobreLasHorasDeReferencia() {
        when(datos.categoriasConPuntos(2026, 11)).thenReturn(List.of(categoria(992, "1738.00")));
        when(datos.asignacionesEtec(LEGAJO, 2026, 11)).thenReturn(List.of(EtecAsignacionCargo.builder()
                .asignacionCargoId(1L)
                .legajoId(LEGAJO)
                .categoriaId(992)
                .horasCargo(new BigDecimal("8.00"))
                .dependenciaId(46)
                .fechaAlta(OffsetDateTime.of(2026, 11, 1, 0, 0, 0, 0, ZoneOffset.UTC))
                .build()));

        var cargos = generador.cargos(contexto(LEGAJO, "356.3552", "283313.35", "5.00"));

        assertThat(cargos).singleElement().satisfies(cargo ->
                assertThat(cargo.getCategoriaBasico()).isEqualByComparingTo("1444253.90"));
    }

    @Test
    void redondeaElBasicoConValorDePuntoDeCuatroDecimales() {
        when(datos.categoriasConPuntos(2026, 11)).thenReturn(List.of(categoria(2001, "1738.00")));
        when(datos.asignacionesEtec(LEGAJO, 2026, 11)).thenReturn(List.of(asignacion(LEGAJO, 2001)));

        var cargos = generador.cargos(contexto(LEGAJO, "311.3633"));

        assertThat(cargos).hasSize(1);
        assertThat(cargos.getFirst().getCategoriaBasico()).isEqualByComparingTo("541149.42");
    }

    @Test
    void sumaElMontoFijoDelCargoAlProducto() {
        when(datos.categoriasConPuntos(2026, 11)).thenReturn(List.of(categoria(2001, "1738.00")));
        when(datos.asignacionesEtec(LEGAJO, 2026, 11)).thenReturn(List.of(asignacion(LEGAJO, 2001)));

        var cargos = generador.cargos(contexto(LEGAJO, "311.3633", "50000.00"));

        assertThat(cargos).hasSize(1);
        assertThat(cargos.getFirst().getCategoriaBasico()).isEqualByComparingTo("591149.42");
    }

    @Test
    void sinAsignacionesNoFallaAunqueElValorDelPuntoSeaCero() {
        when(datos.categoriasConPuntos(2026, 11)).thenReturn(List.of(categoria(2001, "3000.00")));
        when(datos.asignacionesEtec(LEGAJO, 2026, 11)).thenReturn(List.of());

        assertThat(generador.cargos(contexto(LEGAJO, "0.00"))).isEmpty();
    }

    @Test
    void fallaConAsignacionesVigentesYValorDePuntoCero() {
        when(datos.categoriasConPuntos(2026, 11)).thenReturn(List.of(categoria(2001, "3000.00")));
        when(datos.asignacionesEtec(LEGAJO, 2026, 11)).thenReturn(List.of(asignacion(LEGAJO, 2001)));

        assertThatThrownBy(() -> generador.cargos(contexto(LEGAJO, "0.00")))
                .isInstanceOf(CargoLiquidacionException.class)
                .hasMessageContaining("valor de punto ETEC");
    }

    @Test
    void ignoraAsignacionesDeCategoriasSinPuntos() {
        when(datos.categoriasConPuntos(2026, 11)).thenReturn(List.of(categoria(2001, "3000.00")));
        when(datos.asignacionesEtec(LEGAJO, 2026, 11))
                .thenReturn(List.of(asignacion(LEGAJO, 1306)));

        assertThat(generador.cargos(contexto(LEGAJO, "1000.00"))).isEmpty();
    }

    @Test
    void enModoPeriodoPideLasAsignacionesDeTodoElPeriodo() {
        when(datos.categoriasConPuntos(2026, 11)).thenReturn(List.of(categoria(2001, "3000.00")));
        when(datos.asignacionesEtec(null, 2026, 11)).thenReturn(List.of(asignacion(LEGAJO, 2001)));

        var cargos = generador.cargos(contexto(null, "1000.00"));

        assertThat(cargos).hasSize(1);
        assertThat(cargos.getFirst().getLegajoId()).isEqualTo(LEGAJO);
    }
}
