package um.haberes.core.hexagonal.liquidaciones.individual.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * El enum es un contrato con el catálogo de códigos de haberes: si cambia un valor, cambia la
 * regla de período liquidable. El test fija los valores esperados para que el cambio sea explícito.
 */
class CodigoLiquidacionTest {

    @Test
    void conservaLosCodigosDelCatalogo() {
        assertThat(CodigoLiquidacion.AGUINALDO.codigoId()).isEqualTo(3);
        assertThat(CodigoLiquidacion.PRESENTISMO.codigoId()).isEqualTo(16);
        assertThat(CodigoLiquidacion.AGUINALDO_ETEC.codigoId()).isEqualTo(29);
        assertThat(CodigoLiquidacion.VACACIONES.codigoId()).isEqualTo(57);
    }

    @Test
    void coincideSoloConSuCodigo() {
        assertThat(CodigoLiquidacion.AGUINALDO.coincideCon(3)).isTrue();
        assertThat(CodigoLiquidacion.AGUINALDO.coincideCon(99)).isFalse();
        assertThat(CodigoLiquidacion.AGUINALDO.coincideCon(null)).isFalse();
    }
}
