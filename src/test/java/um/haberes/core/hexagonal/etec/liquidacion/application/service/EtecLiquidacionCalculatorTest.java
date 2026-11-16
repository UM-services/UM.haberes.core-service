package um.haberes.core.hexagonal.etec.liquidacion.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.Test;

import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;
import um.haberes.core.hexagonal.etec.liquidacion.domain.model.AcumuladorEtec;

class EtecLiquidacionCalculatorTest {

    private final EtecLiquidacionCalculator calculator = new EtecLiquidacionCalculator();

    private ControlEtec control(String garantia, String horas, String valorHora) {
        return ControlEtec.builder()
                .garantia(new BigDecimal(garantia))
                .horasAdicionalCargaHoraria(new BigDecimal(horas))
                .valorHoraAdicionalCargaHoraria(new BigDecimal(valorHora))
                .build();
    }

    private AcumuladorEtec acumulado(String cargo, String antiguedad) {
        return acumulado(cargo, antiguedad, "20.00");
    }

    private AcumuladorEtec acumulado(String cargo, String antiguedad, String porcentajeArraigo) {
        AcumuladorEtec acumulador = new AcumuladorEtec();
        acumulador.acumularPuntos(new BigDecimal(cargo), new BigDecimal(antiguedad),
                new BigDecimal("20.00"), new BigDecimal("10.00"),
                new BigDecimal(porcentajeArraigo));
        return acumulador;
    }

    @Test
    void calculaPresentismoAdicionalCargaHorariaYSinGarantiaCuandoLaBaseSuperaElPiso() {
        Map<Integer, BigDecimal> conceptos = calculator.calcular(
                acumulado("500000.00", "400000.00"), control("844872.78", "15.00", "8012.19"));

        assertThat(conceptos).containsEntry(985, new BigDecimal("100000.00"));
        assertThat(conceptos).containsEntry(986, new BigDecimal("90000.00"));
        assertThat(conceptos).doesNotContainKey(987);
        assertThat(conceptos).containsEntry(988, new BigDecimal("120182.85"));
        assertThat(conceptos).containsEntry(989, new BigDecimal("100000.00"));
    }

    @Test
    void agregaLaGarantiaComoDiferenciaContraElPiso() {
        Map<Integer, BigDecimal> conceptos = calculator.calcular(
                acumulado("500000.00", "400000.00"), control("2000000.00", "0.00", "0.00"));

        assertThat(conceptos).containsEntry(987, new BigDecimal("1000000.00"));
        assertThat(conceptos).doesNotContainKey(988);
        assertThat(conceptos).containsEntry(989, new BigDecimal("100000.00"));
    }

    @Test
    void noCalculaNadaSinControlEtecOSinCargosEtec() {
        assertThat(calculator.calcular(acumulado("500000.00", "400000.00"), null)).isEmpty();
        assertThat(calculator.calcular(new AcumuladorEtec(), control("0.00", "15.00", "8012.19")))
                .isEmpty();
    }

    @Test
    void laGarantiaNuncaEsNegativa() {
        Map<Integer, BigDecimal> conceptos = calculator.calcular(
                acumulado("500000.00", "400000.00"), control("100000.00", "0.00", "0.00"));

        assertThat(conceptos).doesNotContainKey(987);
        assertThat(conceptos).containsEntry(989, new BigDecimal("100000.00"));
    }

    @Test
    void elArraigoUsaElPorcentajeDeLaEscalaSegunLaAntiguedad() {
        Map<Integer, BigDecimal> conceptos = calculator.calcular(
                acumulado("500000.00", "400000.00", "60.00"), control("0.00", "0.00", "0.00"));

        assertThat(conceptos).containsEntry(989, new BigDecimal("300000.00"));
    }

