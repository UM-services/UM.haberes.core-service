package um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.layout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

class SantanderLayoutTest {

    private static final String CBU = "072" + "0".repeat(19);

    @Test
    void headerSantanderTiene650Caracteres() {
        String header = SantanderLayout.header(SantanderLayout.ACUERDO_SANTANDER, 1);

        assertThat(header).hasSize(SantanderLayout.LONGITUD_LINEA);
        assertThat(header).startsWith("H30518594466" + "0" + "011" + "01" + "007" + "00001" + "00000");
    }

    @Test
    void headerOtrosConCciUsaAcuerdo03() {
        String header = SantanderLayout.header(SantanderLayout.ACUERDO_OTROS_BANCOS_CCI, 7);

        assertThat(header.substring(16, 18)).isEqualTo("03");
        assertThat(header.substring(21, 26)).isEqualTo("00007");
    }

    @Test
    void detalleRespetaLosOffsetsDelLegacy() {
        String detalle = SantanderLayout.detalle(1L, 2026, 11, "PEREZ, JUAN", "20123456789", CBU,
                LocalDate.of(2026, 11, 30), new BigDecimal("1234.56"), 50);

        assertThat(detalle).hasSize(SantanderLayout.LONGITUD_LINEA);
        assertThat(detalle.charAt(0)).isEqualTo('D');
        assertThat(detalle.substring(3, 18)).isEqualTo("000000000000001");
        assertThat(detalle.substring(18, 20)).isEqualTo("RC");
        assertThat(detalle.substring(20, 35)).isEqualTo("000000000202611");
        assertThat(detalle.substring(39, 69)).isEqualTo("PEREZ, JUAN" + " ".repeat(19));
        assertThat(detalle.substring(223, 234)).isEqualTo("20123456789");
        assertThat(detalle.charAt(396)).isEqualTo('N');
        assertThat(detalle.substring(397, 401)).isEqualTo("0054");
        assertThat(detalle.substring(401, 427)).isEqualTo("0" + CBU.substring(0, 8) + "000" + CBU.substring(8));
        assertThat(detalle.substring(435, 443)).isEqualTo("20261130");
        assertThat(detalle.substring(443, 458)).isEqualTo("000000000123456");
        assertThat(detalle.substring(458, 460)).isEqualTo("50");
    }

    @Test
    void detalleOtrosConCciUsaForma57() {
        String detalle = SantanderLayout.detalle(9L, 2026, 11, "GOMEZ, ANA", "27999999999", CBU,
                LocalDate.of(2026, 11, 30), BigDecimal.ONE, 57);

        assertThat(detalle.substring(458, 460)).isEqualTo("57");
    }

    @Test
    void trailerIncluyeTotalEnCentavosYCantidad() {
        String trailer = SantanderLayout.trailer(new BigDecimal("1234.56"), 3);

        assertThat(trailer).hasSize(SantanderLayout.LONGITUD_LINEA);
        assertThat(trailer.charAt(0)).isEqualTo('T');
        assertThat(trailer.substring(1, 16)).isEqualTo("0".repeat(15));
        assertThat(trailer.substring(16, 31)).isEqualTo("000000000123456");
        assertThat(trailer.substring(31, 38)).isEqualTo("0000003");
    }

    @Test
    void serializaConCrLfYEncodingAnsi() {
        String detalle = SantanderLayout.detalle(1L, 2026, 11, "PEREZ, JOSÉ", "20123456789", CBU,
                LocalDate.of(2026, 11, 30), new BigDecimal("10.00"), 50);

        byte[] bytes = SantanderLayout.serializar(List.of(SantanderLayout.header(1, 1), detalle));

        assertThat(bytes).hasSize((SantanderLayout.LONGITUD_LINEA + 2) * 2);
        assertThat(new String(bytes, SantanderLayout.CHARSET_ANSI)).contains("PEREZ, JOSÉ").endsWith("\r\n");
    }

    @Test
    void rechazaCbuDeLongitudInvalidaYLineasMalFormadas() {
        assertThatThrownBy(() -> SantanderLayout.cbuConFormato("072")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> SantanderLayout.serializar(List.of("D"))).isInstanceOf(IllegalStateException.class);
    }
}
