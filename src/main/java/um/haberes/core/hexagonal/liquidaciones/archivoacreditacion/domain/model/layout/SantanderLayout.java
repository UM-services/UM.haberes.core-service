package um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.layout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Layout de archivo de sueldos Santander (compartido con "Otros Bancos").
 *
 * <p>
 * Contrato heredado del legacy VB6 ({@code clsHeaderSueldosSantander},
 * {@code clsDetalleSueldosSantander}, {@code clsTrailerSueldosSantander}): cada línea tiene
 * exactamente 650 caracteres, terminador CRLF y encoding ANSI (windows-1252). El orden, los
 * rellenos y los importes en centavos no se modifican: es un contrato con el banco.
 * </p>
 */
public final class SantanderLayout {

    public static final String CUIT_EMPRESA = "30518594466";

    public static final int LONGITUD_LINEA = 650;

    public static final int ACUERDO_SANTANDER = 1;
    public static final int ACUERDO_OTROS_BANCOS = 2;
    public static final int ACUERDO_OTROS_BANCOS_CCI = 3;

    public static final int FORMA_PAGO_SANTANDER = 50;
    public static final int FORMA_PAGO_OTROS_BANCOS = 52;
    public static final int FORMA_PAGO_OTROS_BANCOS_CCI = 57;

    public static final String FIN_LINEA = "\r\n";

    public static final Charset CHARSET_ANSI = Charset.forName("windows-1252");

    private static final String ESPACIOS_611 = " ".repeat(611);
    private static final String ESPACIOS_612 = " ".repeat(612);
    private static final String ESPACIOS_162 = " ".repeat(162);
    private static final String ESPACIOS_102 = " ".repeat(102);
    private static final String ESPACIOS_51 = " ".repeat(51);
    private static final String ESPACIOS_11 = " ".repeat(11);
    private static final String ESPACIOS_7 = " ".repeat(7);
    private static final String ESPACIOS_4 = "    ";
    private static final String ESPACIOS_3 = "   ";
    private static final String CEROS_83 = "0".repeat(83);
    private static final String CEROS_25 = "0".repeat(25);
    private static final String CEROS_17 = "0".repeat(17);
    private static final String CEROS_11 = "0".repeat(11);
    private static final String CEROS_8 = "0".repeat(8);

    private SantanderLayout() {
    }

    /**
     * Registro de cabecera.
     *
     * @param acuerdo 1 Santander, 2 Otros Bancos, 3 Otros Bancos con CCI
     * @param numEnvio número de envío (1 si no se informa)
     */
    public static String header(int acuerdo, int numEnvio) {
        return "H"
                + CUIT_EMPRESA
                + "0"
                + numero(11, 3)
                + numero(acuerdo, 2)
                + numero(7, 3)
                + numero(numEnvio, 5)
                + "00000"
                + ESPACIOS_7
                + " "
                + ESPACIOS_611;
    }

    /**
     * Registro de detalle de un beneficiario.
     */
    public static String detalle(Long legajoId, Integer anho, Integer mes, String apellidoNombre, String cuil,
            String cbu, java.time.LocalDate fechaPago, BigDecimal importe, int codigoFormaPago) {
        return "D"
                + " "
                + "0"
                + numero(legajoId == null ? 0L : legajoId, 15)
                + "RC"
                + numero((long) anho * 100L + mes, 15)
                + "0000"
                + texto(apellidoNombre, 30)
                + ESPACIOS_51
                + "00000"
                + ESPACIOS_4
                + CEROS_83
                + ESPACIOS_11
                + cuil
                + ESPACIOS_162
                + "N"
                + numero(54, 4)
                + cbuConFormato(cbu)
                + CEROS_8
                + fechaPago.format(java.time.format.DateTimeFormatter.BASIC_ISO_DATE)
                + numero(centavos(importe), 15)
                + numero(codigoFormaPago, 2)
                + ESPACIOS_3
                + CEROS_11
                + ESPACIOS_3
                + CEROS_11
                + ESPACIOS_3
                + CEROS_11
                + ESPACIOS_3
                + CEROS_25
                + " "
                + CEROS_17
                + ESPACIOS_102;
    }

    /**
     * Registro de totales.
     */
    public static String trailer(BigDecimal total, int cantidad) {
        return "T"
                + numero(0L, 15)
                + numero(centavos(total), 15)
                + numero(cantidad, 7)
                + ESPACIOS_612;
    }

    /**
     * Serializa las líneas con terminador CRLF y encoding ANSI, incluyendo el CRLF final.
     */
    public static byte[] serializar(List<String> lineas) {
        StringBuilder sb = new StringBuilder();
        for (String linea : lineas) {
            if (linea.length() != LONGITUD_LINEA) {
                throw new IllegalStateException(
                        "Línea de longitud inválida: " + linea.length() + " (esperado " + LONGITUD_LINEA + ")");
            }
            sb.append(linea).append(FIN_LINEA);
        }
        return sb.toString().getBytes(CHARSET_ANSI);
    }

    static String cbuConFormato(String cbu) {
        if (cbu == null || cbu.length() != 22) {
            throw new IllegalArgumentException("CBU inválido: " + cbu);
        }
        return "0" + cbu.substring(0, 8) + "000" + cbu.substring(8);
    }

    static long centavos(BigDecimal importe) {
        BigDecimal valor = importe == null ? BigDecimal.ZERO : importe;
        return valor.movePointRight(2).setScale(0, RoundingMode.HALF_UP).longValueExact();
    }

    private static String numero(long valor, int largo) {
        String texto = Long.toString(valor);
        if (texto.length() > largo) {
            throw new IllegalArgumentException("Valor " + valor + " no entra en " + largo + " posiciones");
        }
        return "0".repeat(largo - texto.length()) + texto;
    }

    private static String texto(String valor, int largo) {
        String texto = valor == null ? "" : valor;
        if (texto.length() >= largo) {
            return texto.substring(0, largo);
        }
        return texto + " ".repeat(largo - texto.length());
    }
}
