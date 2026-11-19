package um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.layout;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;

import um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.RegistroSijp;

class SijpLayoutTest {

    private static final String LINEA_ESPERADA = "20123456789PEREZ, JUAN                   000010100101 0,0000100000000   110000,00   100000,00     0,00     0,00     0,00     0,00     0,00Mendoza-Gran Mendoza                                 100000,00   100000,00   100000,0000      0,001     0,001010101000100    92000,00     8000,00        0,00        0,00        0,00    30,00   100000,000        0,000        0,00        0,00   100000,00        0,00000     8000,00        0,00     0,00   110000,00     0,000001     7003,68     2000,00        0,00";

    @Test
    void registroNormalTiene499CaracteresYRespetaLosOffsetsDelLegacy() {
        String linea = SijpLayout.render(registro("20123456789", "PEREZ, JUAN"));

        assertThat(linea).isEqualTo(LINEA_ESPERADA);
        assertThat(linea).hasSize(SijpLayout.LONGITUD_LINEA);
        assertThat(campo(linea, 1, 11)).isEqualTo("20123456789");
        assertThat(campo(linea, 12, 30)).isEqualTo("PEREZ, JUAN" + " ".repeat(19));
        assertThat(campo(linea, 42, 1)).isEqualTo("0");
        assertThat(campo(linea, 43, 2)).isEqualTo("00");
        assertThat(campo(linea, 45, 2)).isEqualTo("01");
        assertThat(campo(linea, 47, 2)).isEqualTo("01");
        assertThat(campo(linea, 49, 3)).isEqualTo("001");
        assertThat(campo(linea, 52, 2)).isEqualTo("01");
        assertThat(campo(linea, 54, 5)).isEqualTo(" 0,00");
        assertThat(campo(linea, 59, 3)).isEqualTo("001");
        assertThat(campo(linea, 62, 6)).isEqualTo("000000");
        assertThat(campo(linea, 68, 2)).isEqualTo("00");
        assertThat(campo(linea, 70, 12)).isEqualTo("   110000,00");
        assertThat(campo(linea, 82, 12)).isEqualTo("   100000,00");
        assertThat(campo(linea, 94, 9)).isEqualTo("     0,00");
        assertThat(campo(linea, 139, 50)).isEqualTo("Mendoza-Gran Mendoza" + " ".repeat(30));
        assertThat(campo(linea, 189, 12)).isEqualTo("   100000,00");
        assertThat(campo(linea, 201, 12)).isEqualTo("   100000,00");
        assertThat(campo(linea, 213, 12)).isEqualTo("   100000,00");
        assertThat(campo(linea, 225, 2)).isEqualTo("00");
        assertThat(campo(linea, 227, 1)).isEqualTo(" ");
        assertThat(campo(linea, 237, 1)).isEqualTo("1");
        assertThat(campo(linea, 247, 1)).isEqualTo("1");
        assertThat(campo(linea, 248, 2)).isEqualTo("01");
        assertThat(campo(linea, 250, 2)).isEqualTo("01");
        assertThat(campo(linea, 252, 2)).isEqualTo("01");
        assertThat(campo(linea, 254, 2)).isEqualTo("00");
        assertThat(campo(linea, 256, 2)).isEqualTo("01");
        assertThat(campo(linea, 258, 2)).isEqualTo("00");
        assertThat(campo(linea, 260, 12)).isEqualTo("    92000,00");
        assertThat(campo(linea, 272, 12)).isEqualTo("     8000,00");
        assertThat(campo(linea, 320, 9)).isEqualTo("    30,00");
        assertThat(campo(linea, 329, 12)).isEqualTo("   100000,00");
        assertThat(campo(linea, 341, 1)).isEqualTo("0");
        assertThat(campo(linea, 342, 12)).isEqualTo("        0,00");
        assertThat(campo(linea, 354, 1)).isEqualTo("0");
        assertThat(campo(linea, 379, 12)).isEqualTo("   100000,00");
        assertThat(campo(linea, 391, 12)).isEqualTo("        0,00");
        assertThat(campo(linea, 403, 3)).isEqualTo("000");
        assertThat(campo(linea, 406, 12)).isEqualTo("     8000,00");
        assertThat(campo(linea, 439, 12)).isEqualTo("   110000,00");
        assertThat(campo(linea, 460, 3)).isEqualTo("000");
        assertThat(campo(linea, 463, 1)).isEqualTo("1");
        assertThat(campo(linea, 464, 12)).isEqualTo("     7003,68");
        assertThat(campo(linea, 476, 12)).isEqualTo("     2000,00");
        assertThat(campo(linea, 488, 12)).isEqualTo("        0,00");
    }

