package um.haberes.core.hexagonal.etec.liquidacion.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.Test;

import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;
import um.haberes.core.hexagonal.etec.liquidacion.domain.model.AcumuladorEtecCargo;

class EtecLiquidacionCalculatorTest {

    private final EtecLiquidacionCalculator calculator = new EtecLiquidacionCalculator();

    private ControlEtec control(String garantia, String horas, String valorHora) {
        return ControlEtec.builder()
                .garantia(new BigDecimal(garantia))
                .horasAdicionalCargaHoraria(new BigDecimal(horas))
                .valorHoraAdicionalCargaHoraria(new BigDecimal(valorHora))
                .build();
    }

    private AcumuladorEtecCargo acumulado(String cargo, String antiguedad) {
        return acumulado(cargo, antiguedad, "20.00");
    }

    private AcumuladorEtecCargo acumulado(String cargo, String antiguedad, String porcentajeArraigo) {
        AcumuladorEtecCargo acumulador = new AcumuladorEtecCargo();
        acumulador.acumular(new BigDecimal(cargo), new BigDecimal(antiguedad),
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
        assertThat(calculator.calcular(new AcumuladorEtecCargo(), control("0.00", "15.00", "8012.19")))
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
}
