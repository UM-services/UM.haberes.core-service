package um.haberes.core.hexagonal.etec.hora_escala.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.etec.hora_escala.application.exception.EscalaHoraEtecException;

class AgrupadorHorasEtecTest {

    private CursoCargo cursoCargo(Long legajoId, Integer facultadId, Integer geograficaId,
                                  String horas) {
        return CursoCargo.builder()
                .legajoId(legajoId)
                .horasSemanales(horas == null ? null : new BigDecimal(horas))
                .curso(Curso.builder().facultadId(facultadId).geograficaId(geograficaId).build())
                .build();
    }

    @Test
    void sumaLasHorasDeLaEtecPorLegajoYSede() {
        List<CursoCargo> cursos = List.of(
                cursoCargo(2559L, 15, 1, "6.00"),
                cursoCargo(2559L, 15, 1, "5.00"),
                cursoCargo(2559L, 15, 2, "3.00"),
                cursoCargo(2030L, 15, 1, "5.00"));

        Map<Long, Map<Integer, BigDecimal>> horas = AgrupadorHorasEtec.porLegajoYSede(cursos, null);

        assertThat(horas.get(2559L)).containsEntry(1, new BigDecimal("11.00"));
        assertThat(horas.get(2559L)).containsEntry(2, new BigDecimal("3.00"));
        assertThat(horas.get(2030L)).containsEntry(1, new BigDecimal("5.00"));
    }

    @Test
    void ignoraLosCursosDeOtrasFacultades() {
        List<CursoCargo> cursos = List.of(
                cursoCargo(2559L, 3, 1, "10.00"),
                cursoCargo(2559L, 15, 1, "6.00"));

        Map<Long, Map<Integer, BigDecimal>> horas = AgrupadorHorasEtec.porLegajoYSede(cursos, null);

        assertThat(horas).containsOnlyKeys(2559L);
        assertThat(horas.get(2559L)).containsOnlyKeys(1);
        assertThat(horas.get(2559L).get(1)).isEqualByComparingTo("6.00");
    }

    @Test
    void imputaTodasLasHorasAlLegajoIndicado() {
        List<CursoCargo> cursos = List.of(
                cursoCargo(null, 15, 1, "4.00"),
                cursoCargo(2030L, 15, 1, "2.00"));

        Map<Long, Map<Integer, BigDecimal>> horas =
                AgrupadorHorasEtec.porLegajoYSede(cursos, 2559L);

        assertThat(horas).containsOnlyKeys(2559L);
        assertThat(horas.get(2559L).get(1)).isEqualByComparingTo("6.00");
    }

    @Test
    void lasHorasSinDatoCuentanComoCero() {
        List<CursoCargo> cursos = List.of(cursoCargo(2559L, 15, 1, null));

        Map<Long, Map<Integer, BigDecimal>> horas = AgrupadorHorasEtec.porLegajoYSede(cursos, null);

        assertThat(horas.get(2559L).get(1)).isEqualByComparingTo("0");
    }

    @Test
    void horasEnSedeDevuelveLaSedeOElTotalDelLegajo() {
        List<CursoCargo> cursos = List.of(
                cursoCargo(2559L, 15, 1, "11.00"),
                cursoCargo(2559L, 15, 2, "3.00"));

        assertThat(AgrupadorHorasEtec.horasEnSede(cursos, 2559L, 1))
                .isEqualByComparingTo("11.00");
        assertThat(AgrupadorHorasEtec.horasEnSede(cursos, 2559L, 2))
                .isEqualByComparingTo("3.00");
        assertThat(AgrupadorHorasEtec.horasEnSede(cursos, 2559L, null))
                .isEqualByComparingTo("14.00");
        assertThat(AgrupadorHorasEtec.horasEnSede(cursos, 2559L, 9))
                .isEqualByComparingTo("0");
    }

    @Test
    void unCursoEtecSinLegajoNoSePuedeAgrupar() {
        List<CursoCargo> cursos = List.of(cursoCargo(null, 15, 1, "6.00"));

        assertThatThrownBy(() -> AgrupadorHorasEtec.porLegajoYSede(cursos, null))
                .isInstanceOf(EscalaHoraEtecException.class)
                .hasMessageContaining("no tiene legajo asignado");
    }

    @Test
    void unCursoEtecSinGeograficaNoSePuedeAgrupar() {
        List<CursoCargo> cursos = List.of(cursoCargo(2559L, 15, null, "6.00"));

        assertThatThrownBy(() -> AgrupadorHorasEtec.porLegajoYSede(cursos, null))
                .isInstanceOf(EscalaHoraEtecException.class)
                .hasMessageContaining("no tiene geográfica");
    }
}
