package um.haberes.core.hexagonal.etec.liquidacion.application.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

class ConceptosEtecValidatorTest {

    @Test
    void noFallaCuandoTodosLosConceptosTienenCodigo() {
        Map<Integer, BigDecimal> conceptos = Map.of(
                985, new BigDecimal("100.00"),
                986, new BigDecimal("50.00"));

        assertThatCode(() -> ConceptosEtecValidator.validar(conceptos, Set.of(985, 986)))
                .doesNotThrowAnyException();
    }

    @Test
    void fallaCuandoFaltaUnCodigoConImporte() {
        Map<Integer, BigDecimal> conceptos = Map.of(
                985, new BigDecimal("100.00"),
                989, new BigDecimal("50.00"));

        assertThatThrownBy(() -> ConceptosEtecValidator.validar(conceptos, Set.of(985)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("989")
                .hasMessageContaining("control_etec.codigo_adicional_directivo");
    }

    @Test
    void ignoraConceptosEnCero() {
        Map<Integer, BigDecimal> conceptos = Map.of(987, BigDecimal.ZERO);

        assertThatCode(() -> ConceptosEtecValidator.validar(conceptos, Set.of()))
                .doesNotThrowAnyException();
    }
}