    @Test
    void sinPorcentajeDeArraigoNoGeneraElConcepto() {
        Map<Integer, BigDecimal> conceptos = calculator.calcular(
                acumulado("500000.00", "400000.00", "0.00"), control("0.00", "0.00", "0.00"));

        assertThat(conceptos).doesNotContainKey(989);
    }

    @Test
    void sumaPuntosYHorasEnElMismoCodigoSinMezclarLasBases() {
        AcumuladorEtec acumulador = new AcumuladorEtec();
        acumulador.acumularPuntos(new BigDecimal("500000.00"), BigDecimal.ZERO,
                new BigDecimal("20.00"), new BigDecimal("10.00"), BigDecimal.ZERO);
        acumulador.acumularHoras(new BigDecimal("100000.00"), BigDecimal.ZERO,
                new BigDecimal("20.00"), new BigDecimal("10.00"), BigDecimal.ZERO);

        Map<Integer, BigDecimal> conceptos = calculator.calcular(acumulador,
                control("0.00", "0.00", "0.00"));

        assertThat(conceptos).containsEntry(985, new BigDecimal("120000.00"));
        assertThat(conceptos).containsEntry(986, new BigDecimal("60000.00"));
        assertThat(conceptos).doesNotContainKey(989);
    }

    @Test
    void laGarantiaDelCargoPorPuntosNoIncluyeLasHoras() {
        AcumuladorEtec acumulador = new AcumuladorEtec();
        acumulador.acumularPuntos(new BigDecimal("500000.00"), BigDecimal.ZERO,
                new BigDecimal("20.00"), new BigDecimal("10.00"), BigDecimal.ZERO);
        acumulador.acumularHoras(new BigDecimal("100000.00"), BigDecimal.ZERO,
                new BigDecimal("20.00"), new BigDecimal("10.00"), BigDecimal.ZERO);

        // Base de puntos 600.000 (cargo + presentismo): 800.000 − 600.000. Las horas no descuentan.
        Map<Integer, BigDecimal> conceptos = calculator.calcular(acumulador,
                control("800000.00", "0.00", "0.00"));

        assertThat(conceptos).containsEntry(987, new BigDecimal("200000.00"));
    }

    @Test
    void laGarantiaDelCargoPorHorasUsaElPisoCompletoDesde18Horas() {
        AcumuladorEtec acumulador = new AcumuladorEtec();
        acumulador.acumularHoras(new BigDecimal("400000.00"), BigDecimal.ZERO,
                new BigDecimal("20.00"), new BigDecimal("10.00"), BigDecimal.ZERO,
                new BigDecimal("18.00"));

        // Base 480.000 (cargo + presentismo 20 %): piso completo 900.000.
        Map<Integer, BigDecimal> conceptos = calculator.calcular(acumulador,
                control("900000.00", "0.00", "0.00"));

        assertThat(conceptos).containsEntry(987, new BigDecimal("420000.00"));
    }

    @Test
    void laGarantiaDelCargoPorHorasSeProrrateaPorDebajoDe18Horas() {
        AcumuladorEtec acumulador = new AcumuladorEtec();
        acumulador.acumularHoras(new BigDecimal("100000.00"), BigDecimal.ZERO,
                new BigDecimal("20.00"), new BigDecimal("10.00"), BigDecimal.ZERO,
                new BigDecimal("6.00"));

        // Piso 900.000 / 18 × 6 = 300.000; base 120.000 (cargo + presentismo).
        Map<Integer, BigDecimal> conceptos = calculator.calcular(acumulador,
                control("900000.00", "0.00", "0.00"));

        assertThat(conceptos).containsEntry(987, new BigDecimal("180000.00"));
    }

    @Test
    void sinCargoPorHorasNoHayGarantiaProrrateada() {
        Map<Integer, BigDecimal> conceptos = calculator.calcular(
                acumulado("100000.00", "0.00"), control("900000.00", "0.00", "0.00"));

        // Base de puntos 120.000; sin horas de clase el piso no se prorratea.
        assertThat(conceptos).containsEntry(987, new BigDecimal("780000.00"));
    }

