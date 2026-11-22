package um.haberes.core.hexagonal.etec.liquidacion.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class AcumuladorEtecCargoTest {

    @Test
    void acumulaCargoAntiguedadPresentismoYAdicionalPorCargo() {
        AcumuladorEtecCargo acumulador = new AcumuladorEtecCargo();

        acumulador.acumular(new BigDecimal("500000.00"), new BigDecimal("400000.00"),
                new BigDecimal("20.00"), new BigDecimal("10.00"), new BigDecimal("20.00"));

        assertThat(acumulador.getCargo()).isEqualByComparingTo("500000.00");
        assertThat(acumulador.getAntiguedad()).isEqualByComparingTo("400000.00");
        assertThat(acumulador.getPresentismo()).isEqualByComparingTo("100000.00");
        assertThat(acumulador.getAdicionalCarrera()).isEqualByComparingTo("90000.00");
        assertThat(acumulador.getArraigo()).isEqualByComparingTo("100000.00");
        assertThat(acumulador.hayCargos()).isTrue();
    }

    @Test
    void sumaVariosCargosRedondeandoCadaUno() {
        AcumuladorEtecCargo acumulador = new AcumuladorEtecCargo();

        acumulador.acumular(new BigDecimal("100000.01"), new BigDecimal("80000.00"),
                new BigDecimal("20.00"), new BigDecimal("10.00"), new BigDecimal("20.00"));
        acumulador.acumular(new BigDecimal("250000.00"), new BigDecimal("200000.00"),
                new BigDecimal("20.00"), new BigDecimal("10.00"), new BigDecimal("20.00"));

        assertThat(acumulador.getCargo()).isEqualByComparingTo("350000.01");
        assertThat(acumulador.getAntiguedad()).isEqualByComparingTo("280000.00");
        assertThat(acumulador.getPresentismo()).isEqualByComparingTo("70000.00");
        assertThat(acumulador.getAdicionalCarrera()).isEqualByComparingTo("63000.00");
        assertThat(acumulador.getArraigo()).isEqualByComparingTo("70000.00");
    }

    @Test
    void acumulaPresentismoBrutoYDescuentoCuandoElCargoPierdePresentismo() {
        AcumuladorEtecCargo acumulador = new AcumuladorEtecCargo();

        acumulador.acumular(new BigDecimal("500000.00"), new BigDecimal("400000.00"),
                new BigDecimal("20.00"), new BigDecimal("10.00"), new BigDecimal("20.00"), true);

        assertThat(acumulador.getPresentismo()).isEqualByComparingTo("100000.00");
        assertThat(acumulador.getPresentismoDescuento()).isEqualByComparingTo("100000.00");
    }

    @Test
    void sinPerdidaDePresentismoElDescuentoQuedaEnCero() {
        AcumuladorEtecCargo acumulador = new AcumuladorEtecCargo();

        acumulador.acumular(new BigDecimal("500000.00"), new BigDecimal("400000.00"),
                new BigDecimal("20.00"), new BigDecimal("10.00"), new BigDecimal("20.00"));

        assertThat(acumulador.getPresentismo()).isEqualByComparingTo("100000.00");
        assertThat(acumulador.getPresentismoDescuento()).isEqualByComparingTo("0.00");
    }

    @Test
    void ignoraCargosEnCeroYPorcentajesNulos() {
        AcumuladorEtecCargo acumulador = new AcumuladorEtecCargo();

        acumulador.acumular(BigDecimal.ZERO, BigDecimal.TEN, new BigDecimal("20.00"),
                new BigDecimal("10.00"), new BigDecimal("20.00"));
        acumulador.acumular(new BigDecimal("1000.00"), new BigDecimal("500.00"), null, null, null);

        assertThat(acumulador.hayCargos()).isTrue();
        assertThat(acumulador.getPresentismo()).isEqualByComparingTo("0.00");
        assertThat(acumulador.getAdicionalCarrera()).isEqualByComparingTo("0.00");
        assertThat(acumulador.getArraigo()).isEqualByComparingTo("0.00");
    }
}
