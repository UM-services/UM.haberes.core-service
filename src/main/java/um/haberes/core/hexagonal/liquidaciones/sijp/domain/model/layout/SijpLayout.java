package um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.layout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;

import um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.RegistroSijp;

/**
 * Layout del archivo SIJP.
 *
 * <p>
 * Contrato heredado de {@code bonos.vb6/clsTXTSeguridadSocial.cls}: cada registro tiene
 * exactamente <b>499</b> caracteres, sin separadores, con importes de 2 decimales y coma
 * decimal, relleno de espacios y encoding ANSI (windows-1252). El orden de los campos y los
 * anchos no se modifican: es el contrato de presentación ante AFIP.
 * </p>
 */
public final class SijpLayout {

    public static final int LONGITUD_LINEA = 499;

    public static final String FIN_LINEA = "\r\n";

    public static final Charset CHARSET_ANSI = Charset.forName("windows-1252");

    private SijpLayout() {
    }

    public static String render(RegistroSijp registro) {
        StringBuilder linea = new StringBuilder(LONGITUD_LINEA);
        linea.append(texto(registro.getCuil(), 11));
        linea.append(texto(registro.getApellidoNombre(), 30));
        linea.append(flag(registro.getConyuge()));
        linea.append(codigo(registro.getCantidadHijos(), 2));
        linea.append(codigo(registro.getCodigoSituacion(), 2));
        linea.append(codigo(registro.getCodigoCondicion(), 2));
        linea.append(codigo(registro.getCodigoActividad(), 3));
        linea.append(codigo(registro.getCodigoZona(), 2));
        linea.append(importe(registro.getPorcentajeAporteAdicionalSs(), 5));
        linea.append(codigo(registro.getCodigoModalidadContratacion(), 3));
        linea.append(codigo(registro.getCodigoObraSocial(), 6));
        linea.append(codigo(registro.getCantidadAdherentes(), 2));
        linea.append(importe(registro.getRemuneracionTotal(), 12));
        linea.append(importe(registro.getRemuneracionImponible1(), 12));
        linea.append(importe(registro.getAsignacionesFamiliaresPagadas(), 9));
        linea.append(importe(registro.getImporteAporteVoluntario(), 9));
        linea.append(importe(registro.getImporteAdicionalOs(), 9));
        linea.append(importe(registro.getImporteExcedenteAportesSs(), 9));
        linea.append(importe(registro.getImporteExcedenteAportesOs(), 9));
        linea.append(texto(registro.getProvinciaLocalidad(), 50));
        linea.append(importe(registro.getRemuneracionImponible2(), 12));
        linea.append(importe(registro.getRemuneracionImponible3(), 12));
        linea.append(importe(registro.getRemuneracionImponible4(), 12));
        linea.append(codigo(registro.getCodigoSiniestrado(), 2));
        linea.append(flag(registro.getMarcaCorrespondeReduccion()));
        linea.append(importe(registro.getCapitalRecomposicionLrt(), 9));
        linea.append(flag(registro.getTipoEmpleado()));
        linea.append(importe(registro.getAporteAdicionalObraSocial(), 9));
        linea.append(flag(registro.getRegimen()));
        linea.append(codigo(registro.getSituacionRevista1(), 2));
        linea.append(codigo(registro.getDiaInicioSituacionRevista1(), 2));
        linea.append(codigo(registro.getSituacionRevista2(), 2));
        linea.append(codigo(registro.getDiaInicioSituacionRevista2(), 2));
        linea.append(codigo(registro.getSituacionRevista3(), 2));
        linea.append(codigo(registro.getDiaInicioSituacionRevista3(), 2));
        linea.append(importe(registro.getSueldoAdicionales(), 12));
        linea.append(importe(registro.getSac(), 12));
        linea.append(importe(registro.getHorasExtras(), 12));
        linea.append(importe(registro.getZonaDesfavorable(), 12));
        linea.append(importe(registro.getVacaciones(), 12));
        linea.append(importe(registro.getCantidadDiasTrabajados(), 9));
        linea.append(importe(registro.getRemuneracionImponible5(), 12));
        linea.append(flag(registro.getTrabajadorConvencionado()));
        linea.append(importe(registro.getRemuneracionImponible6(), 12));
        linea.append(flag(registro.getTipoOperacion()));
        linea.append(importe(registro.getAdicionales(), 12));
        linea.append(importe(registro.getPremios(), 12));
        linea.append(importe(registro.getRemuneracion8(), 12));
        linea.append(importe(registro.getRemuneracionImponible7(), 12));
        linea.append(codigo(registro.getCantidadHorasExtras(), 3));
        linea.append(importe(registro.getConceptosNoRemunerativos(), 12));
        linea.append(importe(registro.getMaternidad(), 12));
        linea.append(importe(registro.getRectificacionRemuneracion(), 9));
        linea.append(importe(registro.getRemuneracionImponible9(), 12));
        linea.append(importe(registro.getContribucionTareaDiferencial(), 9));
        linea.append(codigo(registro.getHorasTrabajadas(), 3));
        linea.append(flag(registro.getSeguroColectivoVidaObligatorio()));
        linea.append(importe(registro.getImporteDetraccion(), 12));
        linea.append(importe(registro.getIncrementoSalarial(), 12));
        linea.append(importe(registro.getRemuneracionImponible11(), 12));

        String resultado = linea.toString();
        if (resultado.length() != LONGITUD_LINEA) {
            throw new IllegalStateException(
                    "Registro SIJP con longitud " + resultado.length() + " (esperada " + LONGITUD_LINEA + ")");
        }
        return resultado;
    }

    /** Texto alineado a la izquierda y truncado al ancho (equivalente a {@code Left(v & Space(n), n)}). */
    private static String texto(String valor, int ancho) {
        String texto = valor == null ? "" : valor;
        if (texto.length() > ancho) {
            return texto.substring(0, ancho);
        }
        return texto + " ".repeat(ancho - texto.length());
    }

    /**
     * Importe de 2 decimales con coma decimal, alineado a la derecha (equivalente a
     * {@code Right(Space(n) & Replace(Format(v, "0.00"), ".", ","), n)}).
     */
    private static String importe(BigDecimal valor, int ancho) {
        BigDecimal importe = valor == null ? BigDecimal.ZERO : valor;
        String texto = importe.setScale(2, RoundingMode.HALF_UP).toPlainString().replace('.', ',');
        if (texto.length() > ancho) {
            return texto.substring(texto.length() - ancho);
        }
        return " ".repeat(ancho - texto.length()) + texto;
    }

    /** Código numérico con ceros a la izquierda (equivalente a {@code Format(v, "00")}). */
    private static String codigo(long valor, int ancho) {
        String texto = Long.toString(valor);
        if (texto.length() > ancho) {
            return texto.substring(texto.length() - ancho);
        }
        return "0".repeat(ancho - texto.length()) + texto;
    }

    private static String flag(int valor) {
        return Integer.toString(valor);
    }

    private static String flag(String valor) {
        return valor == null || valor.isEmpty() ? " " : valor.substring(0, 1);
    }

}
