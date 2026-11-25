package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.generation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ContextoGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.GeneracionCargosDatosPort;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;

@ExtendWith(MockitoExtension.class)
class GeneradorCargosDocentesTest {

    private static final Long LEGAJO = 2559L;

    @Mock
    private GeneracionCargosDatosPort datos;

    @InjectMocks
    private GeneradorCargosDocentes generador;

    private ContextoGeneracionCargos contexto(Long legajoId) {
        return new ContextoGeneracionCargos(legajoId, 2026, 11, 2, new BigDecimal("1000.00"), BigDecimal.ZERO, BigDecimal.ZERO, Map.of());
    }

    private Cargo cargo(Integer categoriaId, String puntos) {
        Categoria categoria = Categoria.builder()
                .categoriaId(categoriaId)
                .nombre("CAT " + categoriaId)
                .basico(new BigDecimal("100000.00"))
                .docente((byte) 1)
                .puntos(new BigDecimal(puntos))
                .build();
        return Cargo.builder()
                .cargoId(1L)
                .legajoId(LEGAJO)
                .dependenciaId(3)
                .categoriaId(categoriaId)
                .horasJornada(new BigDecimal("1.50"))
                .jornada(1)
                .presentismo(20)
                .categoria(categoria)
                .build();
    }

    @Test
    void mapeaLosDatosDelCargo() {
        when(datos.cargosDocentes(LEGAJO, 2026, 11)).thenReturn(List.of(cargo(1, "0.00")));

        var cargos = generador.cargos(contexto(LEGAJO));

        assertThat(cargos).hasSize(1);
        var generado = cargos.getFirst();
        assertThat(generado.getCategoriaBasico()).isEqualByComparingTo("100000.00");
        assertThat(generado.getHorasJornada()).isEqualByComparingTo("1.50");
        assertThat(generado.getJornada()).isEqualTo(1);
        assertThat(generado.getPresentismo()).isEqualTo(20);
        assertThat(generado.getDependenciaId()).isEqualTo(3);
        assertThat(generado.getSituacion()).isEqualTo("A");
    }

    @Test
    void excluyeLaCategoriaDeLaNovedadDeAjusteDeBasico() {
        when(datos.cargosDocentes(LEGAJO, 2026, 11))
                .thenReturn(List.of(cargo(980, "0.00"), cargo(1, "0.00")));

        var cargos = generador.cargos(contexto(LEGAJO));

        assertThat(cargos).hasSize(1);
        assertThat(cargos.getFirst().getCategoriaId()).isEqualTo(1);
    }

    @Test
    void excluyeLasCategoriasEtec() {
        when(datos.cargosDocentes(LEGAJO, 2026, 11))
                .thenReturn(List.of(cargo(2001, "3000.00")));

        assertThat(generador.cargos(contexto(LEGAJO))).isEmpty();
    }

    @Test
    void noParticipaEnLaGeneracionDelPeriodoCompleto() {
        assertThat(generador.aplica(contexto(null))).isFalse();
    }
}