    @Test
    void truncaElApellidoYCompletaConEspacios() {
        String linea = SijpLayout.render(registro("20123456789",
                "APELLIDO MUY LARGO QUE SUPERA LOS TREINTA CARACTERES"));

        assertThat(campo(linea, 12, 30)).isEqualTo("APELLIDO MUY LARGO QUE SUPERA ");
    }

    @Test
    void importesNegativosSeAlineanALaDerecha() {
        RegistroSijp registro = registro("20123456789", "PEREZ, JUAN");
        registro.setImporteDetraccion(new BigDecimal("-1234.5"));

        String linea = SijpLayout.render(registro);

        assertThat(campo(linea, 464, 12)).isEqualTo("    -1234,50");
    }

    @Test
    void elContenidoSeCodificaEnWindows1252SinCambiarElLargo() {
        Charset ansi = SijpLayout.CHARSET_ANSI;
        String linea = SijpLayout.render(registro("20123456789", "MUÑOZ, JOSÉ"));

        byte[] bytes = linea.getBytes(ansi);

        assertThat(bytes).hasSize(SijpLayout.LONGITUD_LINEA);
        assertThat(new String(bytes, ansi)).isEqualTo(linea);
    }

    private String campo(String linea, int posicion, int largo) {
        return linea.substring(posicion - 1, posicion - 1 + largo);
    }

    private RegistroSijp registro(String cuil, String apellidoNombre) {
        return RegistroSijp.builder()
                .cuil(cuil)
                .apellidoNombre(apellidoNombre)
                .conyuge(0)
                .cantidadHijos(0)
                .codigoSituacion(1)
                .codigoCondicion(1)
                .codigoActividad(1)
                .codigoZona(1)
                .porcentajeAporteAdicionalSs(BigDecimal.ZERO)
                .codigoModalidadContratacion(1)
                .codigoObraSocial(0L)
                .cantidadAdherentes(0)
                .remuneracionTotal(new BigDecimal("110000"))
                .remuneracionImponible1(new BigDecimal("100000"))
                .asignacionesFamiliaresPagadas(BigDecimal.ZERO)
                .importeAporteVoluntario(BigDecimal.ZERO)
                .importeAdicionalOs(BigDecimal.ZERO)
                .importeExcedenteAportesSs(BigDecimal.ZERO)
                .importeExcedenteAportesOs(BigDecimal.ZERO)
                .provinciaLocalidad("Mendoza-Gran Mendoza")
                .remuneracionImponible2(new BigDecimal("100000"))
                .remuneracionImponible3(new BigDecimal("100000"))
                .remuneracionImponible4(new BigDecimal("100000"))
                .codigoSiniestrado(0)
                .marcaCorrespondeReduccion(" ")
                .capitalRecomposicionLrt(BigDecimal.ZERO)
                .tipoEmpleado(1)
                .aporteAdicionalObraSocial(BigDecimal.ZERO)
                .regimen(1)
                .situacionRevista1(1)
                .diaInicioSituacionRevista1(1)
                .situacionRevista2(1)
                .diaInicioSituacionRevista2(0)
                .situacionRevista3(1)
                .diaInicioSituacionRevista3(0)
                .sueldoAdicionales(new BigDecimal("92000"))
                .sac(new BigDecimal("8000"))
                .horasExtras(BigDecimal.ZERO)
                .zonaDesfavorable(BigDecimal.ZERO)
                .vacaciones(BigDecimal.ZERO)
                .cantidadDiasTrabajados(new BigDecimal("30"))
                .remuneracionImponible5(new BigDecimal("100000"))
                .trabajadorConvencionado(0)
                .remuneracionImponible6(BigDecimal.ZERO)
                .tipoOperacion(0)
                .adicionales(BigDecimal.ZERO)
                .premios(BigDecimal.ZERO)
                .remuneracion8(new BigDecimal("100000"))
                .remuneracionImponible7(BigDecimal.ZERO)
                .cantidadHorasExtras(0)
                .conceptosNoRemunerativos(new BigDecimal("8000"))
                .maternidad(BigDecimal.ZERO)
                .rectificacionRemuneracion(BigDecimal.ZERO)
                .remuneracionImponible9(new BigDecimal("110000"))
                .contribucionTareaDiferencial(BigDecimal.ZERO)
                .horasTrabajadas(0)
                .seguroColectivoVidaObligatorio(1)
                .importeDetraccion(new BigDecimal("7003.68"))
                .incrementoSalarial(new BigDecimal("2000"))
                .remuneracionImponible11(BigDecimal.ZERO)
                .build();
    }

}
