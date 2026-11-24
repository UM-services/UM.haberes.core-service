package um.haberes.core.service.facade;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;

/**
 * Regla de la jubilación secundaria ETEC: 2% adicional cuando el legajo dicta más horas semanales
 * en la ETEC (facultad 15) que en el resto de las facultades, comparando sólo {@code curso_cargo}.
 */
class MakeLiquidacionServiceJubilacionSecundariaTest {

    private CursoCargo curso(Integer facultadId, String horas) {
        return CursoCargo.builder()
                .curso(Curso.builder().facultadId(facultadId).build())
                .horasSemanales(new BigDecimal(horas))
                .build();
    }

    @Test
    void aplicaCuandoLaEtecTieneMasHorasQueElResto() {
        List<CursoCargo> cursos = List.of(curso(15, "16.00"), curso(1, "4.00"), curso(4, "3.00"));

        assertThat(MakeLiquidacionService.tieneMasHorasEtecQueOtras(cursos)).isTrue();
    }

    @Test
    void noAplicaCuandoElRestoTieneMasHoras() {
        List<CursoCargo> cursos = List.of(curso(15, "10.00"), curso(2, "20.00"));

        assertThat(MakeLiquidacionService.tieneMasHorasEtecQueOtras(cursos)).isFalse();
    }

    @Test
    void noAplicaConEmpate() {
        List<CursoCargo> cursos = List.of(curso(15, "16.00"), curso(2, "16.00"));

        assertThat(MakeLiquidacionService.tieneMasHorasEtecQueOtras(cursos)).isFalse();
    }

    @Test
    void noAplicaSinHorasEtec() {
        List<CursoCargo> cursos = List.of(curso(1, "20.00"), curso(4, "4.00"));

        assertThat(MakeLiquidacionService.tieneMasHorasEtecQueOtras(cursos)).isFalse();
    }

    @Test
    void sumaTodasLasHorasDeCadaOrigen() {
        List<CursoCargo> cursos = List.of(
                curso(15, "5.00"), curso(15, "6.00"), curso(15, "5.00"),
                curso(1, "4.00"), curso(4, "3.00"));

        assertThat(MakeLiquidacionService.tieneMasHorasEtecQueOtras(cursos)).isTrue();
    }

    @Test
    void ignoraCursosSinCursoOSinFacultad() {
        CursoCargo sinCurso = CursoCargo.builder().horasSemanales(new BigDecimal("9.00")).build();
        CursoCargo sinFacultad = curso(null, "9.00");
        List<CursoCargo> cursos = List.of(sinCurso, sinFacultad, curso(15, "1.00"));

        assertThat(MakeLiquidacionService.tieneMasHorasEtecQueOtras(cursos)).isTrue();
    }

    @Test
    void elDirectorEtec2559CalificaConSus16Horas() {
        List<CursoCargo> cursos = List.of(curso(15, "5.00"), curso(15, "5.00"), curso(15, "6.00"));

        assertThat(MakeLiquidacionService.tieneMasHorasEtecQueOtras(cursos)).isTrue();
    }

    @Test
    void elLegajo725CalificaCon34HorasEtecContra7DeOtrasFacultades() {
        List<CursoCargo> cursos = List.of(
                curso(15, "3.00"), curso(15, "7.00"), curso(15, "8.00"),
                curso(15, "8.00"), curso(15, "8.00"),
                curso(1, "4.00"), curso(4, "3.00"));

        assertThat(MakeLiquidacionService.tieneMasHorasEtecQueOtras(cursos)).isTrue();
    }
}
