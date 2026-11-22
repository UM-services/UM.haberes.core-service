package um.haberes.core.hexagonal.etec.liquidacion.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class AcumuladorEtecTest {

    private static final BigDecimal PRESENTISMO = new BigDecimal("20.00");

    private static final BigDecimal CARRERA = new BigDecimal("10.00");

    private static final BigDecimal ARRAIGO = new BigDecimal("50.00");

    @Test
    void sinCargosNoHayAcumulacion() {
        AcumuladorEtec acumulador = new AcumuladorEtec();

        assertThat(acumulador.hayCargos()).isFalse();
        assertThat(acumulador.getCargo()).isEqualByComparingTo("0");
        assertThat(acumulador.getPresentismo()).isEqualByComparingTo("0");
    }

    @Test
    void separaLaAcumulacionDePuntosYDeHoras() {
        AcumuladorEtec acumulador = new AcumuladorEtec();

        acumulador.acumularPuntos(new BigDecimal("1000.00"), new BigDecimal("100.00"),
                PRESENTISMO, CARRERA, ARRAIGO);
        acumulador.acumularHoras(new BigDecimal("600.00"), BigDecimal.ZERO,
                PRESENTISMO, CARRERA, ARRAIGO);

        assertThat(acumulador.getPuntos().getCargo()).isEqualByComparingTo("1000.00");
        assertThat(acumulador.getHoras().getCargo()).isEqualByComparingTo("600.00");
        assertThat(acumulador.getPuntos().getPresentismo()).isEqualByComparingTo("200.00");
        assertThat(acumulador.getHoras().getPresentismo()).isEqualByComparingTo("120.00");
        assertThat(acumulador.getCargo()).isEqualByComparingTo("1600.00");
        assertThat(acumulador.getPresentismo()).isEqualByComparingTo("320.00");
        assertThat(acumulador.getAntiguedad()).isEqualByComparingTo("100.00");
        assertThat(acumulador.getAdicionalCarrera()).isEqualByComparingTo("170.00");
        assertThat(acumulador.getArraigo()).isEqualByComparingTo("800.00");
    }

    @Test
    void unCargoEnCeroNoMarcaElOrigenComoAcumulado() {
        AcumuladorEtec acumulador = new AcumuladorEtec();

        acumulador.acumularHoras(BigDecimal.ZERO, BigDecimal.ZERO, PRESENTISMO, CARRERA, ARRAIGO);

        assertThat(acumulador.hayCargos()).isFalse();
        assertThat(acumulador.getHoras().hayCargos()).isFalse();
    }

    @Test
    void elDescuentoDePresentismoSumaLosDosOrigenesSinTocarElPresentismoBruto() {
        AcumuladorEtec acumulador = new AcumuladorEtec();

        acumulador.acumularPuntos(new BigDecimal("1000.00"), BigDecimal.ZERO,
                PRESENTISMO, CARRERA, ARRAIGO, true);
        acumulador.acumularHoras(new BigDecimal("600.00"), BigDecimal.ZERO,
                PRESENTISMO, CARRERA, ARRAIGO, true);

        assertThat(acumulador.getPresentismo()).isEqualByComparingTo("320.00");
        assertThat(acumulador.getPresentismoDescuento()).isEqualByComparingTo("320.00");
    }

    @Test
    void lasHorasDeClaseSoloLasAportaElCargoPorHoras() {        AcumuladorEtec acumulador = new AcumuladorEtec();

        acumulador.acumularPuntos(new BigDecimal("1000.00"), BigDecimal.ZERO,
                PRESENTISMO, CARRERA, ARRAIGO);
        acumulador.acumularHoras(new BigDecimal("600.00"), BigDecimal.ZERO,
                PRESENTISMO, CARRERA, ARRAIGO, new BigDecimal("16.00"));

        assertThat(acumulador.getPuntos().getHoras()).isEqualByComparingTo("0");
        assertThat(acumulador.getHoras().getHoras()).isEqualByComparingTo("16.00");
        assertThat(acumulador.getHorasClase()).isEqualByComparingTo("16.00");
    }
}