    @Test
    void lasDosGarantiasSeSumanEnEl987() {
        AcumuladorEtec acumulador = new AcumuladorEtec();
        acumulador.acumularPuntos(new BigDecimal("500000.00"), BigDecimal.ZERO,
                new BigDecimal("20.00"), new BigDecimal("10.00"), BigDecimal.ZERO);
        acumulador.acumularHoras(new BigDecimal("100000.00"), BigDecimal.ZERO,
                new BigDecimal("20.00"), new BigDecimal("10.00"), BigDecimal.ZERO,
                new BigDecimal("6.00"));

        // Puntos: 900.000 − 600.000 = 300.000. Horas: 900.000 / 18 × 6 − 120.000 = 180.000.
        Map<Integer, BigDecimal> conceptos = calculator.calcular(acumulador,
                control("900000.00", "0.00", "0.00"));

        assertThat(conceptos).containsEntry(987, new BigDecimal("480000.00"));
    }

    @Test
    void unaBaseMayorAlPisoProrrateadoNoGeneraGarantia() {
        AcumuladorEtec acumulador = new AcumuladorEtec();
        acumulador.acumularHoras(new BigDecimal("300000.00"), BigDecimal.ZERO,
                new BigDecimal("20.00"), new BigDecimal("10.00"), BigDecimal.ZERO,
                new BigDecimal("5.00"));

        // Piso 900.000 / 18 × 5 = 250.000 contra una base de 360.000.
        Map<Integer, BigDecimal> conceptos = calculator.calcular(acumulador,
                control("900000.00", "0.00", "0.00"));

        assertThat(conceptos).doesNotContainKey(987);
    }

    @Test
    void elCargoPorHorasSumaSuCargaHorariaAlAdicionalDelCargoPorPuntos() {
        AcumuladorEtec acumulador = new AcumuladorEtec();
        acumulador.acumularPuntos(new BigDecimal("500000.00"), BigDecimal.ZERO,
                new BigDecimal("20.00"), new BigDecimal("10.00"), BigDecimal.ZERO);
        acumulador.acumularHoras(new BigDecimal("400000.00"), BigDecimal.ZERO,
                new BigDecimal("20.00"), new BigDecimal("10.00"), BigDecimal.ZERO,
                new BigDecimal("16.00"));

        Map<Integer, BigDecimal> conceptos = calculator.calcular(acumulador,
                control("0.00", "15.00", "8012.19"));

        // 15 horas fijas del cargo por puntos + 16 horas de clase del cargo por horas.
        assertThat(conceptos).containsEntry(988, new BigDecimal("248377.89"));
    }

    @Test
    void sinCargoPorPuntosElAdicionalSoloLiquidaLasHorasDeClase() {
        AcumuladorEtec acumulador = new AcumuladorEtec();
        acumulador.acumularHoras(new BigDecimal("179742.94"), BigDecimal.ZERO,
                new BigDecimal("20.00"), new BigDecimal("10.00"), BigDecimal.ZERO,
                new BigDecimal("5.00"));

        Map<Integer, BigDecimal> conceptos = calculator.calcular(acumulador,
                control("0.00", "15.00", "8012.19"));

        assertThat(conceptos).containsEntry(988, new BigDecimal("40060.95"));
    }

    @Test
    void unCargoPorHorasSinHorasNoGeneraAdicionalCargaHoraria() {
        AcumuladorEtec acumulador = new AcumuladorEtec();
        acumulador.acumularHoras(new BigDecimal("100000.00"), BigDecimal.ZERO,
                new BigDecimal("20.00"), new BigDecimal("10.00"), BigDecimal.ZERO);

        Map<Integer, BigDecimal> conceptos = calculator.calcular(acumulador,
                control("0.00", "15.00", "8012.19"));

        assertThat(conceptos).doesNotContainKey(988);
    }
}
